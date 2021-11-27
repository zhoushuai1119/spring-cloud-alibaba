package com.cloud.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * Queue 可以有4个参数
 *      1. name          队列名
 *      2. durable       持久化消息队列 ,rabbitmq重启的时候不需要创建新的队列 默认true
 *      3. auto-delete   如果所有消费者都断开连接了,是否自动删除, 默认是false
 *      4. exclusive     表示该消息队列是否只在当前connection生效,默认是false,连接断开后,该队列会被删除
 *      5. arguments:    队列的配置
 *            Message TTL : 消息生存期
 *            Auto expire : 队列生存期
 *            Max length : 队列可以容纳的消息的最大条数
 *            Max length bytes : 队列可以容纳的消息的最大字节数
 *            Overflow behaviour : 队列中的消息溢出后如何处理
 *            Dead letter exchange : 溢出的消息需要发送到绑定该死信交换机的队列
 *            Dead letter routing key : 溢出的消息需要发送到绑定该死信交换机,并且路由键匹配的队列
 *            Maximum priority : 最大优先级
 *            Lazy mode : 懒人模式
 *            Master locator :
 */
@Configuration
public class RabbitMQConfig {

    /**
     * topic模式
     * TOPIC binding key中可以存在两种特殊字符“*”与“#”，用于做模糊匹配，
     * 其中“*”用于匹配一个单词，“#”用于匹配多个单词（可以是零个）
     * */
    public static final String TOPIC_QUEUE = "topic.queue";
    private static final String TOPIC_QUEUE_ROUTYKEY = "lzc.#";
    public static final String TOPIC_EXCHANGE = "topic.exchange";

    /**
     * fanout模式
     * Fanout 就是我们熟悉的广播模式或者订阅模式，
     * 给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     * */
    public static final String FANOUT_QUEUE = "fanout.queue";
    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    /**
     * direct模式
     * 消息中的路由键（routing key）如果和 Binding 中的 binding key 一致，
     * 交换器就将消息发到对应的队列中。路由键与队列名完全匹配
     * */
    public static final String DIRECT_QUEUE ="direct.queue" ;
    public static final String DIRECT_QUEUE_ROUTYKEY = "direct.key";
    public static final String DIRECT_EXCHANGE = "direct.exchange";

    /**
     * deadLetterQueue(死信队列type = fanout)
     * 进入死信队列三种方式:
     * 1.消息被拒绝（basic.reject or basic.nack）并且requeue=false
     * 2.消息TTL过期过期时间
     * 3.队列达到最大长度
     * */
    public static final String DEAD_LETTER_EXCHANGE = "dead_exchange";
    public static final String DEAD_LETTER_QUEUE = "dead.letter.queue";

    /**
     *
     * dalternateQueue(预警队列type = fanout)
     * 进入预警交换机的条件:
     * exchange没有匹配到相应的队列的routy_key,
     * */
    public static final String ALTERNATE_EXCHANGE = "alternate_exchange";
    public static final String ALTERNATE_QUEUE = "alternate_queue";

    /**
     * 定制化amqp模版      可根据需要定制多个
     * 此处为模版类定义 Jackson消息转换器
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
     * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
     *
     *消息没有到exchange,则confirm回调,ack=false
     * 如果消息到达exchange,则confirm回调,ack=true
     * exchange到queue成功,则不回调return
     * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
     *
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //使用jackson 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        //消息确认  yml需要配置publisher-confirms: true
        // 消息推送到server，但是在server里找不到交换机,这种情况触发的是 ConfirmCallback 回调函数。
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.error("消息发送到exchange失败,原因: {}", cause);
            }
        });
        // 消息推送到server，找到交换机了，但是没找到队列,这种情况触发的是 ConfirmCallback和RetrunCallback两个回调函数。
        // ConfirmCallback每次都会响应回调，RetrunCallback这种情况只在有交换机无队列的情况下响应！
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            Map<String,Object> map = message.getMessageProperties().getHeaders();
            Object messageId = map.get("spring_returned_message_correlation");
            Object idType = map.get("__TypeId__");
            log.info("idType:"+idType);
            byte[] body = message.getBody();
            String messageBody = new String(body);
            log.error("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", messageId+":"+messageBody, replyCode, replyText, exchange, routingKey);
        });
        return rabbitTemplate;
    }


    /**
     * topic 队列声明 && 绑定死信队列
     * x-dead-letter-exchange   对应  死信交换机
     * x-dead-letter-routing-key  对应 死信队列
     * alternate-exchange   预警交换机
     * topicQueue2消费失败或过期的消息会发送到这个死信队列中
     */
    @Bean
    public Queue topicQueue() {
        Map<String, Object> args = new HashMap<>(2);
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        return new Queue(TOPIC_QUEUE,true,false,false,args);
    }

    /**
     * alternate-exchange   对应  预警队列的exchange
     */
    @Bean
    public TopicExchange topicExchange() {
        Map<String, Object> exchanges = new HashMap<>(2);
        exchanges.put("alternate-exchange",ALTERNATE_EXCHANGE);
        return new TopicExchange(TOPIC_EXCHANGE,true,false,exchanges);
    }

    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(TOPIC_QUEUE_ROUTYKEY);
    }


    /**
     * fanout 队列声明
     */
    @Bean
    public Queue fanoutQueue() {
        Map<String, Object> args = new HashMap<>(2);
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        /*args.Add("x-message-ttl", 10000);*/
        return new Queue(FANOUT_QUEUE,true,false,false,args);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

    /**
     * direct 队列声明
     */
    @Bean
    public Queue directQueue() {
        Map<String, Object> args = new HashMap<>(2);
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        return new Queue(DIRECT_QUEUE,true,false,false,args);
    }

    @Bean
    public DirectExchange directExchange() {
        Map<String, Object> exchanges = new HashMap<>(2);
        /**备份交换器,备份交换器是为了实现没有路由到队列的消息，
         * 声明交换机的时候添加属性alternate-exchange，
         声明一个备用交换机，一般声明为fanout类型，
         这样交换机收到路由不到队列的消息就会发送到备用交换机绑定的队列中。*/
        //exchanges.put("alternate-exchange",ALTERNATE_EXCHANGE);
        //return new DirectExchange(DIRECT_EXCHANGE,true,false,exchanges);
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT_QUEUE_ROUTYKEY);
    }

    /**
     * 死信队列  声明
     * 死信队列跟交换机类型没有关系 不一定为fanoutExchange  不影响该类型交换机的特性.
     * @return the exchange
     */
    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DEAD_LETTER_EXCHANGE);
    }


    /**
     * 定义死信队列
     */
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE);
    }


    /**
     * 死信路由绑定键绑定到死信队列上.
     */
    @Bean
    public Binding redirectBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }


    /**
     * alternate_queue 队列声明
     */
    @Bean
    public Queue alternateQueue() {
        return new Queue(ALTERNATE_QUEUE);
    }

    @Bean
    public FanoutExchange alternateExchange() {
        return new FanoutExchange(ALTERNATE_EXCHANGE);
    }

    @Bean
    public Binding alternateBinding() {
        return BindingBuilder.bind(alternateQueue()).to(alternateExchange());
    }
}
