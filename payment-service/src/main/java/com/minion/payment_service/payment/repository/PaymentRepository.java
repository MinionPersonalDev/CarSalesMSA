package com.minion.payment_service.payment.repository;

import com.minion.payment_service.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByContractId(Long contractId);
}