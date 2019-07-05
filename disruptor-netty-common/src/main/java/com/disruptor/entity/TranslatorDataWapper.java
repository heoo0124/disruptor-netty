package com.disruptor.entity;

import io.netty.channel.ChannelHandlerContext;

public class TranslatorDataWapper {

    private TranlatorData tranlatorData;

    private ChannelHandlerContext ctx;

    public TranlatorData getTranlatorData() {
        return tranlatorData;
    }

    public void setTranlatorData(TranlatorData tranlatorData) {
        this.tranlatorData = tranlatorData;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
}
