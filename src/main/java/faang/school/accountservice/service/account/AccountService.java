package faang.school.accountservice.service.account;

import faang.school.accountservice.dto.account.AccountDto;
import faang.school.accountservice.exception.ExceptionMessages;
import faang.school.accountservice.mapper.account.AccountMapper;
import faang.school.accountservice.model.AccountStatus;
import faang.school.accountservice.model.OwnerType;
import faang.school.accountservice.model.account.Account;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.service.EnumConverter;
import faang.school.accountservice.validator.account.AccountValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountValidator accountValidator;
    private final EnumConverter enumConverter;

    @Transactional
    public List<AccountDto> getAllAccounts(long ownerId, String owner) {
        OwnerType newOwner = enumConverter.checkEnumAndTransformation(owner, OwnerType.class);

        List<Account> accounts = accountRepository.findAllByOwnerIdAndOwner(ownerId, newOwner);
        return accounts.stream().map(accountMapper::toDto).toList();
    }

    @Transactional
    public AccountDto getAccount(String number) {
        return accountMapper.toDto(findAccountAndValid(number));
    }

    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        accountValidator.checkExistenceOfTheNumber(accountDto);

        Account account = accountMapper.toEntity(accountDto);
        accountRepository.save(account);

        return accountMapper.toDto(account);
    }

    @Transactional
    public AccountDto updateStatusAccount(String number, String status) {
        Account account = findAccountAndValid(number);
        AccountStatus newStatus = enumConverter.checkEnumAndTransformation(status, AccountStatus.class);

        if (account.getAccountStatus() == AccountStatus.CLOSED) {
            log.error(ExceptionMessages.CHANGE_STATUS_EXCEPTION + number);
            throw new IllegalArgumentException(ExceptionMessages.CHANGE_STATUS_EXCEPTION + number);
        }

        if (!account.getAccountStatus().equals(newStatus)) {
            account.setAccountStatus(newStatus);
            if (newStatus == AccountStatus.CLOSED) {
                account.setCloseAt(LocalDateTime.now());
            }
            accountRepository.save(account);
            log.info("Статус аккаунта с номером {} был изменён на {}", number, newStatus);
        }

        return accountMapper.toDto(account);
    }

    private Account findAccountAndValid(String number) {
        return accountRepository.findByNumber(number)
                .orElseThrow(() -> {
                    log.error(ExceptionMessages.CHECK_ACCOUNT_BY_NUMBER_EXCEPTION + number);
                    return new IllegalArgumentException(ExceptionMessages.CHECK_ACCOUNT_BY_NUMBER_EXCEPTION + number);
                });
    }
}