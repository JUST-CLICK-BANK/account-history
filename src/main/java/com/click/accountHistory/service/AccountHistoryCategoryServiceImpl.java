package com.click.accountHistory.service;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.AmountByCategory;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.repository.AccountHistoryRepository;
import com.click.accountHistory.domain.repository.AmountByCategoryRepository;
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
    private final AmountByCategoryRepository amountByCategoryRepository;

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

        // 카테고리 수정 시, 지출 데이터 수정
        if(accountHistory.getBhStatus().equals("출금")){
            Category before = accountHistory.getCategoryId();;
            Long amount = accountHistory.getBhAmount();

            if (accountHistory.getHistoryId() != null) {
                AmountByCategory byCategory = amountByCategoryRepository.findByAbcAccountAndAbcCategoryAndAbcDisableTrue(
                    accountHistory.getMyAccount(), before.getCategoryName());

                byCategory.setAbcAmount(byCategory.getAbcAmount() - amount);
            }

            AmountByCategory amountByCategory = amountByCategoryRepository.findByAbcAccountAndAbcCategory(
                accountHistory.getMyAccount(), category.getCategoryName());

            if (amountByCategory == null) {
                AmountByCategory newData = new AmountByCategory(null, accountHistory.getMyAccount(), category.getCategoryName(), amount, true);
                amountByCategoryRepository.save(newData);
            } else {
                amountByCategory.setAbcAmount(amountByCategory.getAbcAmount() + amount);
            }
        }

    }
}
