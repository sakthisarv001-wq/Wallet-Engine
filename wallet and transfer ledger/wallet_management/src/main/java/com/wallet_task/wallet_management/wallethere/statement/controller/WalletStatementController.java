package com.wallet_task.wallet_management.wallethere.statement.controller;

import com.wallet_task.wallet_management.wallethere.statement.dto.StatementResponse;
import com.wallet_task.wallet_management.wallethere.statement.service.StatementService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/wallets")
public class WalletStatementController {
    private final StatementService statementService;

    public WalletStatementController(
            StatementService statementService
    ) {
        this.statementService = statementService;
    }

    @GetMapping("/{id}/statement")
    public StatementResponse getStatement(
            @PathVariable Long id,

            @RequestParam(required = false)
            LocalDateTime from,

            @RequestParam(required = false)
            LocalDateTime to
    ) {

        return statementService.getStatement(
                id,
                from,
                to
        );
    }

}
