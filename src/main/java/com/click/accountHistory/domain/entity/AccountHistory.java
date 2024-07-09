package com.click.accountHistory.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ACCOUNT_HISTORIES")
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HISTORY_ID")
    private Long historyId;

    @Column(name = "BH_AT")
    private LocalDateTime bhAt;

    @Column(name = "BH_AMOUNT")
    private Long bhAmount;

    @Column(name = "MY_ACCOUNT")
    private String myAccount;

    @Column(name = "YOUR_ACCOUNT")
    private String yourAccount;

    @Column(name = "BH_STATUS")
    private String bhStatus;

    @Column(name = "BH_OUT_TYPE")
    private Integer bhOutType; //TODO : Enum 으로 수정

    @Column(name = "CARD_ID")
    private Long cardId;

    @Column(name = "BH_NAME")
    private String bhName;

    @Column(name = "BH_MEMO")
    private String bhMemo;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category categoryId;
}
