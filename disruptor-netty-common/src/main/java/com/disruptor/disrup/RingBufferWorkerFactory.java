package com.disruptor.disrup;

import com.disruptor.entity.TranslatorDataWapper;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class RingBufferWorkerFactory {

    //1、单例模式
    private static class SingletonHolder {
        static final RingBufferWorkerFactory instance = new RingBufferWorkerFactory();
    }

    private RingBufferWorkerFactory() {
    }

    public static RingBufferWorkerFactory getInstance() {
        return SingletonHolder.instance;
    }

    //2、生产者消费者池化
    private Map<String, MessageComsumer> comsumers = new ConcurrentHashMap<>();
    private Map<String, MessageProducer> producers = new ConcurrentHashMap<>();

    //3、RingBuffer
    private RingBuffer<TranslatorDataWapper> ringBuffer;

    //4、sequenceBarrier
    private SequenceBarrier sequenceBarrier;

    //5、workerPool
    private WorkerPool<TranslatorDataWapper> workerPool;

    //6、初始化
    public void initAndStart(ProducerType type, int bufferSize, WaitStrategy strategy, MessageComsumer[] messageComsumers) {

        //6.1、构建RingBuffer对象
        this.ringBuffer = RingBuffer.create(type, TranslatorDataWapper::new, bufferSize, strategy);

        //6.2、设置序号栅栏
        this.sequenceBarrier = this.ringBuffer.newBarrier();

        //6.3、设置工作池
        this.workerPool = new WorkerPool<>(this.ringBuffer, this.sequenceBarrier, new EventExceptionHandler(), messageComsumers);

        //6.4、构建的消费者放入池中
        for (MessageComsumer mc : messageComsumers) {
            this.comsumers.put(mc.getConsumerId(), mc);
        }

        //6.5、添加sequences
        this.ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());

        //6.6、启动工作池
        this.workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2));

    }

    public MessageProducer getMessageProducer(String producerId) {
        MessageProducer messageProducer = this.producers.get(producerId);
        if (null == messageProducer) {
            messageProducer = new MessageProducer(producerId, this.ringBuffer);
            this.producers.put(producerId, messageProducer);
        }
        return messageProducer;
    }


    /*
     *异常静态类
     */
    static class EventExceptionHandler implements ExceptionHandler<TranslatorDataWapper> {
        public void handleEventException(Throwable ex, long sequence, TranslatorDataWapper event) {
        }

        public void handleOnStartException(Throwable ex) {
        }

        public void handleOnShutdownException(Throwable ex) {
        }
    }
}
