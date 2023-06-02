package org.hzz.payments.infrastructure.persistence.repository;

import org.hzz.payments.infrastructure.persistence.mapping.PaymentEventTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventStore extends CrudRepository<PaymentEventTable, String> {
}
