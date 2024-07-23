package com.click.accountHistory.domain.repository;

import com.click.accountHistory.domain.entity.AmountByCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AmountByCategoryRepository extends JpaRepository<AmountByCategory, Long> {

    AmountByCategory findByAbcAccountAndAbcCategory(String abcAccount, String abcCategory);

    List<AmountByCategory> findByAbcAccount(String myAccount);

    @Query("SELECT SUM(a.abcAmount) "
        + "FROM AmountByCategory a "
        + "WHERE a.abcAccount = :abcAccount")
    Long sumAmountsByAccount(String abcAccount);
}
