package com.click.accountHistory.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ACCOUNT_MONTH_BUDGET")
public class AccountMonthBudget {

    @Id
    @Column(name = "MB_ACCOUNT")
    private String mbAccount;

    @Column(name = "MB_BUDGET") @Setter
    private Long mbBudget;

    @Column(name = "MB_EXPENDITURE") @Setter
    private Long mbExpenditure;
}
