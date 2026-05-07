package com.minion.inventory_service.inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minion.inventory_service.inventory.dto.InventoryReserveRequest;
import com.minion.inventory_service.inventory.dto.InventoryResponse;
import com.minion.inventory_service.inventory.service.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@PostMapping("/reserve")
	public ResponseEntity<InventoryResponse> reserve(@Valid @RequestBody InventoryReserveRequest request) {
		return ResponseEntity.ok(inventoryService.reserve(request));
	}

	@PostMapping("/cancel")
	public ResponseEntity<Void> cancelReservation(@RequestParam Long carId) {
		inventoryService.cancelReservation(carId);
		return ResponseEntity.ok().build();
	}
}