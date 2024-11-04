package faang.school.accountservice.repository;

import faang.school.accountservice.model.number.AccountUniqueNumberCounter;
import feign.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountNumbersSequenceRepository extends JpaRepository<AccountUniqueNumberCounter, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountUniqueNumberCounter a WHERE a.type = :type")
    Optional<AccountUniqueNumberCounter> findByTypeForUpdate(String type);

    @Modifying
    @Query("UPDATE AccountUniqueNumberCounter a SET a.generation_state = true WHERE a.type = :type")
    void setActiveGenerationState(String type);

    @Modifying
    @Query("UPDATE AccountUniqueNumberCounter a SET a.generation_state = false WHERE a.type = :type")
    void setNonActiveGenerationState(String type);

    @Query(nativeQuery = true, value = """
            UPDATE account_unique_number_counter
            SET counter = counter + 1
            WHERE type = :type
            RETURNING counter;
            """)
    long incrementAndGet(String type);

    @Query(nativeQuery = true, value = """
            UPDATE account_unique_number_counter
            SET counter = counter + :increment
            WHERE type = :type
            RETURNING counter;
            """)
    long upCounterAndGet(String type, long increment);
}
