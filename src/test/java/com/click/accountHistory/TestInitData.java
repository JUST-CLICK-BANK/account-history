package com.click.accountHistory;

import com.click.accountHistory.domain.entity.AccountHistory;
import com.click.accountHistory.domain.entity.Category;
import com.click.accountHistory.domain.type.TransactionType;
import java.time.LocalDateTime;

public class TestInitData {

    protected final AccountHistory accountHistory;

    public TestInitData() {
        this.accountHistory = new AccountHistory(
            1L,
            LocalDateTime.now(),
            "abc",
            10000L,
            "111-111-111",
            "333-333-333",
            "출금",
            10000000L,
            TransactionType.TRANSFER,
            null,
            "카카오페이",
            null,
            null
        );
    }
}
