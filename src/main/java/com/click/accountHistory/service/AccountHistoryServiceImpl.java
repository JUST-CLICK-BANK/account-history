package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.request.DepositRequest;
import com.click.accountHistory.domain.dto.request.WithdrawRequest;
import com.click.accountHistory.domain.dto.response.AccountHistoryDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.repository.AccountHistoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        AccountHistory accountHistory = byId.orElseThrow(() -> new IllegalArgumentException("거래 기록이 없습니다."));
        return AccountHistoryDetailResponse.from(accountHistory);
    }

    @Override
    public void addDeposit(UUID userId, DepositRequest depositRequest) {
        accountHistoryRepository.save(depositRequest.toEntity());
    }

    @Override
    public void addWithdraw(UUID userId, WithdrawRequest withdrawRequest) {
        accountHistoryRepository.save(withdrawRequest.toEntity());
    }
}
