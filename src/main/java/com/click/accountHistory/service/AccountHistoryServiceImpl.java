package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.request.DepositRequest;
import com.click.accountHistory.domain.dto.request.WithdrawRequest;
import com.click.accountHistory.domain.dto.response.AccountHistoryDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.repository.AccountHistoryRepository;
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
public class AccountHistoryServiceImpl implements AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepository;

    @Override
    public List<AccountHistoryResponse> getAllHistory(String account) {
        List<AccountHistory> byMyAccount = accountHistoryRepository.findByMyAccount(account);
        return byMyAccount.stream()
            .map(AccountHistoryResponse::from)
            .collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDetailResponse getHistoryDetail(Long historyId) {
        Optional<AccountHistory> byId = accountHistoryRepository.findById(historyId);
        AccountHistory accountHistory = byId.orElseThrow(() -> new AccountHistoryException(
            AccountHistoryErrorCode.NO_ACCOUNT_HISTORY));
        return AccountHistoryDetailResponse.from(accountHistory);
    }

    @Transactional
    @Override
    public void updateHistoryMemo(Long historyId, String memo) {
        Optional<AccountHistory> byId = accountHistoryRepository.findById(historyId);
        AccountHistory accountHistory = byId.orElseThrow(() -> new AccountHistoryException(
            AccountHistoryErrorCode.NO_ACCOUNT_HISTORY));

        accountHistory.setBhMemo(memo);
    }

    @Override
    public void addDeposit(DepositRequest depositRequest) {
        accountHistoryRepository.save(depositRequest.toEntity());
    }

    @Override
    public void addWithdraw(WithdrawRequest withdrawRequest) {
        accountHistoryRepository.save(withdrawRequest.toEntity());
    }
}
