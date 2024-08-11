package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.response.AccountHistoryMongoDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryMongoResponse;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.mongo.AccountHistoryDocument;
import com.click.accountHistory.domain.mongo.CategoryDocument;
import com.click.accountHistory.domain.repository.CategoryRepository;
import com.click.accountHistory.domain.repository.MongoHistoryRepository;
import com.click.accountHistory.exception.AccountHistoryErrorCode;
import com.click.accountHistory.exception.AccountHistoryException;
import jakarta.transaction.Transactional;
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
    private final CategoryRepository categoryRepository;

    @Override
    public List<AccountHistoryMongoResponse> getAllPastHistory(String account, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<AccountHistoryDocument> byMyAccount = mongoHistoryRepository.findByMyAccountOrderByHistoryIdDesc(account, pageRequest);
        return byMyAccount.stream().map(AccountHistoryMongoResponse::from).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryMongoDetailResponse getPastDetailHistory(Long id) {
        Optional<AccountHistoryDocument> byId = mongoHistoryRepository.findById(id);
        AccountHistoryDocument accountHistoryDocument =byId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.NO_ACCOUNT_HISTORY));

        // AccountHistoryDocument byIdAndMyAccount = mongoHistoryRepository.findByIdAndMyAccount(id,
        //     account);
        return AccountHistoryMongoDetailResponse.from(accountHistoryDocument);
    }

    @Transactional
    @Override
    public void changeCategory(Long id, Integer categoryId) {
        Optional<AccountHistoryDocument> byId = mongoHistoryRepository.findById(id);

        if(byId.isPresent()) {
            AccountHistoryDocument accountHistoryDocument = byId.get();

            Optional<Category> byCategoryId = categoryRepository.findById(categoryId);
            Category category = byCategoryId.orElseThrow(
                () -> new AccountHistoryException(AccountHistoryErrorCode.NO_CATEGORY));

            CategoryDocument categoryDocument = new CategoryDocument(category.getCategoryId(),
                category.getCategoryName());
            System.out.println(categoryDocument.getCategoryId() + " " + categoryDocument.getCategoryName());

            accountHistoryDocument.setCategoryId(categoryDocument);
            mongoHistoryRepository.save(accountHistoryDocument);
        } else {
            throw new AccountHistoryException(AccountHistoryErrorCode.NO_ACCOUNT_HISTORY);
        }

    }

    @Transactional
    @Override
    public void updateHistoryMemo(Long id, String memo) {
        Optional<AccountHistoryDocument> byId = mongoHistoryRepository.findById(id);
        AccountHistoryDocument accountHistoryDocument = byId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.NO_ACCOUNT_HISTORY));

        accountHistoryDocument.setBhMemo(memo);
        mongoHistoryRepository.save(accountHistoryDocument);
    }
}
