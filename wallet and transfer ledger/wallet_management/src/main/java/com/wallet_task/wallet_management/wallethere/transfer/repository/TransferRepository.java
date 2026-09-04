package com.wallet_task.wallet_management.wallethere.transfer.repository;

import com.wallet_task.wallet_management.wallethere.transfer.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    Optional<Transfer> findByIdempotencyKey(String key);

    Page<Transfer> findAllByCreatedAtBetween(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    );
}
