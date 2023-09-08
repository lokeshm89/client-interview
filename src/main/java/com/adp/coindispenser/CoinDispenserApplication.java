package com.adp.coindispenser;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Lokesh Venktesan
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableTransactionManagement
@EntityScan(basePackages = {"com.adp.coindispenser.entity"})
@EnableJpaRepositories(basePackages = {"com.adp.coindispenser.repository"})
public class CoinDispenserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoinDispenserApplication.class, args);
    }

}
