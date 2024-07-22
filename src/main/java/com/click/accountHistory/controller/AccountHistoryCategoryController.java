package com.click.accountHistory.controller;

import com.click.accountHistory.service.AccountHistoryCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/histories")
public class AccountHistoryCategoryController {

    private final AccountHistoryCategoryService accountHistoryCategoryService;

    @PutMapping("/detail/{historyId}/category/{categoryId}")
    public void updateCategory(
        @PathVariable("historyId") Long historyId,
        @PathVariable("categoryId") Integer categoryId) {
        accountHistoryCategoryService.changeCategory(historyId, categoryId);
    }
}
