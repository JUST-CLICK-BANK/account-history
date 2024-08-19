package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.response.AccountHistoryMongoDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryMongoResponse;
import com.click.accountHistory.domain.mongo.AccountHistoryDocument;
import java.util.List;

public interface MongoService {

    List<AccountHistoryMongoResponse> getAllPastHistory(String account, int page, int size);

    AccountHistoryMongoDetailResponse getPastDetailHistory(String id);

    void changeCategory(String id, Integer categoryId);

    void updateHistoryMemo(String id, String memo);
}
