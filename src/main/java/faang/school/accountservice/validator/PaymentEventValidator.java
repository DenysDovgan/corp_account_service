package faang.school.accountservice.validator;

import faang.school.accountservice.model.entity.Account;
import faang.school.accountservice.model.enums.AccountStatus;
import faang.school.accountservice.model.enums.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentEventValidator {

    public ValidationResult validateIfAccountActive(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            return ValidationResult.failure(String.format("Account with id = %d should be in ACTIVE status", account.getId()));
        }

        return ValidationResult.success();
    }

    public ValidationResult validateSufficientActualBalance(BigDecimal actualBalance, BigDecimal amount, Long accountId) {
        if (actualBalance.compareTo(amount) < 0) {
            return ValidationResult.failure(String.format("Insufficient current balance for the account with id = %d", accountId));
        }

        return ValidationResult.success();
    }

    public ValidationResult validateOperationType(OperationType operationType, OperationType expectedOperationType) {
        if (operationType != expectedOperationType) {
            return ValidationResult.failure(String.format("In authorize OperationType should be %s", expectedOperationType));
        }

        return ValidationResult.success();
    }

    public ValidationResult validateAccountUserIdAndContextTheSame(Long accountUserId, Long senderContextUserId) {
        if (!senderContextUserId.equals(accountUserId)) {
            return ValidationResult.failure(String.format("The User ID = %d from the context does not match the user ID = %d from the account",
                    senderContextUserId, accountUserId));
        }

        return ValidationResult.success();
    }
}
