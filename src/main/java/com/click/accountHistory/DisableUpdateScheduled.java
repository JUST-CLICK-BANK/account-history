package com.click.accountHistory;

import com.click.accountHistory.domain.entity.AmountByCategory;
import com.click.accountHistory.domain.repository.AmountByCategoryRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
public class DisableUpdateScheduled {

    private final AmountByCategoryRepository amountByCategoryRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void disableUpdate() {

        List<AmountByCategory> byAbcDisableTrue = amountByCategoryRepository.findByAbcDisableTrue();

        for (AmountByCategory abc : byAbcDisableTrue) {
            abc.setAbcDisable(false);
        }
    }
}
