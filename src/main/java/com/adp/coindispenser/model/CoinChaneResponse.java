package com.adp.coindispenser.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoinChaneResponse  {
    public double coinValue;
    public Long numberOfCoins;
}
