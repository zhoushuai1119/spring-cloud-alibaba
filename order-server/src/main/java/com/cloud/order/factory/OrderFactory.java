package com.cloud.order.factory;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单工厂类(工厂模式)
 *
 * @Author zhou shuai
 * @Date 2020/11/15 12:13:13
 */
@Component
public class OrderFactory {

    @Resource
    private List<ChannelOrder> channelOrders = new ArrayList<>();

    private Map<String, ChannelOrder> channelOrderHashMap = new HashMap<>();

    @PostConstruct
    private void init() {
        for (ChannelOrder channelOrder : channelOrders) {
            String orderWay = String.valueOf(channelOrder.orderWay().getValue());
            channelOrderHashMap.put(orderWay, channelOrder);
        }
    }

    /**
     * 根据订单的不同渠道，获取不一样的实例
     *
     * @param orderWay 下单途径
     * @return
     */
    public ChannelOrder chooseOrderObj(Integer orderWay) {
        return channelOrderHashMap.get(String.valueOf(orderWay));
    }

}
