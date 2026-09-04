package com.wallet_task.wallet_management.wallethere.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletResponse(
        Long id,
        Long userId,
        BigDecimal balance,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}