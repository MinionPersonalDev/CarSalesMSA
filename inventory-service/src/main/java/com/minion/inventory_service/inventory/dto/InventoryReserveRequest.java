package com.minion.inventory_service.inventory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InventoryReserveRequest {

	@NotNull
	private Long carId;

	@NotNull
	private Long contractId;
}