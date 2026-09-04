package com.wallet_task.wallet_management.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/ui/wallet")
    public String wallet() {
        return "wallet";
    }

    @GetMapping("/ui/transfer")
    public String transfer() {
        return "transfer";
    }

    @GetMapping("/ui/transfers")
    public String transfers() {
        return "transfers";
    }

    @GetMapping("/ui/statement")
    public String statement() {
        return "statement";
    }
}
