package com.click.accountHistory.domain.repository;

import com.click.accountHistory.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
