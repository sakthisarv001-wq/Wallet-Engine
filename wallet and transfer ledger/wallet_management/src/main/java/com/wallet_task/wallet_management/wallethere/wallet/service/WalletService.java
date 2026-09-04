package com.wallet_task.wallet_management.wallethere.wallet.service;

import com.wallet_task.wallet_management.wallethere.exception.WalletNotFoundException;
import com.wallet_task.wallet_management.wallethere.wallet.dto.WalletResponse;
import com.wallet_task.wallet_management.wallethere.wallet.entity.Wallet;
import com.wallet_task.wallet_management.wallethere.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional(readOnly = true)
    public WalletResponse getWallet(Long id) {

        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() ->
                        new WalletNotFoundException("Wallet not found"));

        return new WalletResponse(
                wallet.getId(),
                wallet.getUser().getId(),
                wallet.getBalance(),
                wallet.getCreatedAt(),
                wallet.getUpdatedAt()
        );
    }
}
