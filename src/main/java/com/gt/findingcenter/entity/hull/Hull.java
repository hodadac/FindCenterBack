package com.gt.findingcenter.entity.hull;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Hull {
    BigDecimal x;
    BigDecimal y;

    public Hull(BigDecimal x, BigDecimal y){
        this.x = x;
        this.y = y;
    }
}
