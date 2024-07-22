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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/histories")
public class AccountHistoryController {

    public final AccountHistoryService accountHistoryService;

    @GetMapping
    public List<AccountHistoryResponse> getAccountHistory(@RequestParam String account) {
        return accountHistoryService.getAllHistory(account);
    }

    @GetMapping("/detail/{historyId}")
    public AccountHistoryDetailResponse getAccountHistoryDetail(@PathVariable Long historyId) {
        return accountHistoryService.getHistoryDetail(historyId);
    }

    @PutMapping("/detail/{historyId}")
    public void updateAccountHistoryMemo(@PathVariable Long historyId,
        @RequestBody(required = false) String memo) {
        accountHistoryService.updateHistoryMemo(historyId, memo);
    }

    @PostMapping("/deposit")
    public void deposit(@RequestBody DepositRequest depositRequest) {
        accountHistoryService.addDeposit(depositRequest);
    }

    @PostMapping("/withdraw")
    public void deposit(@RequestBody WithdrawRequest withdrawRequest) {
        accountHistoryService.addWithdraw(withdrawRequest);
    }


}
