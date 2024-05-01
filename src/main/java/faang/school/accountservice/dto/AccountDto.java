package faang.school.accountservice.dto;

import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.OwnerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    @Size(min = 12, max = 20, message = "The account number length must be from 12 to 20 characters.")
    @NotBlank(message = "The account number cannot be empty")
    private String number;

    @NotNull(message = "The owner id cannot be empty")
    private Long ownerId;

    @NotNull(message = "The owner type cannot be empty")
    private OwnerType ownerType;

    @NotNull(message = "The account type cannot be empty")
    private AccountType accountType;

    @NotNull(message = "The currency cannot be empty")
    private Currency currency;

    @NotNull(message = "The account status cannot be empty")
    private AccountStatus accountStatus;
}
