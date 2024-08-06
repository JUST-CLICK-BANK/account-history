package com.click.accountHistory.service;

import com.click.accountHistory.domain.entity.AccountMonthBudget;
import com.click.accountHistory.domain.entity.AmountByCategory;
import com.click.accountHistory.domain.repository.AccountMonthBudgetRepository;
import com.click.accountHistory.domain.repository.AmountByCategoryRepository;
import com.click.accountHistory.exception.AccountHistoryErrorCode;
import com.click.accountHistory.exception.AccountHistoryException;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountHistoryStatisticsServiceImpl implements AccountHistoryStatisticsService {

    private final AccountMonthBudgetRepository accountMonthBudgetRepository;
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

    @Transactional
    @Override
    public AccountMonthBudget getBudgetByAccount(String myAccount) {
        Long expenditure = amountByCategoryRepository.sumAmountsByAccount(myAccount);
        Optional<AccountMonthBudget> byId = accountMonthBudgetRepository.findById(myAccount);

        if (byId.isEmpty()) {
            AccountMonthBudget accountMonthBudget = new AccountMonthBudget(myAccount, 0L,
                expenditure);
            return accountMonthBudgetRepository.save(accountMonthBudget);
        } else {
            AccountMonthBudget accountMonthBudget = byId.get();
            accountMonthBudget.setMbExpenditure(expenditure);
            return accountMonthBudget;
        }
    }

    @Transactional
    @Override
    public void updateBudgetByAccount(String myAccount, Long budget) {
        Optional<AccountMonthBudget> byId = accountMonthBudgetRepository.findById(myAccount);
        AccountMonthBudget accountMonthBudget = byId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.WRONG_ACCOUNT));

        accountMonthBudget.setMbBudget(budget);
    }
}
