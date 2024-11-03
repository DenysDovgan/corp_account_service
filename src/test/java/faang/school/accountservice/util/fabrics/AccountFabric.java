package faang.school.accountservice.util.fabrics;

import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.balance.Balance;
import faang.school.accountservice.enums.account.AccountType;
import lombok.experimental.UtilityClass;

import java.util.UUID;

import static faang.school.accountservice.enums.Currency.USD;
import static faang.school.accountservice.enums.account.AccountStatus.ACTIVE;
import static faang.school.accountservice.enums.account.AccountType.PERSONAL;

@UtilityClass
public class AccountFabric {
    private static final String ACCOUNT_NUMBER = "408124878517";

    public static Account buildAccountDefault(Long userId) {
        return Account.builder()
                .number(ACCOUNT_NUMBER)
                .userId(userId)
                .type(PERSONAL)
                .currency(USD)
                .status(ACTIVE)
                .build();
    }

    public static Account buildAccount() {
        return Account.builder()
                .build();
    }

    public static Account buildAccount(UUID id) {
        return Account.builder()
                .id(id)
                .build();
    }

    public static Account buildAccount(Balance balance) {
        return Account.builder()
                .balance(balance)
                .build();
    }
}
