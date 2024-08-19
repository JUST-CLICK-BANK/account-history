package com.click.accountHistory.domain.mongo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "PastRecord")
public class AccountHistoryDocument {

    @Id
    @Field("_id")
    private String historyId;
    private LocalDateTime bhAt;
    private String bhName;
    private Long bhAmount;
    private String myAccount;
    private String bhStatus;
    private Long bhBalance;
    private String bhOutType;
    private Long cardId;

    @Setter
    private String bhMemo;
    @Setter
    private CategoryDocument categoryId;
}