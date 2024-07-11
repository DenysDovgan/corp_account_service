package faang.school.accountservice.controller;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public AccountDto get(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @PostMapping("/open")
    public AccountDto open(@RequestBody AccountDto accountDto) {
        return accountService.openAccount(accountDto);
    }

    @PutMapping("/{id}/block")
    public void block(@PathVariable long id) {
        accountService.blockAccount(id);
    }

    @PutMapping("/{id}/activate")
    public void activate(@PathVariable long id) {
        accountService.activateAccount(id);
    }

    @PutMapping("/{id}/close")
    public void close(@PathVariable long id) {
        accountService.closeAccount(id);
    }
}
