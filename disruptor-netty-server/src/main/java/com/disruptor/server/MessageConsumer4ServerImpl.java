package com.disruptor.server;

import com.disruptor.disrup.MessageComsumer;
import com.disruptor.entity.TranlatorData;
import com.disruptor.entity.TranslatorDataWapper;
import io.netty.channel.ChannelHandlerContext;

public class MessageConsumer4ServerImpl extends MessageComsumer {

	public MessageConsumer4ServerImpl(String consumerId) {
		super(consumerId);
	}

	public void onEvent(TranslatorDataWapper event) throws Exception {
		TranlatorData request = event.getTranlatorData();
		ChannelHandlerContext ctx = event.getCtx();
		//1.业务处理逻辑:
    	System.err.println("Sever端: id= " + request.getId() 
		+ ", name= " + request.getName() 
		+ ", message= " + request.getMessage());
    	
    	//2.回送响应信息:
    	TranlatorData response = new TranlatorData();
    	response.setId("resp: " + request.getId());
    	response.setName("resp: " + request.getName());
    	response.setMessage("resp: " + request.getMessage());
    	//写出response响应信息:
    	ctx.writeAndFlush(response);
	}

}
