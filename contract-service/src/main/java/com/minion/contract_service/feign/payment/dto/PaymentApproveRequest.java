package com.minion.contract_service.feign.payment.dto;

import com.minion.contract_service.contract.entity.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentApproveRequest {
	private Long contractId;
	private Integer amount;
	private PaymentMethod paymentMethod;
}