package com.disruptor.disrup;

import com.disruptor.entity.TranslatorDataWapper;
import com.lmax.disruptor.WorkHandler;

public abstract class MessageComsumer implements WorkHandler<TranslatorDataWapper> {

    private String consumerId;

    public MessageComsumer(String id){
        this.consumerId = id;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }
}
