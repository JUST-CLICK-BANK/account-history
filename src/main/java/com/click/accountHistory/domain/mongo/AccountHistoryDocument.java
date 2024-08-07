package com.click.accountHistory.domain.mongo;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "PastRecord")
public class AccountHistoryDocument {

    @Id
    private String id;
    private LocalDateTime bhAt;
    private String bhName;
    private Long bhAmount;
    private String myAccount;
    private String bhStatus;
    private Long bhBalance;
    private String bhOutType;
    private Long cardId;
    private String bhMemo;
    private String categoryName;
}