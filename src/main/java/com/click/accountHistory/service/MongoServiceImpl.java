package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.response.AccountHistoryMongoDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryMongoResponse;
import com.click.accountHistory.domain.mongo.AccountHistoryDocument;
import com.click.accountHistory.domain.repository.MongoHistoryRepository;
import com.click.accountHistory.exception.AccountHistoryErrorCode;
import com.click.accountHistory.exception.AccountHistoryException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoServiceImpl implements MongoService{

    private final MongoHistoryRepository mongoHistoryRepository;

    @Override
    public List<AccountHistoryMongoResponse> getAllPastHistory(String account, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<AccountHistoryDocument> byMyAccount = mongoHistoryRepository.findByMyAccountOrderByIdDesc(account, pageRequest);
        return byMyAccount.stream().map(AccountHistoryMongoResponse::from).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryMongoDetailResponse getPastDetailHistory(String id) {
        Optional<AccountHistoryDocument> byId = mongoHistoryRepository.findById(id);
        AccountHistoryDocument accountHistoryDocument =byId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.NO_ACCOUNT_HISTORY));

        // AccountHistoryDocument byIdAndMyAccount = mongoHistoryRepository.findByIdAndMyAccount(id,
        //     account);
        return AccountHistoryMongoDetailResponse.from(accountHistoryDocument);
    }
}
