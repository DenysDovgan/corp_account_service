package faang.school.accountservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(indexes = @Index(name = "idx_balance_id", columnList = "balance_id"))
public class BalanceAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", length = 20, nullable = false, unique = true)
    private String number;

    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "cur_auth_balance")
    private double curAuthBalance;

    @Column(name = "cur_fact_balance")
    private double curFactBalance;

    @Column(name = "update_operation_id")
    private Long updateOperationId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "balance_id", nullable = false)
    private Balance balance;

}
