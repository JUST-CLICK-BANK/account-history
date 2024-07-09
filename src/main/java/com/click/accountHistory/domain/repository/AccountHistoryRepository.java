package com.click.accountHistory.domain.repository;

import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import com.click.accountHistory.domain.entity.AccountHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

    List<AccountHistory> findByMyAccount(String account);
}
