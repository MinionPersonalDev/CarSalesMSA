package com.minion.inventory_service.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.minion.inventory_service.inventory.entity.Inventory;

import jakarta.persistence.LockModeType;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	// 단일 인스턴스 테스트 시 DB 비관락 적용
	// 추후 K8s 환경에서 다중 인스턴스로 확장 시 Valkey 분산락 적용
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT i FROM Inventory i WHERE i.carId = :carId")
	Optional<Inventory> findByCarIdWithLock(Long carId);
}