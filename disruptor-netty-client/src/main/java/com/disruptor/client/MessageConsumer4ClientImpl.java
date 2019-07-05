package com.disruptor.client;

import com.disruptor.disrup.MessageComsumer;
import com.disruptor.entity.TranlatorData;
import com.disruptor.entity.TranslatorDataWapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class MessageConsumer4ClientImpl extends MessageComsumer {

	public MessageConsumer4ClientImpl(String consumerId) {
		super(consumerId);
	}

	public void onEvent(TranslatorDataWapper event) throws Exception {
		TranlatorData response = event.getTranlatorData();
		ChannelHandlerContext ctx = event.getCtx();
		//业务逻辑处理:
		try {
    		System.err.println("Client端: id= " + response.getId() 
			+ ", name= " + response.getName()
			+ ", message= " + response.getMessage());
		} finally {
			ReferenceCountUtil.release(response);
		}
	}

}
