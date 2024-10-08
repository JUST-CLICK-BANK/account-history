package com.click.accountHistory.domain.repository;

import com.click.accountHistory.domain.dto.response.AccountHistoryMongoResponse;
import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.mongo.AccountHistoryDocument;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface MongoHistoryRepository extends MongoRepository<AccountHistoryDocument, String> {

    Page<AccountHistoryDocument> findByMyAccountOrderByBhAtDesc(String account, PageRequest pageRequest);

    // Page<AccountHistoryDocument> findByMyAccountOrderByHistoryIdDesc(String account, Pageable pageable);

    // List<AccountHistoryDocument> findByMyAccountOrderByBhAtDesc(String account);

    // AccountHistoryDocument findByIdAndMyAccount(Long id, String account);



}
