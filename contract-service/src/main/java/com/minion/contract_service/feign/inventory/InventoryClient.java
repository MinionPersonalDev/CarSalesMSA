package com.minion.contract_service.feign.inventory;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.minion.contract_service.feign.inventory.dto.InventoryReserveRequest;
import com.minion.contract_service.feign.inventory.dto.InventoryResponse;

@FeignClient(name = "inventory-service", url = "${feign.inventory.url}")
public interface InventoryClient {

	@PostMapping("/api/inventories/reserve")
	InventoryResponse reserve(@RequestBody InventoryReserveRequest request);

	@PostMapping("/api/inventories/cancel")
	void cancelReservation(@RequestParam Long carId);
}