package faang.school.accountservice;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.exception.DataValidationException;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.service.AccountService;
import faang.school.accountservice.validator.AccountValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Spy
    private AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    @Mock
    private AccountValidator accountValidator;

    @InjectMocks
    private AccountService accountService;

    Account account;
    AccountDto accountDto;

    @BeforeEach
    void init() {
        account = Account.builder()
                .id(1L)
                .number("123456789101112")
                .ownerType(OwnerType.PROJECT)
                .ownerProjectId(1L)
                .currency(Currency.USD)
                .accountType(AccountType.INDIVIDUAL)
                .createdAt(LocalDateTime.now())
                .accountStatus(AccountStatus.OPEN)
                .version(1L)
                .build();

        accountDto = AccountDto.builder()
                .number("123456789101112")
                .ownerType(OwnerType.PROJECT)
                .ownerProjectId(1L)
                .currency(Currency.USD)
                .accountType(AccountType.INDIVIDUAL)
                .accountStatus(AccountStatus.OPEN)
                .build();
    }


    @Test
    @DisplayName("Test Account open | Wrong input data - Number of Account")
    public void testAccountOpenWrongAccountNumber() {
        String errorMessage = "The number of account is null or blank";

        doThrow(new DataValidationException(errorMessage)).when(accountValidator).validateAccountNumber(anyString());

        Exception exception = assertThrows(DataValidationException.class, () -> accountService.openAccount(accountDto));
        verifyNoMoreInteractions(accountRepository, accountValidator);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Test Account open | Wrong input data - Wrong account owner")
    public void testAccountOpenWrongAccountOwner() {
        String errorMessage = "Owner project_id and Owner user_id cannot be both null";
        doNothing().when(accountValidator).validateAccountNumber(anyString());

        doThrow(new DataValidationException(errorMessage)).when(accountValidator).validateAccountOwner(any(), any(), any());

        Exception exception = assertThrows(DataValidationException.class, () -> accountService.openAccount(accountDto));
        verifyNoMoreInteractions(accountRepository, accountValidator);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Test Account open | Successfully")
    public void testAccountOpenOk() {
        doNothing().when(accountValidator).validateAccountNumber(anyString());
        doNothing().when(accountValidator).validateAccountOwner(any(), any(), any());

        when(accountRepository.save(any())).thenReturn(account);

        AccountDto result = accountService.openAccount(accountDto);

        assertEquals("123456789101112", result.getNumber());
    }

    @Test
    @DisplayName("Test Block account | Wrong account ID")
    public void testBlockAccountWrongId() {
        String errorMessage = "Couldn't find account with ID = " + 1L;

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, ()-> accountService.blockAccount(1L));

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Test Block account | Successfully")
    public void testBlockAccountOk() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        accountService.blockAccount(1L);

        assertEquals(AccountStatus.FROZEN, account.getAccountStatus());
    }

    @Test
    @DisplayName("Test Close account | Successfully")
    public void testCloseAccountOk() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        accountService.closeAccount(1L);

        assertEquals(AccountStatus.CLOSED, account.getAccountStatus());
        assertNotNull(account.getClosedAt());
    }



}
