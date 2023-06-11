package com.atguigu.ssyx.search.receiver;

import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.mq.constant.MqConst;
import com.atguigu.ssyx.search.service.SkuService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：zhuo
 * @description：rabbit消息接收监听器
 * @date ：2023/6/11 17:26
 */
@Component
public class SkuReceiver {

    @Autowired
    private SkuService skuService;

    /**
     * !商品上架
     * @param skuId
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_GOODS_UPPER, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_GOODS_DIRECT),
            key = {MqConst.ROUTING_GOODS_UPPER}
    ))
    public void upperSku(Long skuId, Message message, Channel channel){
        if (skuId!=null){
            //*调用方法商品上架
            skuService.upperSku(skuId);
        }
        //*手动确认接收到消息
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false); //false表示接收一个消息
        } catch (IOException e) {
            throw new SsyxException("接收消息确认失败",201);
        }
    }

    /**
     * 商品下架
     * @param skuId
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_GOODS_LOWER, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_GOODS_DIRECT),
            key = {MqConst.ROUTING_GOODS_LOWER}
    ))
    public void lowerSku(Long skuId, Message message, Channel channel) {
        if (null != skuId) {
            skuService.lowerSku(skuId);
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //false表示接收一个消息
        } catch (IOException e) {
            throw new SsyxException("接收消息确认失败",201);
        }
    }
}
