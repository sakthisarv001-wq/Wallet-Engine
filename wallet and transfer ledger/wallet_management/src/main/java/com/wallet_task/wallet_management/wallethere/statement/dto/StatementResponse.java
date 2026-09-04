package com.wallet_task.wallet_management.wallethere.statement.dto;

import com.wallet_task.wallet_management.wallethere.ledger.entity.Ledger;

import java.math.BigDecimal;
import java.util.List;

public record StatementResponse (
        Long walletId,
        BigDecimal totalCredits,
        BigDecimal totalDebits,
        BigDecimal currentBalance,
        List<Ledger> entries
){
}
