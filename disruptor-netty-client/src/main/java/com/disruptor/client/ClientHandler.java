package com.disruptor.client;

import com.disruptor.disrup.MessageProducer;
import com.disruptor.disrup.RingBufferWorkerFactory;
import com.disruptor.entity.TranlatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*try {
            TranlatorData response = (TranlatorData)msg;
            System.err.println("Return From Client端: id= " + response.getId()
                    + ", name= " + response.getName()
                    + ", message= " + response.getMessage());
        } finally {
            ReferenceCountUtil.release(msg); hh
        }*/
        TranlatorData response = (TranlatorData)msg;
        MessageProducer producer = RingBufferWorkerFactory.getInstance().getMessageProducer("comsumer:" + UUID.randomUUID());
        producer.onData(response, ctx);

    }


}
