package com.minion.contract_service.saga;

import com.minion.contract_service.contract.entity.Contract;
import com.minion.contract_service.contract.repository.ContractRepository;
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

	private final ContractRepository contractRepository;
	private final InventoryClient inventoryClient;
	private final PaymentClient paymentClient;

	public void execute(Contract contract) {
		try {
			// 1단계 — 재고 예약
			reserveInventory(contract);

			// 2단계 — 결제 승인
			approvePayment(contract);

			// 3단계 — 계약 확정
			contract.updateStatus(Contract.ContractStatus.CONFIRMED);
			contractRepository.save(contract);
			log.info("계약 확정 완료. contractId: {}", contract.getId());

		} catch (Exception e) {
			log.error("Saga 실패. contractId: {}, 원인: {}", contract.getId(), e.getMessage());
			compensate(contract, e.getMessage());
		}
	}

	private void reserveInventory(Contract contract) {
		log.info("재고 예약 시작. contractId: {}, carId: {}", contract.getId(), contract.getCarId());

		inventoryClient.reserve(new InventoryReserveRequest(
			contract.getCarId(),
			contract.getId()
		));

		contract.updateStatus(Contract.ContractStatus.INVENTORY_RESERVED);
		contractRepository.save(contract);
		log.info("재고 예약 완료. contractId: {}", contract.getId());
	}

	private void approvePayment(Contract contract) {
		log.info("결제 승인 시작. contractId: {}", contract.getId());

		paymentClient.approve(new PaymentApproveRequest(
			contract.getId(),
			contract.getPrice(),
			contract.getPaymentMethod()
		));

		contract.updateStatus(Contract.ContractStatus.PAYMENT_APPROVED);
		contractRepository.save(contract);
		log.info("결제 승인 완료. contractId: {}", contract.getId());
	}

	private void compensate(Contract contract, String reason) {
		log.info("보상 트랜잭션 시작. contractId: {}", contract.getId());

		// 재고 예약이 완료된 경우에만 재고 보상
		if (contract.getStatus() == Contract.ContractStatus.INVENTORY_RESERVED
			|| contract.getStatus() == Contract.ContractStatus.PAYMENT_APPROVED) {
			try {
				inventoryClient.cancelReservation(contract.getCarId());
				log.info("재고 예약 취소 완료. contractId: {}", contract.getId());
			} catch (Exception e) {
				log.error("재고 예약 취소 실패. contractId: {}, 원인: {}", contract.getId(), e.getMessage());
			}
		}

		// 결제 승인이 완료된 경우에만 결제 보상
		if (contract.getStatus() == Contract.ContractStatus.PAYMENT_APPROVED) {
			try {
				paymentClient.cancel(contract.getId(), reason);
				log.info("결제 취소 완료. contractId: {}", contract.getId());
			} catch (Exception e) {
				log.error("결제 취소 실패. contractId: {}, 원인: {}", contract.getId(), e.getMessage());
			}
		}

		contract.updateStatus(Contract.ContractStatus.CANCELLED);
		contractRepository.save(contract);
		log.info("계약 취소 완료. contractId: {}", contract.getId());
	}
}