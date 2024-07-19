package com.click.accountHistory.controller;

import com.click.accountHistory.service.AccountHistoryStatisticsService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/histories")
public class AccountHistoryStatisticsController {

    private final AccountHistoryStatisticsService accountHistoryStatisticsService;

    @GetMapping("/statistics")
    public Map<String, Long> historyStatistics(
        // @RequestParam("month") String month,
        @RequestParam("account") String myAccount) {
        return accountHistoryStatisticsService.historyStatistics(myAccount);
        // return accountHistoryStatisticsService.historyStatistics(month, myAccount);
    }

}
