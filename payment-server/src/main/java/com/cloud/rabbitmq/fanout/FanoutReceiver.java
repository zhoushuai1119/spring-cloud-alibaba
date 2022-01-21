package com.cloud.rabbitmq.fanout;

import com.cloud.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author： Zhou Shuai
 * @Date： 16:19 2019/1/10
 * @Description：
 * @Version:  01
 */
@Component
@Slf4j
public class FanoutReceiver {

    /**
     * queues是指要监听的队列的名字
     * */
    @RabbitListener(queues = RabbitMQConfig.FANOUT_QUEUE)
    public void receiveFanout(Message message, Channel channel) throws IOException {
        long tag = message.getMessageProperties().getDeliveryTag();
        String msg = new String(message.getBody());
        log.info("【receiveFanout监听到消息】" + msg);
        /*
         *接收到消息，处理业务成功则调用channel.basicAck()方法，
         *告诉mq服务器消费端处理成功，mq服务器就会删除该消息
         *参数解析
         　　deliveryTag：该消息的index
         　　multiple：是否批量处理.true:将一次性ack所有小于deliveryTag的消息
         */
        channel.basicAck(tag,false);

        /*
         * 处理失败可以调用basicNack()方法，调用该方法之后，
         * 1:会将消息发送到绑定的死信队列中(requeue= false)
         * 2:服务器会自动的重新发送该消息，让消费端重新处理，直到消费端返回basicAsk(requeue=true)
         * 参数解析
            deliveryTag:该消息的index
            multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
            requeue：被拒绝的是否重新入队列
         */
        //channel.basicNack(tag,false,false);
    }

}