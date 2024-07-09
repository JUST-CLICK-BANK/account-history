package com.click.accountHistory.domain.repository;

import com.click.accountHistory.domain.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

}
