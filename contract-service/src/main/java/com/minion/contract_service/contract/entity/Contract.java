package com.minion.contract_service.contract.entity;

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
@Table(name = "contracts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long carId;

	@Column(nullable = false)
	private Long customerId;

	@Column(nullable = false)
	private Long dealerId;

	@Column(nullable = false)
	private Integer price;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ContractStatus status;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	public enum ContractStatus {
		CREATED,
		INVENTORY_RESERVED,
		PAYMENT_APPROVED,
		CONFIRMED,
		CANCELLED
	}

	@Builder
	public Contract(Long carId, Long customerId, Long dealerId, Integer price) {
		this.carId = carId;
		this.customerId = customerId;
		this.dealerId = dealerId;
		this.price = price;
		this.status = ContractStatus.CREATED;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public void updateStatus(ContractStatus status) {
		this.status = status;
		this.updatedAt = LocalDateTime.now();
	}
}
