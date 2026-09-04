package com.wallet_task.wallet_management.wallethere.ledger.repository;

import com.wallet_task.wallet_management.wallethere.ledger.entity.Ledger;
import com.wallet_task.wallet_management.wallethere.ledger.entity.EntryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface LedgerEntryRepository
        extends JpaRepository<Ledger, Long> {

    Page<Ledger> findByWalletIdOrderByCreatedAtDesc(
            Long walletId,
            Pageable pageable
    );

    @Query("""
           SELECT COALESCE(SUM(l.amount), 0)
           FROM Ledger l
           WHERE l.wallet.id = :walletId
           AND l.entryType = 'CREDIT'
           AND l.createdAt BETWEEN :from AND :to
           """)
    BigDecimal calculateCredits(
            @Param("walletId") Long walletId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    @Query("""
           SELECT COALESCE(SUM(l.amount), 0)
           FROM Ledger l
           WHERE l.wallet.id = :walletId
           AND l.entryType = 'DEBIT'
           AND l.createdAt BETWEEN :from AND :to
           """)
    BigDecimal calculateDebits(
            @Param("walletId") Long walletId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}