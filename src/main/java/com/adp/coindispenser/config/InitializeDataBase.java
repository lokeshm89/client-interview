package com.adp.coindispenser.config;

import com.adp.coindispenser.entity.Coin;
import com.adp.coindispenser.repository.CoinRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Utility class to load default values.
 */
@Service
@RequiredArgsConstructor
@DependsOn("entityManagerFactory")
public class InitializeDataBase {


    private final CoinRepository coinDao;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeCoinTable() {
        coinDao.save(new Coin(Double.valueOf(0.01), Long.valueOf(100)));
        coinDao.save(new Coin(Double.valueOf(0.05), Long.valueOf(100)));
        coinDao.save(new Coin(Double.valueOf(0.10), Long.valueOf(100)));
        coinDao.save(new Coin(Double.valueOf(0.25), Long.valueOf(100)));
        coinDao.flush();
    }
}
