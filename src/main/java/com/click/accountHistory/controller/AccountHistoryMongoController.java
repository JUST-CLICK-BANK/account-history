package com.click.accountHistory.controller;

import com.click.accountHistory.domain.dto.response.AccountHistoryMongoDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryMongoResponse;
import com.click.accountHistory.domain.mongo.AccountHistoryDocument;
import com.click.accountHistory.service.MongoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/histories")
public class AccountHistoryMongoController {

    private final MongoService mongoService;

    @GetMapping("/past")
    public List<AccountHistoryMongoResponse> getPastHistory(
        @RequestParam("account") String account,
        @RequestParam("page") int page,
        @RequestParam("size") int size
    ) {
        return mongoService.getAllPastHistory(account, page, size);
    }

    @GetMapping("/past/{id}")
    public AccountHistoryMongoDetailResponse getPastDetailHistory(
        @PathVariable String id
        // @RequestParam("account") String account
    ){
        return mongoService.getPastDetailHistory(id);
    }

    @PutMapping("/past/{id}/category/{categoryId}")
    public void updatePastCategory(
        @PathVariable("id") String id,
        @PathVariable("categoryId") Integer categoryId) {
        mongoService.changeCategory(id, categoryId);
    }

    @PutMapping("/past/{id}/memo")
    public void updatePastMemo(
        @PathVariable("id") String id,
        @RequestBody(required = false) String memo) {
        mongoService.updateHistoryMemo(id, memo);
    }
}
