package org.hzz.payments.infrastructure.persistence.mapping;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hzz.payments.domain.vo.PaymentEventType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
public class PaymentEventTable {
    @Id
    private String eventId;
    private PaymentEventType eventType;
    private String paymentId;
    private LocalDateTime timestamp;
    @Column(length = 1024)
    private String eventData;
}
