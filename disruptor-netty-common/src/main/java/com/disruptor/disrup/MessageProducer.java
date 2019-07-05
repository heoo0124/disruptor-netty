package com.disruptor.disrup;

import com.disruptor.entity.TranlatorData;
import com.disruptor.entity.TranslatorDataWapper;
import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelHandlerContext;

public class MessageProducer {

    private String producerId;

    private RingBuffer<TranslatorDataWapper> ringBuffer;

    public MessageProducer(String producerId, RingBuffer<TranslatorDataWapper> ringBuffer) {

        this.producerId = producerId;
        this.ringBuffer = ringBuffer;
    }

    public void onData(TranlatorData data, ChannelHandlerContext ctx) {
        long sequence = ringBuffer.next();
        try {
            TranslatorDataWapper wapper = ringBuffer.get(sequence);
            wapper.setTranlatorData(data);
            wapper.setCtx(ctx);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public RingBuffer<TranslatorDataWapper> getRingBuffer() {
        return ringBuffer;
    }

    public void setRingBuffer(RingBuffer<TranslatorDataWapper> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
}
