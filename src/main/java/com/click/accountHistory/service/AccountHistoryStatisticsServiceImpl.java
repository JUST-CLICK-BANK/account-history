package com.click.accountHistory.service;

import com.click.accountHistory.domain.entity.AmountByCategory;
import com.click.accountHistory.domain.repository.AccountHistoryRepository;
import com.click.accountHistory.domain.repository.AmountByCategoryRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountHistoryStatisticsServiceImpl implements AccountHistoryStatisticsService {

    private final AccountHistoryRepository accountHistoryRepository;
    private final AmountByCategoryRepository amountByCategoryRepository;

    @Override
    public Map<String, Long> historyStatistics(String myAccount) {

        List<AmountByCategory> byAbcAccount = amountByCategoryRepository.findByAbcAccount(
            myAccount);

        Map<String, Long> sumAmountByCategory = new HashMap<>();

        for (AmountByCategory result : byAbcAccount) {
            String categoryName = result.getAbcCategory();
            Long sumAmount = result.getAbcAmount();
            sumAmountByCategory.put(categoryName, sumAmount);
        }

        return sumAmountByCategory;
    }
}
