package com.wallet_task.wallet_management.wallethere.statement.service;

import com.wallet_task.wallet_management.wallethere.exception.WalletNotFoundException;
import com.wallet_task.wallet_management.wallethere.ledger.entity.Ledger;
import com.wallet_task.wallet_management.wallethere.ledger.repository.LedgerEntryRepository;
import com.wallet_task.wallet_management.wallethere.statement.dto.StatementResponse;
import com.wallet_task.wallet_management.wallethere.wallet.entity.Wallet;
import com.wallet_task.wallet_management.wallethere.wallet.repository.WalletRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatementService {

    private final WalletRepository walletRepository;
    private final LedgerEntryRepository ledgerRepository;

    public StatementService(
            WalletRepository walletRepository,
            LedgerEntryRepository ledgerRepository
    ) {
        this.walletRepository = walletRepository;
        this.ledgerRepository = ledgerRepository;
    }

    public StatementResponse getStatement(
            Long walletId,
            LocalDateTime from,
            LocalDateTime to
    ) {

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() ->
                        new WalletNotFoundException(
                                "Wallet not found"));

        if (from == null) {
            from = LocalDateTime.of(1970, 1, 1, 0, 0);
        }

        if (to == null) {
            to = LocalDateTime.now();
        }

        BigDecimal credits =
                ledgerRepository.calculateCredits(
                        walletId,
                        from,
                        to
                );

        BigDecimal debits =
                ledgerRepository.calculateDebits(
                        walletId,
                        from,
                        to
                );

        List<Ledger> entries =
                ledgerRepository
                        .findByWalletIdOrderByCreatedAtDesc(
                                walletId,
                                PageRequest.of(0, 100)
                        )
                        .getContent();

        return new StatementResponse(
                walletId,
                credits,
                debits,
                wallet.getBalance(),
                entries
        );
    }

}
