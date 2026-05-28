package com.minion.contract_service.contract.service;

import com.minion.contract_service.contract.dto.ContractCreateRequest;
import com.minion.contract_service.contract.dto.ContractResponse;
import com.minion.contract_service.contract.entity.Contract;
import com.minion.contract_service.saga.ContractSaga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

	private final ContractWriter contractWriter;
	private final ContractSaga contractSaga;

	public ContractResponse create(ContractCreateRequest request) {

		// 1단계 — 재고 예약
		try {
			contractSaga.reserveInventory(request.getCarId());
		} catch (Exception e) {
			log.error("재고 예약 실패. 원인: {}", e.getMessage());
			throw new IllegalStateException("재고 예약 실패: " + e.getMessage());
		}

		// 2단계 — 계약 생성
		Contract contract;
		try {
			contract = contractWriter.create(request);
		} catch (Exception e) {
			log.error("계약 생성 실패. 원인: {}", e.getMessage());
			contractSaga.cancelInventory(request.getCarId());
			throw new IllegalStateException("계약 생성 실패: " + e.getMessage());
		}

		// 3단계 — 결제 승인
		try {
			contractSaga.approvePayment(
				contract.getId(),
				contract.getPrice(),
				contract.getPaymentMethod()
			);
			contractWriter.updateStatus(contract, Contract.ContractStatus.PAYMENT_APPROVED);
		} catch (Exception e) {
			log.error("결제 승인 실패. contractId: {}, 원인: {}", contract.getId(), e.getMessage());
			contractSaga.cancelInventory(contract.getCarId());
			contractWriter.updateStatus(contract, Contract.ContractStatus.CANCELLED);
			throw new IllegalStateException("결제 승인 실패: " + e.getMessage());
		}

		// 4단계 — 계약 확정
		try {
			contractWriter.updateStatus(contract, Contract.ContractStatus.CONFIRMED);
		} catch (Exception e) {
			log.error("계약 확정 실패. contractId: {}, 원인: {}", contract.getId(), e.getMessage());
			contractSaga.cancelPayment(contract.getId(), e.getMessage());
			contractSaga.cancelInventory(contract.getCarId());
			contractWriter.updateStatus(contract, Contract.ContractStatus.CANCELLED);
			throw new IllegalStateException("계약 확정 실패: " + e.getMessage());
		}

		return new ContractResponse(contract);
	}
}