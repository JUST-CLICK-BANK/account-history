package com.click.accountHistory.service;

import static org.junit.jupiter.api.Assertions.*;

import com.click.accountHistory.domain.entity.AccountMonthBudget;
import com.click.accountHistory.domain.repository.AccountMonthBudgetRepository;
import com.click.accountHistory.domain.repository.AmountByCategoryRepository;
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

@ExtendWith(MockitoExtension.class)
class AccountHistoryStatisticsServiceImplTest {

    @InjectMocks
    private AccountHistoryStatisticsServiceImpl accountHistoryStatisticsService;

    @Mock
    private AccountMonthBudgetRepository accountMonthBudgetRepository;

    @Mock
    private AmountByCategoryRepository amountByCategoryRepository;

    @Nested
    class getBudget {
        @Test
        void 성공_예산을_처음_조회() {
            // given
            String myAccount = "110-486-119643";
            BDDMockito.given(accountMonthBudgetRepository.findById(myAccount))
                .willReturn(Optional.of(new AccountMonthBudget("110-486-119643", 0L, 0L)));

            // when
            AccountMonthBudget budgetByAccount = accountHistoryStatisticsService.getBudgetByAccount(
                myAccount);

            Mockito.verify(accountMonthBudgetRepository, Mockito.times(1))
                .findById(myAccount);
            assertEquals(myAccount, budgetByAccount.getMbAccount());
            assertEquals(0L, budgetByAccount.getMbBudget());
            assertEquals(0L, budgetByAccount.getMbExpenditure());
        }

        @Test
        void 성공_예산_조회가_처음이_아닐_때() {
            // given
            AccountMonthBudget accountMonthBudget = new AccountMonthBudget("110-486-119643", 3000000L, 0L);
            String myAccount = "110-486-119643";
            BDDMockito.given(accountMonthBudgetRepository.findById(myAccount))
                .willReturn(Optional.of(accountMonthBudget));

            // when
            AccountMonthBudget budgetByAccount = accountHistoryStatisticsService.getBudgetByAccount(myAccount);

            Mockito.verify(accountMonthBudgetRepository, Mockito.times(1))
                .findById(myAccount);
            assertEquals(3000000L, budgetByAccount.getMbBudget());
        }
    }

    // @Nested
    // class updateBudget {
    //     @Test
    //     void 성공_
    // }
}