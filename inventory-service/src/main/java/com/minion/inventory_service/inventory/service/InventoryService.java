package com.minion.inventory_service.inventory.service;

import org.springframework.stereotype.Service;

import com.minion.inventory_service.inventory.dto.InventoryReserveRequest;
import com.minion.inventory_service.inventory.dto.InventoryResponse;
import com.minion.inventory_service.inventory.entity.Inventory;
import com.minion.inventory_service.inventory.repository.InventoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	@Transactional
	public InventoryResponse reserve(InventoryReserveRequest request) {
		Inventory inventory = inventoryRepository.findByCarIdWithLock(request.getCarId())
			.orElseThrow(() -> new IllegalArgumentException("입력된 차량 정보를 찾을 수 없습니다. carId: " + request.getCarId()));

		if (inventory.getStock() <= 0) {
			throw new IllegalStateException("재고가 없습니다. carId: " + request.getCarId());
		}

		inventory.reserve();
		return new InventoryResponse(inventory);
	}

	@Transactional
	public void cancelReservation(Long carId) {
		Inventory inventory = inventoryRepository.findByCarIdWithLock(carId)
			.orElseThrow(() -> new IllegalArgumentException("입력된 차량 정보를 찾을 수 없습니다. carId: " + carId));

		inventory.cancelReservation();
	}
}