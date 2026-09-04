package com.wallet_task.wallet_management;

import org.springframework.boot.SpringApplication;

public class TestWalletManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(WalletManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
