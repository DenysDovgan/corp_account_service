package faang.school.accountservice.entity;

import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.PaymentAccountStatus;
import faang.school.accountservice.enums.PaymentAccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "account")
public class PaymentAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", length = 20, nullable = false)
    private String number;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentAccountType accountType;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentAccountStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "version", nullable = false)
    @Version
    private Long version;
}
