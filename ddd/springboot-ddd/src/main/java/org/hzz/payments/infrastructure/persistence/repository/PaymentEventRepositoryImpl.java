package org.hzz.payments.infrastructure.persistence.repository;

import lombok.extern.slf4j.Slf4j;
import org.hzz.payments.domain.entity.PaymentEventRepository;
import org.hzz.payments.domain.event.PaymentEvent;
import org.hzz.payments.domain.vo.PaymentEventId;
import org.hzz.payments.infrastructure.persistence.mapping.PaymentEventTable;
import org.hzz.payments.infrastructure.util.json.JsonMapper;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Repository
public class PaymentEventRepositoryImpl implements PaymentEventRepository {

    private final EventStore eventStore;
    private final JsonMapper jsonMapper;
    public PaymentEventRepositoryImpl(EventStore eventStore,
                                      JsonMapper jsonMapper) {
        this.eventStore = eventStore;
        this.jsonMapper = jsonMapper;
    }
    @Override
    public CompletableFuture<PaymentEventId> store(PaymentEvent event) {
        log.info("store event: {}", event);
        String eventDataAsJson = jsonMapper.write(event);
        log.info("store event as json: {}", eventDataAsJson);
        PaymentEventTable paymentEventTable = new PaymentEventTable();
        paymentEventTable.setEventId(event.getEventId().id);
        paymentEventTable.setEventType(event.getEventType());
        paymentEventTable.setPaymentId(event.getPaymentId().id);
        paymentEventTable.setTimestamp(event.getTimestamp());
        paymentEventTable.setEventData(eventDataAsJson);
        eventStore.save(paymentEventTable);
        return CompletableFuture.completedFuture(event.getEventId());
    }
}
