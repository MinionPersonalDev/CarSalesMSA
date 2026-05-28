package com.minion.contract_service.contract.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minion.contract_service.contract.dto.ContractCreateRequest;
import com.minion.contract_service.contract.entity.Contract;
import com.minion.contract_service.contract.repository.ContractRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractWriter {

	private final ContractRepository contractRepository;

	@Transactional
	public Contract create(ContractCreateRequest request) {
		// 계약 생성
		Contract contract = Contract.builder()
			.carId(request.getCarId())
			.customerId(request.getCustomerId())
			.dealerId(request.getDealerId())
			.price(request.getPrice())
			.paymentMethod(request.getPaymentMethod())
			.build();

		Contract saved = contractRepository.save(contract);
		log.info("계약 생성 완료. contractId: {}", saved.getId());
		return saved;
	}

	@Transactional
	public void updateStatus(Contract contract, Contract.ContractStatus status) {
		contract.updateStatus(status);
		contractRepository.save(contract);
		log.info("계약 상태 변경 완료. contractId: {}, status: {}", contract.getId(), status);
	}
}
