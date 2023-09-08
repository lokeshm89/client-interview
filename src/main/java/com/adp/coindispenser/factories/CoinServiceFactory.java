package com.adp.coindispenser.factories;

import com.adp.coindispenser.interfaces.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Component
public class CoinServiceFactory {

    Map<String, CoinService> coinServicesMap = new HashMap<>();
    @Autowired
    public CoinServiceFactory(Set<CoinService> coinServices) {
        createCoinServiceMap(coinServices);
    }
    private void createCoinServiceMap(Set<CoinService> coinServices) {
        coinServices.forEach(c->coinServicesMap.put(c.version(), c));
    }

    public CoinService getCoinServiceForVersion(String version){
        return coinServicesMap.get(version);
    }

    public boolean isSupportedVersion(String version) {
        return coinServicesMap.containsKey(version);
    }
}
