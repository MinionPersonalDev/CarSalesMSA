package com.minion.payment_service.payment.dto;

import com.minion.payment_service.payment.entity.Payment;
import lombok.Getter;

@Getter
public class PaymentResponse {

	private Long id;
	private Long contractId;
	private Integer amount;
	private String paymentMethod;
	private String status;

	public PaymentResponse(Payment payment) {
		this.id = payment.getId();
		this.contractId = payment.getContractId();
		this.amount = payment.getAmount();
		this.paymentMethod = payment.getPaymentMethod().name();
		this.status = payment.getStatus().name();
	}
}