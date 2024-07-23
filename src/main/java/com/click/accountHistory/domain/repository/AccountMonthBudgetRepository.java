package com.click.accountHistory.domain.repository;

import com.click.accountHistory.domain.entity.AccountMonthBudget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountMonthBudgetRepository extends JpaRepository<AccountMonthBudget, String> {

}
