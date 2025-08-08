package ru.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import ru.practicum.dto.payment.enums.PaymentState;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue
    @UuidGenerator
    String paymentId;

    @Enumerated(EnumType.STRING)
    PaymentState state;

    Double totalPayment;
    Double deliveryTotal;
    Double feeTotal;
}
