package com.minion.payment_service.payment.service;

import com.minion.payment_service.payment.dto.PaymentApproveRequest;
import com.minion.payment_service.payment.dto.PaymentResponse;
import com.minion.payment_service.payment.entity.Payment;
import com.minion.payment_service.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;

	@Transactional
	public PaymentResponse approve(PaymentApproveRequest request) {
		return paymentRepository.findByContractId(request.getContractId())
			.map(PaymentResponse::new)
			.orElseGet(() -> {
				Payment payment = Payment.builder()
					.contractId(request.getContractId())
					.amount(request.getAmount())
					.paymentMethod(request.getPaymentMethod())
					.build();
				paymentRepository.save(payment);
				return new PaymentResponse(payment);
			});
	}

	@Transactional
	public void cancel(Long contractId, String failReason) {
		paymentRepository.findByContractId(contractId)
				.ifPresent(payment -> payment.cancel(failReason));
	}
}