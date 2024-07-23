package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.request.WithdrawRequest;
import com.click.accountHistory.domain.entity.AccountMonthBudget;
import java.util.Map;

public interface AccountHistoryStatisticsService {

    Map<String, Long> historyStatistics(String myAccount);

    AccountMonthBudget getBudgetByAccount(String myAccount);

    void updateBudgetByAccount(String myAccount, Long budget);
}