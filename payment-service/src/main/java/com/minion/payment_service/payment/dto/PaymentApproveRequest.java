package com.minion.payment_service.payment.dto;

import com.minion.payment_service.payment.entity.Payment.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentApproveRequest {

	@NotNull
	private Long contractId;

	@NotNull
	private Integer amount;

	@NotNull
	private PaymentMethod paymentMethod;
}