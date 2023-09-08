package com.adp.coindispenser.services;


import com.adp.coindispenser.entity.Coin;

import com.adp.coindispenser.exception.ApplicationBusyException;
import com.adp.coindispenser.exception.NotEnoughCoinsException;
import com.adp.coindispenser.exception.TooManyCoinsException;
import com.adp.coindispenser.exception.UnSupportedBillException;
import com.adp.coindispenser.interfaces.CoinService;
import com.adp.coindispenser.model.CoinChaneResponse;
import com.adp.coindispenser.repository.CoinRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.adp.coindispenser.config.ApplicationConstants.allowedBills;

@Service(value = "DefaultCoinService")
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CoinServiceVersion1 implements CoinService {


    private final CoinRepository coinDao;

    @Override
    public String version() {
        return "v1";
    }

    @Override

    public List<CoinChaneResponse> issueChange(Integer bill, Integer maxCoins) {
        validateBill(bill);
        isSufficientCoinsAvailableForChange(bill );
        return calculateChange(bill , maxCoins);
    }

    private List<CoinChaneResponse> calculateChange(Integer bill , Integer maxCoins) {

        List<CoinChaneResponse> response = new ArrayList<>();
        //Fetching all available coins from DB.
        List<Coin> availableCoins = coinDao.findByAvailableCountGreaterThanOrderByCoinValueDesc(Long.valueOf(0))
                .orElseThrow(() -> new NotEnoughCoinsException(bill));
        Map<Long, Coin> usedCoinsTrackerMap = availableCoins.stream().collect(Collectors.toMap(Coin::getId, coin -> coin));

        Double remainingBillAmount = bill.doubleValue();
        for (Coin coin : availableCoins) {
            if (remainingBillAmount > 0) {
                Long requiredCoins = getRequiredCoinsOfThisValue(remainingBillAmount, coin);
                remainingBillAmount = remainingBillAmount - (coin.getCoinValue() * requiredCoins);
                usedCoinsTrackerMap.get(coin.getId()).updateAvailableCount(requiredCoins);
                response.add(CoinChaneResponse.builder().coinValue(coin.getCoinValue()).numberOfCoins(requiredCoins).build());
            }
        }

        double totalCoinsRequiredToCompleteTransaction = response.stream().mapToDouble(CoinChaneResponse::getNumberOfCoins).sum();
        if(totalCoinsRequiredToCompleteTransaction > maxCoins)
            throw new TooManyCoinsException(maxCoins , totalCoinsRequiredToCompleteTransaction );

        persistUsedCoinsInfoIntoDataBase(usedCoinsTrackerMap);
        return response;
    }

    private void persistUsedCoinsInfoIntoDataBase(Map<Long, Coin> usedCoinsTrackerMap) {

        try {
            coinDao.saveAllAndFlush(usedCoinsTrackerMap.values());
        } catch (OptimisticLockingFailureException e) {
            throw new ApplicationBusyException();
        }


    }

    private static Long getRequiredCoinsOfThisValue(Double bill, Coin coin) {
        Long requiredCoins = Long.valueOf(0);
        //Checking if total value of this coin is equal to given bill or
        //If total value of this coin is still less than bill , use all the coins.
        if (coin.getTotal() == bill || coin.getTotal() < bill) {
            requiredCoins = coin.getAvailableCount();
        } else if (coin.getTotal() > bill) {
            //If the total value of this coin is more than the bill use only the required coins
            requiredCoins = (long) (bill / coin.getCoinValue());
        }
        return requiredCoins;
    }

    private void isSufficientCoinsAvailableForChange(Integer bill) {
        Double availableAmount = coinDao.calculateAvailableValue();
        if (availableAmount < bill)
            throw new NotEnoughCoinsException(bill);
    }


    public void validateBill(Integer bill) {
        if (allowedBills.contains(bill))
            return;
        else throw new UnSupportedBillException("Bill " + bill + " is currently not supported");

    }
}
