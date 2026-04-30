package com.minion.payment_service.payment.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long contractId;

	@Column(nullable = false)
	private Integer amount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private PaymentMethod paymentMethod;

	@Column(length = 255)
	private String failReason;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private PaymentStatus status;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	public enum PaymentMethod {
		CASH,
		CREDIT_CARD,
		LOAN,
		LEASE,
		RENTAL
	}

	public enum PaymentStatus {
		APPROVED,
		CANCELLED
	}

	@Builder
	public Payment(Long contractId, Integer amount, PaymentMethod paymentMethod) {
		this.contractId = contractId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.status = PaymentStatus.APPROVED;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public void cancel(String failReason) {
		this.status = PaymentStatus.CANCELLED;
		this.failReason = failReason;
		this.updatedAt = LocalDateTime.now();
	}
}