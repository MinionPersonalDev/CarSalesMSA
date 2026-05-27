package com.minion.contract_service.feign.inventory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InventoryResponse {
	private Long id;
	private Long carId;
	private String carName;
	private Integer stock;
	private String status;
}