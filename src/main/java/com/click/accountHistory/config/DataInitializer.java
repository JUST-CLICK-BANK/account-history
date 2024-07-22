package com.click.accountHistory.config;

import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final CategoryRepository categoryRepository;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category(0, "기타"));
                categoryRepository.save(new Category(1, "식비"));
                categoryRepository.save(new Category(2, "생활"));
                categoryRepository.save(new Category(3, "쇼핑"));
                categoryRepository.save(new Category(4, "교통"));
                categoryRepository.save(new Category(5, "의료/건강"));
                categoryRepository.save(new Category(6, "문화/여가"));
                categoryRepository.save(new Category(7, "교육"));
                categoryRepository.save(new Category(8, "경조/선물"));
                categoryRepository.save(new Category(9, "수입"));
            }
        };
    }
}
