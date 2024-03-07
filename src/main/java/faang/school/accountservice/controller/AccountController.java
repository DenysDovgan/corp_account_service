package faang.school.accountservice.controller;

import faang.school.accountservice.config.context.UserContext;
import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final UserContext userContext;
    private final AccountService accountService;

    @PostMapping("/account")
    public AccountDto openAccount(AccountDto accountDto) {
    return null;
    }

    @GetMapping("/account")
    public AccountDto getAccount() {
        return null;
    }
}
