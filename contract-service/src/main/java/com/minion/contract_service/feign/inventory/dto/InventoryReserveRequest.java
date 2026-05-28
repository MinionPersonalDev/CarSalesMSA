package com.minion.contract_service.feign.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryReserveRequest {
	private Long carId;
	private Long contractId;
}