package com.click.accountHistory.domain.repository;

import com.click.accountHistory.domain.dto.response.AccountHistoryResponse;
import com.click.accountHistory.domain.entity.AccountHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

    List<AccountHistory> findByMyAccount(String account);

    @Query("SELECT c.categoryName, SUM(a.bhAmount) " +
        "FROM AccountHistory a " +
        "JOIN a.categoryId c " +
        "WHERE a.myAccount = :myAccount and " +
        "a.bhStatus = '출금' and " +
        "FUNCTION('MONTH', a.bhAt) = :month " +
        "GROUP BY c.categoryName")
    List<Object[]> sumAmountByCategoryNameAndMonth(String month, String myAccount);
}
