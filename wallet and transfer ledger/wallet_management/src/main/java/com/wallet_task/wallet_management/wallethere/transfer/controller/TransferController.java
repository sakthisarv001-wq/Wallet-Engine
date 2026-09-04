package com.wallet_task.wallet_management.wallethere.transfer.controller;

import com.wallet_task.wallet_management.wallethere.transfer.dto.TransferRequest;
import com.wallet_task.wallet_management.wallethere.transfer.dto.TransferResponse;
import com.wallet_task.wallet_management.wallethere.transfer.entity.Transfer;
import com.wallet_task.wallet_management.wallethere.transfer.service.TransferHistoryService;
import com.wallet_task.wallet_management.wallethere.transfer.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/transfers")
public class TransferController {
    private final TransferService transferService;
    private final TransferHistoryService historyService;

    public TransferController(
            TransferService transferService,
            TransferHistoryService historyService
    ) {
        this.transferService = transferService;
        this.historyService = historyService;
    }

    @PostMapping
    public TransferResponse createTransfer(
            @RequestHeader("Idempotency-Key")
            String idempotencyKey,

            @Valid
            @RequestBody TransferRequest request
    ) {

        return transferService.transfer(
                idempotencyKey,
                request
        );
    }

    @GetMapping
    public Page<Transfer> getTransfers(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(required = false)
            LocalDateTime from,

            @RequestParam(required = false)
            LocalDateTime to
    ) {

        return historyService.getTransfers(
                page,
                size,
                from,
                to
        );
    }

}
