package com.minion.contract_service.saga;

import com.minion.contract_service.contract.entity.PaymentMethod;
import com.minion.contract_service.feign.inventory.InventoryClient;
import com.minion.contract_service.feign.inventory.dto.InventoryReserveRequest;
import com.minion.contract_service.feign.payment.PaymentClient;
import com.minion.contract_service.feign.payment.dto.PaymentApproveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContractSaga {

	private final InventoryClient inventoryClient;
	private final PaymentClient paymentClient;

	public void reserveInventory(Long carId) {
		log.info("재고 예약 시작. carId: {}", carId);
		inventoryClient.reserve(new InventoryReserveRequest(carId, null));
		log.info("재고 예약 완료. carId: {}", carId);
	}

	public void cancelInventory(Long carId) {
		try {
			inventoryClient.cancelReservation(carId);
			log.info("재고 예약 취소 완료. carId: {}", carId);
		} catch (Exception e) {
			log.error("재고 예약 취소 실패. carId: {}, 원인: {}", carId, e.getMessage());
		}
	}

	public void approvePayment(Long contractId, Integer price, PaymentMethod paymentMethod) {
		log.info("결제 승인 시작. contractId: {}", contractId);
		paymentClient.approve(new PaymentApproveRequest(contractId, price, paymentMethod));
		log.info("결제 승인 완료. contractId: {}", contractId);
	}

	public void cancelPayment(Long contractId, String reason) {
		try {
			paymentClient.cancel(contractId, reason);
			log.info("결제 취소 완료. contractId: {}", contractId);
		} catch (Exception e) {
			log.error("결제 취소 실패. contractId: {}, 원인: {}", contractId, e.getMessage());
		}
	}
}