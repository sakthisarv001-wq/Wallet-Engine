package com.wallet_task.wallet_management.wallethere.ledger.entity;


import com.wallet_task.wallet_management.wallethere.transfer.entity.Transfer;
import com.wallet_task.wallet_management.wallethere.wallet.entity.Wallet;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger_entries")
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transfer_id", nullable = false)
    private Transfer transfer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type",nullable = false)
    private EntryType entryType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    public Ledger() {
    }

    public Ledger(
            Transfer transfer,
            Wallet wallet,
            EntryType entryType,
            BigDecimal amount
    ) {
        this.transfer = transfer;
        this.wallet = wallet;
        this.entryType = entryType;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}