package com.click.accountHistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccountHistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountHistoryApplication.class, args);
    }

}
