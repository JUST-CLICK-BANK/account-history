package com.click.accountHistory.service;

import static org.junit.jupiter.api.Assertions.*;

import com.click.accountHistory.TestInitData;
import com.click.accountHistory.domain.dto.request.DepositRequest;
import com.click.accountHistory.domain.dto.request.WithdrawRequest;
import com.click.accountHistory.domain.dto.response.AccountHistoryDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.repository.AccountHistoryRepository;
import com.click.accountHistory.domain.repository.AmountByCategoryRepository;
import com.click.accountHistory.domain.repository.CategoryRepository;
import com.click.accountHistory.exception.AccountHistoryException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AccountHistoryServiceImplTest extends TestInitData {

    @InjectMocks
    private AccountHistoryServiceImpl accountHistoryService;

    @Mock
    private AccountHistoryRepository accountHistoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AmountByCategoryRepository amountByCategoryRepository;

    @Nested
    @DisplayName("거래내역 전체 조회")
    class getAllAccountHistory {
        @Test
        void 성공_전체_거래내역을_조회할_때() {
            // given
            String account = "111-111-111";
            BDDMockito.given(accountHistoryRepository.findByMyAccount(account))
                .willReturn(List.of(accountHistory));

            // when
            List<AccountHistoryResponse> responses = accountHistoryService.getAllHistory(account);

            // then
            Mockito.verify(accountHistoryRepository, Mockito.times(1))
                .findByMyAccount(account);
            assertEquals(responses.size(), 1);
            assertEquals(responses.get(0).bhAmount(),accountHistory.getBhAmount());
        }

        @Test
        void 성공_해당계좌에_데이터가_없을_때() {
            // given
            String account = "111-111-112";
            BDDMockito.given(accountHistoryRepository.findByMyAccount(account))
                .willReturn(Collections.emptyList());

            // when
            List<AccountHistoryResponse> responses = accountHistoryService.getAllHistory(account);

            // then
            Mockito.verify(accountHistoryRepository, Mockito.times(1))
                .findByMyAccount(account);
            assertEquals(responses.size(), 0);
        }
    }

    @Nested
    @DisplayName("거래내역 상세 조회")
    class getAccountHistoryDetail {
        @Test
        void 성공_올바른_ID_값으로_조회할_때() {
            // given
            Long accountHistoryId = 1L;
            BDDMockito.given(accountHistoryRepository.findById(accountHistoryId))
                .willReturn(Optional.of(accountHistory));

            // when
            AccountHistoryDetailResponse response = accountHistoryService.getHistoryDetail(accountHistoryId);

            // then
            Mockito.verify(accountHistoryRepository, Mockito.times(1))
                .findById(accountHistoryId);
            assertEquals(response.historyId(), 1);
            assertEquals(response.bhMemo(), accountHistory.getBhMemo());
        }

        @Test
        void 실패_잘못된_ID_값으로_조회할_떄() {
            // given
            Long accountHistoryId = 2L;
            BDDMockito.given(accountHistoryRepository.findById(accountHistoryId))
                    .willReturn(Optional.empty());

            // when & then
            assertThrows(AccountHistoryException.class,
                () -> accountHistoryService.getHistoryDetail(accountHistoryId));
        }
    }

    @Nested
    @DisplayName("거래내역 메모 수정")
    class updateHistoryMemo {
        @Test
        void 성공_메모를_업데이트_할_때() {
            // given
            Long accountHistoryId = 1L;
            String memo = "memo";
            BDDMockito.given(accountHistoryRepository.findById(accountHistoryId))
                .willReturn(Optional.of(accountHistory));

            // when
            accountHistoryService.updateHistoryMemo(accountHistoryId, memo);

            // then
            Mockito.verify(accountHistoryRepository, Mockito.times(1))
                .findById(accountHistoryId);
            assertEquals(memo, accountHistory.getBhMemo());
        }

        @Test
        void 실패_잘못된_ID_값으로_조회할_떄() {
            // given
            Long accountHistoryId = 2L;
            BDDMockito.given(accountHistoryRepository.findById(accountHistoryId))
                .willReturn(Optional.empty());

            // when & then
            assertThrows(AccountHistoryException.class,
                () -> accountHistoryService.getHistoryDetail(accountHistoryId));
        }
    }

    @Nested
    @DisplayName("입금 기록")
    class addDepositTest {
        @Test
        void 성공_입금내역_저장() {
            // given
            DepositRequest request = new DepositRequest(
                "거래1",
                1000000L,
                "111-111-111",
                "333-333-333",
                "입금",
                1000000L,
                "급여",
                9
            );
            Category category = new Category(9, "급여");
            BDDMockito.given(categoryRepository.findById(request.categoryId()))
                .willReturn(Optional.of(category));
            BDDMockito.given(accountHistoryRepository.save(any()))
                    .willReturn(null);

            // when
            accountHistoryService.addDeposit(request);

            // then
            Mockito.verify(accountHistoryRepository, Mockito.times(1))
                .save(any());
        }
    }

    @Nested
    @DisplayName("출금 기록")
    class addWithdrawTest {
        @Test
        void 성공_출금내역_저장() {
            // given
            WithdrawRequest request = new WithdrawRequest(
                "거래1",
                10000L,
                "111-111-111",
                "333-333-333",
                "출금",
                1000000L,
                1,
                null,
                "카카오페이",
                1
            );
            Category category = new Category(1, "이체");
            BDDMockito.given(categoryRepository.findById(request.categoryId()))
                .willReturn(Optional.of(category));
            BDDMockito.given(accountHistoryRepository.save(any()))
                .willReturn(null);
            BDDMockito.given(amountByCategoryRepository.findByAbcAccountAndAbcCategory(
                    request.myAccount(), category.getCategoryName()))
                .willReturn(null);

            accountHistoryService.addWithdraw(request);

            Mockito.verify(accountHistoryRepository, Mockito.times(1))
                .save(any());
            Mockito.verify(amountByCategoryRepository, Mockito.times(1))
                .findByAbcAccountAndAbcCategory(request.myAccount(), category.getCategoryName());
        }


    }
}
