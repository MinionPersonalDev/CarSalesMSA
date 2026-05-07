package com.minion.inventory_service.inventory.dto;

import com.minion.inventory_service.inventory.entity.Inventory;

import lombok.Getter;

@Getter
public class InventoryResponse {

	private Long id;
	private Long carId;
	private String carName;
	private Integer stock;
	private String status;

	public InventoryResponse(Inventory inventory) {
		this.id = inventory.getId();
		this.carId = inventory.getCarId();
		this.carName = inventory.getCarName();
		this.stock = inventory.getStock();
		this.status = inventory.getStatus().name();
	}
}