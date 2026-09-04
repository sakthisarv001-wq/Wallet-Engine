package com.wallet_task.wallet_management.wallethere.transfer.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull
        Long senderWalletId,

        @NotNull
        Long receiverWalletId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount
) {
}
