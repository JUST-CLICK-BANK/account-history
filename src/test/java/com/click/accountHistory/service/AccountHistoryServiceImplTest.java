package com.click.accountHistory.service;

import static org.junit.jupiter.api.Assertions.*;

import com.click.accountHistory.domain.dto.request.DepositRequest;
import com.click.accountHistory.domain.dto.request.WithdrawRequest;
import com.click.accountHistory.domain.dto.response.AccountHistoryDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.repository.AccountHistoryRepository;
import com.click.accountHistory.domain.type.TransactionType;
import com.click.accountHistory.exception.AccountHistoryErrorCode;
import com.click.accountHistory.exception.AccountHistoryException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        accountHistoryRepository.deleteAll();
    }

    @Test
    @DisplayName("거래 내역 전체 조회")
    void getAllHistory() {
        // given
        AccountHistory accountHistory = new AccountHistory(
            null, LocalDateTime.now(), "abc", 10000L, "111-111-111", "222-222-222",
            "출금", 10000000L, TransactionType.TRANSFER, null, "카카오페이", null, null);
        accountHistoryRepository.save(accountHistory);

        AccountHistory accountHistory1 = new AccountHistory(
            null, LocalDateTime.now(), "abc", 10000L, "111-111-111", "222-222-222",
            "출금", 10000000L, TransactionType.TRANSFER, null, "카카오페이", null, null);
        accountHistoryRepository.save(accountHistory1);

        // when
        List<AccountHistoryResponse> allHistory = accountHistoryService.getAllHistory(
            "111-111-111");
        List<AccountHistoryResponse> allHistory1 = accountHistoryService.getAllHistory(
            "222-222-222");

        // then
        assertEquals(2, allHistory.size());
        assertEquals(0, allHistory1.size());
    }

    @Test
    @DisplayName("거래 내역 상세 조회")
    void getHistoryDetail() {
        // given
        AccountHistory accountHistory = new AccountHistory(
            null, LocalDateTime.now(), "abc", 10000L, "111-111-111", "333-333-333",
            "출금", 10000000L, TransactionType.TRANSFER, null, "카카오페이", null, null);
        accountHistory = accountHistoryRepository.save(accountHistory);

        // when
        AccountHistoryDetailResponse historyDetail = accountHistoryService.getHistoryDetail(accountHistory.getHistoryId());

        // then
        assertEquals("카카오페이", historyDetail.bhReceive());
        assertNotEquals("박미람", historyDetail.bhReceive());
    }

    @Test
    @DisplayName("거래 내역 상세 조회 - 없는 내역 조회 시 예외 발생")
    void getHistoryDetail_NotFound() {
        // given
        Long nonExistentId = 999L;

        // when / then
        AccountHistoryException exception = assertThrows(AccountHistoryException.class, () ->
            accountHistoryService.getHistoryDetail(nonExistentId)
        );

        assertEquals(AccountHistoryErrorCode.NO_ACCOUNT_HISTORY, exception.getErrorCode());
    }

    @Test
    @DisplayName("거래 내역 메모 수정")
    void updateHistoryMemo() {
        // given
        AccountHistory accountHistory = new AccountHistory(
            null, LocalDateTime.now(), "abc", 10000L, "111-111-111", "333-333-333",
            "출금", 10000000L, TransactionType.TRANSFER, null, "박미람", null, null);
        accountHistory = accountHistoryRepository.save(accountHistory);

        // when
        accountHistoryService.updateHistoryMemo(accountHistory.getHistoryId(), "밥 값");

        // then
        Optional<AccountHistory> byId = accountHistoryRepository.findById(accountHistory.getHistoryId());
        AccountHistory accountHistory1 = byId.orElseThrow(
            () -> new IllegalArgumentException("해당 거래 내역이 존재하지 않습니다."));

        assertEquals("밥 값", accountHistory1.getBhMemo());
    }

    @Test
    @DisplayName("입금 내역 기록")
    void addDeposit() {
        // given
        DepositRequest request = new DepositRequest("월급", 1000000L,
            "111-111-111", "222-222-222", "입금", 100000000L, null, null);

        // when
        accountHistoryService.addDeposit(request);

        // then
        List<AccountHistory> allHistories = accountHistoryRepository.findAll();
        assertEquals(1, allHistories.size());

        AccountHistory accountHistory1 = allHistories.get(0);
        assertEquals("111-111-111", accountHistory1.getMyAccount());
        assertEquals("월급", accountHistory1.getBhName());
        assertNotEquals("222-222-222", accountHistory1.getMyAccount());
    }

    @Test
    @DisplayName("출금 내역 기록")
    void addWithdraw() {
        // given
        WithdrawRequest request = new WithdrawRequest(null, "카드값", 1000000L,
            "111-111-111", "222-222-222", "출금", 100000000L, 2, null, null, null);

        // when
        accountHistoryService.addWithdraw(request);

        // then
        List<AccountHistory> allHistories = accountHistoryRepository.findAll();
        assertEquals(1, allHistories.size());

        AccountHistory accountHistory1 = allHistories.get(0);
        assertEquals("111-111-111", accountHistory1.getMyAccount());
        assertEquals("카드값", accountHistory1.getBhName());
    }
}
