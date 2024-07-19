package com.click.accountHistory.service;

import com.click.accountHistory.domain.dto.request.WithdrawRequest;

public interface AmountByCategoryService {

    void CalculatedCategoryAmount(WithdrawRequest withdrawRequest);
}
