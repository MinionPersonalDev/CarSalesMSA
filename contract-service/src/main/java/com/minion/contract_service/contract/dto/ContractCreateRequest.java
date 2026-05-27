package com.minion.contract_service.contract.dto;

import com.minion.contract_service.contract.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContractCreateRequest {

	@NotNull
	@Positive
	private Long carId;

	@NotNull
	@Positive
	private Long customerId;

	@NotNull
	@Positive
	private Long dealerId;

	@NotNull
	@Positive
	private Integer price;

	@NotNull
	private PaymentMethod paymentMethod;
}