package com.click.accountHistory.service;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.repository.AccountHistoryRepository;
import com.click.accountHistory.domain.repository.CategoryRepository;
import com.click.accountHistory.exception.AccountHistoryErrorCode;
import com.click.accountHistory.exception.AccountHistoryException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountHistoryCategoryServiceImpl implements AccountHistoryCategoryService {

    private final AccountHistoryRepository accountHistoryRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void changeCategory(Long historyId, Integer categoryId) {
        Optional<AccountHistory> byId = accountHistoryRepository.findById(historyId);
        AccountHistory accountHistory = byId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.NO_ACCOUNT_HISTORY));
        Optional<Category> byCategoryId = categoryRepository.findById(categoryId);
        Category category = byCategoryId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.NO_CATEGORY));

        accountHistory.setCategoryId(category);
    }
}
