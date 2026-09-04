package com.wallet_task.wallet_management.wallethere.wallet.controller;

import com.wallet_task.wallet_management.wallethere.wallet.dto.WalletResponse;
import com.wallet_task.wallet_management.wallethere.wallet.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;

    public WalletController(
            WalletService walletService
    ) {
        this.walletService = walletService;
    }

    @GetMapping("/{id}")
    public WalletResponse getWallet(@PathVariable Long id) {
        return walletService.getWallet(id);
    }
}
