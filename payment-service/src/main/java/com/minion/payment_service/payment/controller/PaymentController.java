package com.minion.payment_service.payment.controller;

import com.minion.payment_service.payment.dto.PaymentApproveRequest;
import com.minion.payment_service.payment.dto.PaymentResponse;
import com.minion.payment_service.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/approve")
	public ResponseEntity<PaymentResponse> approve(@Valid @RequestBody PaymentApproveRequest request) {
		return ResponseEntity.ok(paymentService.approve(request));
	}

	@PostMapping("/cancel")
	public ResponseEntity<Void> cancel(
		@RequestParam Long contractId,
		@RequestParam(required = false) String failReason) {
		paymentService.cancel(contractId, failReason);
		return ResponseEntity.noContent().build();
	}
}