package com.minion.inventory_service.inventory.entity;

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
@Table(name = "inventories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private Long carId;

	@Column(nullable = false, length = 100)
	private String carName;

	@Column(nullable = false)
	private Integer modelYear;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private Integer stock;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private InventoryStatus status;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	public enum InventoryStatus {
		AVAILABLE,
		RESERVED,
		SOLD_OUT
	}

	@Builder
	public Inventory(Long carId, String carName, Integer modelYear, Integer price, Integer stock) {
		this.carId = carId;
		this.carName = carName;
		this.modelYear = modelYear;
		this.price = price;
		this.stock = stock;
		this.status = InventoryStatus.AVAILABLE;
		this.updatedAt = LocalDateTime.now();
	}

	public void reserve() {
		this.stock -= 1;
		this.status = this.stock == 0 ? InventoryStatus.SOLD_OUT : InventoryStatus.RESERVED;
		this.updatedAt = LocalDateTime.now();
	}

	public void cancelReservation() {
		this.stock += 1;
		this.status = InventoryStatus.AVAILABLE;
		this.updatedAt = LocalDateTime.now();
	}
}