package com.adp.coindispenser.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Entity(name = "coins")
@Data
@DynamicUpdate
@NoArgsConstructor
public class Coin {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    private double coinValue;

    private Long availableCount;

    private double total;

    public Coin(double coinValue, Long availableCount) {
        this.coinValue = coinValue;
        this.availableCount = availableCount;
    }
    @PreUpdate
    @PrePersist
    public void calculateTotal() {
        this.total = this.availableCount * this.coinValue;
    }
    public void updateAvailableCount( Long usedCount){
        this.availableCount = this.availableCount - usedCount;
    }
}
