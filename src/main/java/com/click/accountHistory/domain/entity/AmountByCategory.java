package com.click.accountHistory.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "AMOUNT_BY_CATEGORY")
public class AmountByCategory {

    // 이하 필드명에 있는 ABC 는 Amount_By_Category(카테고리별 지출) 의 약자입니다.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ABC_ID")
    private Long abcId;

    @Column(name = "ABC_ACCOUNT")
    private String abcAccount;

    @Column(name = "ABC_CATEGORY")
    private String abcCategory;

    @Column(name = "ABC_AMOUNT") @Setter
    private Long abcAmount;

    @Column(name = "ABC_DISABLE") @Setter
    private Boolean abcDisable = true;
}
