package com.wallet_task.wallet_management.wallethere.transfer.dto;

import com.wallet_task.wallet_management.wallethere.transfer.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponse (
        Long id,
        String idempotencyKey,
        Long senderWalletId,
        Long receiverWalletId,
        BigDecimal amount,
        TransferStatus status,
        LocalDateTime createdAt
){
}
