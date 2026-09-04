package com.wallet_task.wallet_management.wallethere.user.controller;

import com.wallet_task.wallet_management.wallethere.user.enetity.User;
import com.wallet_task.wallet_management.wallethere.user.repository.UserRepository;
import com.wallet_task.wallet_management.wallethere.wallet.entity.Wallet;
import com.wallet_task.wallet_management.wallethere.wallet.repository.WalletRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public UserController(
            UserRepository userRepository,
            WalletRepository walletRepository
    ) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    @PostMapping
    public User createUser(
            @RequestBody User user
    ) {

        User savedUser = userRepository.save(user);

        Wallet wallet = new Wallet(
                savedUser,
                BigDecimal.ZERO
        );

        walletRepository.save(wallet);

        return savedUser;
    }

}
