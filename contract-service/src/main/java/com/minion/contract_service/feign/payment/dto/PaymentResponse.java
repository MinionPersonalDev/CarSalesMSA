package com.minion.contract_service.feign.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentResponse {
	private Long id;
	private Long contractId;
	private Integer amount;
	private String paymentMethod;
	private String status;
}