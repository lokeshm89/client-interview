package com.adp.coindispenser.services;

import com.adp.coindispenser.model.CoinCountUpdateRequest;
import com.adp.coindispenser.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoinInventoryService {
    private final CoinRepository coinDao;

    public boolean updateCoinCounts(List<CoinCountUpdateRequest> updateRequestList) {
        try {
            updateRequestList.forEach(request -> coinDao.updateCoinCount(Long.valueOf(request.getCount()), request.getId()));
            coinDao.flush();
        } catch (Exception e) {
            log.error("Update failed" + e.getMessage());
            return false;
        }
        return true;
    }
}
