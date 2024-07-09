package com.click.accountHistory.controller;

import com.click.accountHistory.domain.dto.request.DepositRequest;
import com.click.accountHistory.domain.dto.request.WithdrawRequest;
import com.click.accountHistory.domain.dto.response.AccountHistoryDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import com.click.accountHistory.service.AccountHistoryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/histories")
public class AccountHistoryController {

    public final AccountHistoryService accountHistoryService;

    @GetMapping
    public List<AccountHistoryResponse> getAccountHistory(String account) {
        return accountHistoryService.getAllHistory(account);
    }

    @GetMapping("/detail/{historyId}")
    public AccountHistoryDetailResponse getAccountHistoryDetail(@PathVariable Long historyId) {
        return accountHistoryService.getHistoryDetail(historyId);
    }

    @PutMapping("/detail/{historyId}")
    public void updateAccountHistoryMemo(@PathVariable Long historyId, String memo) {
        accountHistoryService.updateHistoryMemo(historyId, memo);
    }

    @PostMapping("/deposit")
    public void deposit(UUID userId, DepositRequest depositRequest){
        accountHistoryService.addDeposit(userId, depositRequest);
        //TODO 이미 계좌 쪽을 들렸다가 오는데 굳이 userID가 필요한가?
    }

    @PostMapping("/withdraw")
    public void deposit(UUID userId, WithdrawRequest withdrawRequest){
        accountHistoryService.addWithdraw(userId, withdrawRequest);
        //TODO 이것도 마찬가지로 userID가 필요한가?
    }



}
