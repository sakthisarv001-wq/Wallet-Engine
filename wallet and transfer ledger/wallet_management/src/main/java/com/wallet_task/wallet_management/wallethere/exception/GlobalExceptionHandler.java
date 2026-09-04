package com.wallet_task.wallet_management.wallethere.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // Wallet not found
    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleWalletNotFound(
            WalletNotFoundException ex) {


        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", 404);
        response.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


    // User not found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(
            UserNotFoundException ex) {


        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", 404);
        response.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


    // Transfer related errors
    @ExceptionHandler(TransferException.class)
    public ResponseEntity<Map<String, Object>> handleTransferException(
            TransferException ex) {


        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", 404);
        response.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

}
