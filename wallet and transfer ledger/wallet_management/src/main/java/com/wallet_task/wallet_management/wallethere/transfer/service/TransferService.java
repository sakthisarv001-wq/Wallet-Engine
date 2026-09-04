package com.wallet_task.wallet_management.wallethere.transfer.service;

import com.wallet_task.wallet_management.wallethere.exception.TransferException;
import com.wallet_task.wallet_management.wallethere.exception.UserNotFoundException;
import com.wallet_task.wallet_management.wallethere.ledger.entity.EntryType;
import com.wallet_task.wallet_management.wallethere.ledger.entity.Ledger;
import com.wallet_task.wallet_management.wallethere.ledger.repository.LedgerEntryRepository;
import com.wallet_task.wallet_management.wallethere.transfer.TransferStatus;
import com.wallet_task.wallet_management.wallethere.transfer.dto.TransferRequest;
import com.wallet_task.wallet_management.wallethere.transfer.dto.TransferResponse;
import com.wallet_task.wallet_management.wallethere.transfer.entity.Transfer;
import com.wallet_task.wallet_management.wallethere.transfer.repository.TransferRepository;
import com.wallet_task.wallet_management.wallethere.wallet.entity.Wallet;
import com.wallet_task.wallet_management.wallethere.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferService {

    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    public TransferService(
            WalletRepository walletRepository,
            TransferRepository transferRepository,
            LedgerEntryRepository ledgerEntryRepository
    ) {
        this.walletRepository = walletRepository;
        this.transferRepository = transferRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @Transactional
    public TransferResponse transfer(
            String idempotencyKey,
            TransferRequest request
    ) {

        // 1. Check duplicate request
        var existing = transferRepository
                .findByIdempotencyKey(idempotencyKey);

        if (existing.isPresent()) {
            return toResponse(existing.get());
        }

        // 2. Validate sender and receiver
        if (request.senderWalletId()
                .equals(request.receiverWalletId())) {

            throw new IllegalArgumentException(
                    "Sender and receiver cannot be same"
            );
        }

        if (request.amount()
                .compareTo(BigDecimal.ZERO) <= 0) {

            throw new TransferException(
                    "Amount must be greater than zero"
            );
        }

        // 3. Lock sender wallet
        Wallet sender = walletRepository
                .findByIdForUpdate(request.senderWalletId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Sender wallet not found"));

        // 4. Lock receiver wallet
        Wallet receiver = walletRepository
                .findByIdForUpdate(request.receiverWalletId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Receiver wallet not found"));

        // 5. Check balance
        if (sender.getBalance()
                .compareTo(request.amount()) < 0) {

            throw new TransferException(
                    "Insufficient balance"
            );
        }

        // 6. Debit sender
        sender.setBalance(
                sender.getBalance()
                        .subtract(request.amount())
        );

        // 7. Credit receiver
        receiver.setBalance(
                receiver.getBalance()
                        .add(request.amount())
        );

        walletRepository.save(sender);
        walletRepository.save(receiver);

        // 8. Create transfer
        Transfer transfer = new Transfer();

        transfer.setIdempotencyKey(idempotencyKey);
        transfer.setSenderWallet(sender);
        transfer.setReceiverWallet(receiver);
        transfer.setAmount(request.amount());
        transfer.setStatus(TransferStatus.SUCCESS);
        transfer.setCreatedAt(LocalDateTime.now());

        Transfer savedTransfer =
                transferRepository.save(transfer);

        // 9. Create DEBIT ledger entry
        Ledger debit = new Ledger(
                savedTransfer,
                sender,
                EntryType.DEBIT,
                request.amount()
        );

        // 10. Create CREDIT ledger entry
        Ledger credit = new Ledger(
                savedTransfer,
                receiver,
                EntryType.CREDIT,
                request.amount()
        );

        ledgerEntryRepository.save(debit);
        ledgerEntryRepository.save(credit);

        return toResponse(savedTransfer);
    }

    private TransferResponse toResponse(
            Transfer transfer
    ) {

        return new TransferResponse(
                transfer.getId(),
                transfer.getIdempotencyKey(),
                transfer.getSenderWallet().getId(),
                transfer.getReceiverWallet().getId(),
                transfer.getAmount(),
                transfer.getStatus(),
                transfer.getCreatedAt()
        );
    }

}
