package com.disruptor.server;

import com.disruptor.disrup.MessageProducer;
import com.disruptor.disrup.RingBufferWorkerFactory;
import com.disruptor.entity.TranlatorData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /*TranlatorData request = (TranlatorData) msg;
        System.err.println("From Client:"+request);
        TranlatorData response = new TranlatorData();
        response.setId("resp: " + request.getId());
        response.setName("resp: " + request.getName());
        response.setMessage("resp: " + request.getMessage());
        //写出response响应信息:
        ctx.writeAndFlush(response);*/

        TranlatorData request = (TranlatorData) msg;

        MessageProducer producer = RingBufferWorkerFactory.getInstance().getMessageProducer("producer: "+UUID.randomUUID());

        producer.onData(request,ctx);

    }

}
