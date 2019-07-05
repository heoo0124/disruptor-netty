package com.disruptor;

import com.disruptor.disrup.MessageComsumer;
import com.disruptor.disrup.RingBufferWorkerFactory;
import com.disruptor.server.MessageConsumer4ServerImpl;
import com.disruptor.server.NettyServer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);

        MessageComsumer[] comsumers = new MessageComsumer[4];

        for (int i = 0; i < comsumers.length; i++) {
            comsumers[i] = new MessageConsumer4ServerImpl("ServerComsumer:" + UUID.randomUUID());
        }

        RingBufferWorkerFactory.getInstance().initAndStart(ProducerType.MULTI, 1024 * 1024, new YieldingWaitStrategy(), comsumers);

        new NettyServer();
    }

}
