package com.minion.contract_service.feign.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.minion.contract_service.feign.payment.dto.PaymentApproveRequest;
import com.minion.contract_service.feign.payment.dto.PaymentResponse;

@FeignClient(name = "payment-service", url = "${feign.payment.url}")
public interface PaymentClient {

	@PostMapping("/api/payments/approve")
	PaymentResponse approve(@RequestBody PaymentApproveRequest request);

	@PostMapping("/api/payments/cancel")
	void cancel(@RequestParam Long contractId,
		@RequestParam(required = false) String failReason);
}