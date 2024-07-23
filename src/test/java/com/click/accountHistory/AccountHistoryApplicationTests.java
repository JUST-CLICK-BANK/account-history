package com.click.accountHistory;

import com.click.accountHistory.domain.entity.AccountMonthBudget;
import com.click.accountHistory.domain.entity.AmountByCategory;
import com.click.accountHistory.domain.repository.AccountMonthBudgetRepository;
import com.click.accountHistory.domain.repository.AmountByCategoryRepository;
import com.click.accountHistory.service.AccountHistoryStatisticsServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class AccountHistoryApplicationTests {

	@Autowired
	private AccountHistoryStatisticsServiceImpl accountHistoryStatisticsService;

	@Autowired
	private AccountMonthBudgetRepository accountMonthBudgetRepository;

	@Autowired
	private AmountByCategoryRepository amountByCategoryRepository;

	@BeforeEach
	void setUp() {
		accountMonthBudgetRepository.save(new AccountMonthBudget("111-111-111", 1000000L,0L));
		amountByCategoryRepository.save(new AmountByCategory(1L,"111-111-111","교통",10000L));
	}

	@Test
	void contextLoads() {
		AccountMonthBudget budgetByAccount = accountHistoryStatisticsService.getBudgetByAccount(
			"111-111-111");

		Assertions.assertEquals(1000000L, budgetByAccount.getMbBudget());
		System.out.println(budgetByAccount.getMbBudget());
	}

}
