package com.minion.contract_service.contract.service;

import com.minion.contract_service.contract.dto.ContractCreateRequest;
import com.minion.contract_service.contract.dto.ContractResponse;
import com.minion.contract_service.contract.entity.Contract;
import com.minion.contract_service.contract.repository.ContractRepository;
import com.minion.contract_service.saga.ContractSaga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractService {

	private final ContractRepository contractRepository;
	private final ContractSaga contractSaga;

	@Transactional
	public ContractResponse create(ContractCreateRequest request) {
		// 계약 생성
		Contract contract = Contract.builder()
			.carId(request.getCarId())
			.customerId(request.getCustomerId())
			.dealerId(request.getDealerId())
			.price(request.getPrice())
			.paymentMethod(request.getPaymentMethod())
			.build();

		contractRepository.save(contract);
		log.info("계약 생성 완료. contractId: {}", contract.getId());

		// Saga 실행
		contractSaga.execute(contract);

		return new ContractResponse(contract);
	}
}