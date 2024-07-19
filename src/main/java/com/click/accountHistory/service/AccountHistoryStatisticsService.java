package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.request.WithdrawRequest;
import java.util.Map;

public interface AccountHistoryStatisticsService {

    Map<String, Long> historyStatistics(String myAccount);

}