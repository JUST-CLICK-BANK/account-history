package com.click.accountHistory.service;

import static org.junit.jupiter.api.Assertions.*;

import com.click.accountHistory.domain.dto.request.DepositRequest;
import com.click.accountHistory.domain.dto.request.WithdrawRequest;
import com.click.accountHistory.domain.dto.response.AccountHistoryDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.repository.AccountHistoryRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountHistoryServiceImplTest {

    @Autowired
    private AccountHistoryService accountHistoryService;

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @Test
    @DisplayName("거래 내역 전체 조회")
    void getAllHistory() {
        // given
        AccountHistory accountHistory = new AccountHistory(
            null, LocalDateTime.now(), "abc", 10000L, "111-111-111", "222-222-222",
            "출금", 10000000L, 1, null, "카카오페이", null, null);
        accountHistoryRepository.save(accountHistory);

        AccountHistory accountHistory1 = new AccountHistory(
            null, LocalDateTime.now(), "abc", 10000L, "111-111-111", "222-222-222",
            "출금", 10000000L, 1, null, "카카오페이", null, null);
        accountHistoryRepository.save(accountHistory1);

        // when
        List<AccountHistoryResponse> allHistory = accountHistoryService.getAllHistory(
            "111-111-111");
        List<AccountHistoryResponse> allHistory1 = accountHistoryService.getAllHistory(
            "222-222-222");
        // then
        Assertions.assertEquals(2, allHistory.size());
        Assertions.assertEquals(0, allHistory1.size());
    }

    @Test
    @DisplayName("거래 내역 상세 조회")
    void getHistoryDetail() {
        // given
        AccountHistory accountHistory = new AccountHistory(
            null, LocalDateTime.now(), "abc", 10000L, "111-111-111", "333-333-333",
            "출금", 10000000L, 1, null, "카카오페이", null, null);
        accountHistoryRepository.save(accountHistory);

        AccountHistory accountHistory1 = new AccountHistory(
            null, LocalDateTime.now(), "abc", 10000L, "111-111-111", "222-222-222",
            "출금", 10000000L, 1, null, "박미람", null, null);
        accountHistoryRepository.save(accountHistory1);

        //when
        AccountHistoryDetailResponse historyDetail = accountHistoryService.getHistoryDetail(1L);

        //then
        Assertions.assertEquals("카카오페이", historyDetail.bhReceive());
        Assertions.assertNotEquals("박미람",historyDetail.bhReceive());
    }

    @Test
    @DisplayName("거래 내역 메모 수정")
    void updateHistoryMemo() {
        // given
        AccountHistory accountHistory = new AccountHistory(
            null, LocalDateTime.now(), "abc", 10000L, "111-111-111", "333-333-333",
            "출금", 10000000L, 1, null, "박미람", null, null);
        accountHistoryRepository.save(accountHistory);

        // when
        accountHistoryService.updateHistoryMemo(1L, "밥 값");

        //then
        Optional<AccountHistory> byId = accountHistoryRepository.findById(1L);
        AccountHistory accountHistory1 = byId.orElseThrow(
            () -> new IllegalArgumentException("asd"));

        Assertions.assertEquals("밥 값", accountHistory1.getBhMemo());

    }

    @Test
    @DisplayName("입금 내역 기록")
    void addDeposit() {
        // given
        UUID uuid = UUID.randomUUID();
        DepositRequest request = new DepositRequest(null,"월급", 1000000L,
            "111-111-111","222-222-222", "입금", "고용노동부",null);

        // when
        accountHistoryService.addDeposit(uuid, request);

        // then
        Optional<AccountHistory> byId = accountHistoryRepository.findById(1L);
        AccountHistory accountHistory1 = byId.orElseThrow(() -> new IllegalArgumentException("asd"));

        Assertions.assertEquals("111-111-111", accountHistory1.getMyAccount());
        Assertions.assertEquals("월급", accountHistory1.getBhName());
        Assertions.assertNotEquals("222-222-222", accountHistory1.getMyAccount());
    }

    @Test
    @DisplayName("출금 내역 기록")
    void addWithdraw() {
        // given
        UUID uuid = UUID.randomUUID();
        WithdrawRequest request = new WithdrawRequest(null,"카드값", 1000000L,
            "111-111-111","222-222-222", "출금", 1,null, "현대카드",null);

        // when
        accountHistoryService.addWithdraw(uuid, request);

        // then
        Optional<AccountHistory> byId = accountHistoryRepository.findById(1L);
        AccountHistory accountHistory1 = byId.orElseThrow(() -> new IllegalArgumentException("asd"));
        Assertions.assertEquals("111-111-111", accountHistory1.getMyAccount());
        Assertions.assertEquals("카드값", accountHistory1.getBhName());
        Assertions.assertEquals("현대카드", accountHistory1.getBhReceive());
    }
}
