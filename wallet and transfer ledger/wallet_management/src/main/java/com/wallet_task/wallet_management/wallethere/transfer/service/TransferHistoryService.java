package com.wallet_task.wallet_management.wallethere.transfer.service;

import com.wallet_task.wallet_management.wallethere.transfer.entity.Transfer;
import com.wallet_task.wallet_management.wallethere.transfer.repository.TransferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransferHistoryService {
    private final TransferRepository repository;

    public TransferHistoryService(
            TransferRepository repository
    ) {
        this.repository = repository;
    }

    public Page<Transfer> getTransfers(
            int page,
            int size,
            LocalDateTime from,
            LocalDateTime to
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        if (from != null && to != null) {
            return repository.findAllByCreatedAtBetween(
                    from,
                    to,
                    pageable
            );
        }

        return repository.findAll(pageable);
    }

}
