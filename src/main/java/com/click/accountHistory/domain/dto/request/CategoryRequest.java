package com.click.accountHistory.domain.dto.request;

import com.click.accountHistory.domain.entity.AmountByCategory;

public record CategoryRequest(
    String abcAccount,
    String abcCategory,
    Long abcAmount
) {
    public AmountByCategory toEntity() {
        return AmountByCategory.builder()
            .abcAccount(abcAccount)
            .abcCategory(abcCategory)
            .abcAmount(abcAmount)
            .abcDisable(true)
            .build();
    }
}
