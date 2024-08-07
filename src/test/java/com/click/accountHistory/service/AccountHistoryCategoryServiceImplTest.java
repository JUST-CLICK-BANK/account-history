package com.click.accountHistory.service;

import static org.junit.jupiter.api.Assertions.*;

import com.click.accountHistory.TestInitData;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.repository.AccountHistoryRepository;
import com.click.accountHistory.domain.repository.CategoryRepository;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountHistoryCategoryServiceImplTest extends TestInitData {

    @InjectMocks
    private AccountHistoryCategoryServiceImpl accountHistoryCategoryService;

    @Mock
    private AccountHistoryRepository accountHistoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Nested
    class updateCategory {
        @Test
        void 성공_카테고리_수정_성공() {
            Long historyId = 1L;
            Integer categoryId = 1;
            Category category = new Category(categoryId, "식비");
            BDDMockito.given(accountHistoryRepository.findById(historyId))
                .willReturn(Optional.of(accountHistory));
            BDDMockito.given(categoryRepository.findById(categoryId))
                .willReturn(Optional.of(category));

            accountHistoryCategoryService.changeCategory(historyId, categoryId);

            assertEquals(accountHistory.getCategoryId().getCategoryName(), "식비");
            assertEquals(accountHistory.getCategoryId(), category);
        }
    }
}