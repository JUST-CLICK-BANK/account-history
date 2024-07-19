package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.request.CategoryRequest;
import com.click.accountHistory.domain.dto.request.DepositRequest;
import com.click.accountHistory.domain.dto.request.WithdrawRequest;
import com.click.accountHistory.domain.dto.response.AccountHistoryDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.AmountByCategory;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.repository.AccountHistoryRepository;
import com.click.accountHistory.domain.repository.AmountByCategoryRepository;
import com.click.accountHistory.domain.repository.CategoryRepository;
import com.click.accountHistory.exception.AccountHistoryErrorCode;
import com.click.accountHistory.exception.AccountHistoryException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountHistoryServiceImpl implements AccountHistoryService, AmountByCategoryService {

    private final AccountHistoryRepository accountHistoryRepository;
    private final AmountByCategoryRepository amountByCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<AccountHistoryResponse> getAllHistory(String account) {
        List<AccountHistory> byMyAccount = accountHistoryRepository.findByMyAccount(account);
        return byMyAccount.stream().map(AccountHistoryResponse::from).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDetailResponse getHistoryDetail(Long historyId) {
        Optional<AccountHistory> byId = accountHistoryRepository.findById(historyId);
        AccountHistory accountHistory = byId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.NO_ACCOUNT_HISTORY));
        return AccountHistoryDetailResponse.from(accountHistory);
    }

    @Transactional
    @Override
    public void updateHistoryMemo(Long historyId, String memo) {
        Optional<AccountHistory> byId = accountHistoryRepository.findById(historyId);
        AccountHistory accountHistory = byId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.NO_ACCOUNT_HISTORY));

        accountHistory.setBhMemo(memo);
    }

    @Override
    public void addDeposit(DepositRequest depositRequest) {
        Optional<Category> byId = categoryRepository.findById(depositRequest.categoryId());
        Category category = byId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.NO_CATEGORY));
        accountHistoryRepository.save(depositRequest.toEntity(category));
    }

    @Override
    @Transactional
    public void addWithdraw(WithdrawRequest withdrawRequest) {
        Optional<Category> byId = categoryRepository.findById(withdrawRequest.categoryId());
        Category category = byId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.NO_CATEGORY));
        accountHistoryRepository.save(withdrawRequest.toEntity(category));

        CalculatedCategoryAmount(withdrawRequest);
    }

    @Override
    public void CalculatedCategoryAmount(WithdrawRequest withdrawRequest) {
        Category category = categoryRepository.findById(withdrawRequest.categoryId())
            .orElseThrow(() -> new AccountHistoryException(AccountHistoryErrorCode.NO_CATEGORY));
        AmountByCategory byFind = amountByCategoryRepository.findByAbcAccountAndAbcCategory(
            withdrawRequest.myAccount(), category.getCategoryName());

        CategoryRequest categoryRequest = new CategoryRequest(withdrawRequest.myAccount(),
            category.getCategoryName(), withdrawRequest.bhAmount());

        if (byFind != null) {
            byFind.setAbcAmount(byFind.getAbcAmount() + withdrawRequest.bhAmount());
            return;
        }

        amountByCategoryRepository.save(categoryRequest.toEntity());
    }

}
