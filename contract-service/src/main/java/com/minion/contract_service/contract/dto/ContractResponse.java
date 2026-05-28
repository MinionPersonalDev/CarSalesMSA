package com.minion.contract_service.contract.dto;

import com.minion.contract_service.contract.entity.Contract;
import lombok.Getter;

@Getter
public class ContractResponse {

	private Long id;
	private Long carId;
	private Long customerId;
	private Long dealerId;
	private Integer price;
	private String paymentMethod;
	private String status;

	public ContractResponse(Contract contract) {
		this.id = contract.getId();
		this.carId = contract.getCarId();
		this.customerId = contract.getCustomerId();
		this.dealerId = contract.getDealerId();
		this.price = contract.getPrice();
		this.paymentMethod = contract.getPaymentMethod().name();
		this.status = contract.getStatus().name();
	}
}