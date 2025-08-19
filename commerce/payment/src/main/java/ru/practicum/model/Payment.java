package ru.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import ru.practicum.dto.payment.enums.PaymentState;

import java.util.UUID;

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
    UUID paymentId;

    @Enumerated(EnumType.STRING)
    PaymentState state;

    @NotNull
    UUID orderId;

    Double totalPayment;
    Double deliveryTotal;
    Double feeTotal;
}
