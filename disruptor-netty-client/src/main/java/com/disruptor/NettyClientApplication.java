package com.disruptor;

import com.disruptor.client.MessageConsumer4ClientImpl;
import com.disruptor.client.NettyClient;
import com.disruptor.disrup.MessageComsumer;
import com.disruptor.disrup.RingBufferWorkerFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class NettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class, args);


        MessageComsumer[] comsumers = new MessageComsumer[4];

        for (int i = 0; i < comsumers.length; i++) {
            comsumers[i] = new MessageConsumer4ClientImpl("ClientComsumer:" + UUID.randomUUID());
        }

        RingBufferWorkerFactory.getInstance().initAndStart(ProducerType.MULTI, 1024 * 1024, new BlockingWaitStrategy(), comsumers);

        new NettyClient().sendData();
    }

}
