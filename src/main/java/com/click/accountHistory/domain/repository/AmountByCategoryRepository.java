package com.click.accountHistory.domain.repository;

import com.click.accountHistory.domain.entity.AmountByCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountByCategoryRepository extends JpaRepository<AmountByCategory, Long> {

    AmountByCategory findByAbcAccountAndAbcCategory(String abcAccount, String abcCategory);

    List<AmountByCategory> findByAbcAccount(String myAccount);

}
