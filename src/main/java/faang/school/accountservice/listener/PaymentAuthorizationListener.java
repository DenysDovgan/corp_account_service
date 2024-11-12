package faang.school.accountservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.accountservice.dto.PendingDto;
import faang.school.accountservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentAuthorizationListener implements KafkaEventListener<String> {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "${spring.kafka.topics.payment-authorization.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void onEvent(String jsonEvent, Acknowledgment acknowledgment) {
        try {
            log.info("Received event {}", jsonEvent);
            PendingDto event = objectMapper.readValue(jsonEvent, PendingDto.class);
            paymentService.paymentAuthorization(event);
            acknowledgment.acknowledge();
        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage(), exception);
        }
    }
}