package com.click.accountHistory.service;

import static org.springframework.data.mongodb.core.aggregation.AggregationUpdate.update;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.click.accountHistory.domain.dto.response.AccountHistoryMongoDetailResponse;
import com.click.accountHistory.domain.dto.response.AccountHistoryMongoResponse;
import com.click.accountHistory.domain.entity.AmountByCategory;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.mongo.AccountHistoryDocument;
import com.click.accountHistory.domain.mongo.CategoryDocument;
import com.click.accountHistory.domain.repository.AmountByCategoryRepository;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoServiceImpl implements MongoService{

    private final MongoHistoryRepository mongoHistoryRepository;
    private final CategoryRepository categoryRepository;
    private final MongoTemplate mongoTemplate;
    private final AccountHistoryCategoryServiceImpl accountHistoryCategoryServiceImpl;
    private final AmountByCategoryRepository amountByCategoryRepository;

    @Override
    public List<AccountHistoryMongoResponse> getAllPastHistory(String account, int page, int size) {
        // PageRequest pageRequest = PageRequest.of(page, size);
        // Page<AccountHistoryDocument> byMyAccount = mongoHistoryRepository.findByMyAccountOrderByHistoryIdDesc(account, pageRequest);
        // return byMyAccount.stream().map(AccountHistoryMongoResponse::from).collect(Collectors.toList());
        List<AccountHistoryDocument> allHistories = mongoHistoryRepository.findByMyAccountOrderByBhAtDesc(account);

        // 가져온 데이터 중에서 필요한 부분만을 페이징 처리합니다.
        int start = Math.min(page * size, allHistories.size());
        int end = Math.min((page + 1) * size, allHistories.size());

        // 해당 범위의 데이터를 반환합니다.
        List<AccountHistoryDocument> pagedHistories = allHistories.subList(start, end);

        return pagedHistories.stream().map(AccountHistoryMongoResponse::from).collect(Collectors.toList());

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

    @Transactional
    @Override
    public void changeCategory(String id, Integer categoryId) {

        AccountHistoryDocument accountHistoryDocument = mongoHistoryRepository.findById(id)
            .orElseThrow(
                () -> new AccountHistoryException(AccountHistoryErrorCode.NO_ACCOUNT_HISTORY));

        String account = accountHistoryDocument.getMyAccount();
        Long amount = accountHistoryDocument.getBhAmount();
        Category before = categoryRepository.findById(accountHistoryDocument.getCategoryId().getCategoryId()).orElseThrow(() -> new AccountHistoryException(AccountHistoryErrorCode.NO_CATEGORY));

        Optional<Category> byCategoryId = categoryRepository.findById(categoryId);
        Category category = byCategoryId.orElseThrow(
            () -> new AccountHistoryException(AccountHistoryErrorCode.NO_CATEGORY));

        // CategoryDocument categoryDocument = new CategoryDocument(category.getCategoryId(),
        //     category.getCategoryName());

        if(accountHistoryDocument.getBhStatus().equals("출금")){
            AmountByCategory byCategory = amountByCategoryRepository.findByAbcAccountAndAbcCategoryAndAbcDisableTrue(
                account, before.getCategoryName());

            if (byCategory != null){
                byCategory.setAbcAmount(byCategory.getAbcAmount() - amount);
            }

            AmountByCategory amountByCategory = amountByCategoryRepository.findByAbcAccountAndAbcCategory(
                account, category.getCategoryName());

            if (amountByCategory == null) {
                AmountByCategory newData = new AmountByCategory(null, account, category.getCategoryName(), amount, true);
                amountByCategoryRepository.save(newData);
            } else {
                amountByCategory.setAbcAmount(amountByCategory.getAbcAmount() + amount);
            }
        }

        Query query = query(where("_id").is(id));
        Update update = new Update().set("categoryId", category);
        mongoTemplate.updateFirst(query, update, AccountHistoryDocument.class);

    }

    @Transactional
    @Override
    public void updateHistoryMemo(String id, String memo) {
        Query query = new Query(where("_id").is(id));
        Update update = new Update();

        if (memo == null || memo.isEmpty()) {
            // memo가 빈 문자열이면 필드를 삭제
            update.unset("bhMemo");
        } else {
            // memo가 빈 문자열이 아니면 필드에 값을 설정
            update.set("bhMemo", memo);
        }

        mongoTemplate.updateFirst(query, update, AccountHistoryDocument.class);
    }
}
