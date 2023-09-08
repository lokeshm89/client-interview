package com.adp.coindispenser.interfaces;

import com.adp.coindispenser.model.CoinChaneResponse;

import java.util.List;

public interface CoinService {
    String version();
    List<CoinChaneResponse> issueChange(Integer bill , Integer maxCoins);
    void validateBill(Integer bill);
}
