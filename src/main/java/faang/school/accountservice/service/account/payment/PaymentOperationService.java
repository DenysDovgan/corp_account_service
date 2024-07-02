package faang.school.accountservice.service.account.payment;


import faang.school.accountservice.entity.PaymentOperation;
import faang.school.accountservice.repository.PaymentOperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentOperationService {
    private final PaymentOperationRepository repository;


    @Transactional(readOnly = true)
    public boolean existsById(Long paymentNumber) {
        return repository.existsById(paymentNumber);
    }


    public void saveOperation(PaymentOperation pendingOperation) {
        repository.save(pendingOperation);
    }

    @Transactional(readOnly = true)
    public PaymentOperation findById(Long paymentNumber) {
        return repository.findById(paymentNumber);
    }
}
