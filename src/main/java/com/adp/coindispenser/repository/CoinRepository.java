package com.adp.coindispenser.repository;

import com.adp.coindispenser.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

    @Query("select sum( c.availableCount * c.coinValue) AS TotalValue FROM coins as c")
    Double calculateAvailableValue();


    Optional<List<Coin>> findByAvailableCountGreaterThanOrderByCoinValueDesc(Long count);

    @Transactional
    @Modifying
    @Query("update coins c set c.availableCount=:availableCount where c.id =:id")
    void updateCoinCount(@Param("availableCount") Long availableCount, @Param("id") double id);

}
