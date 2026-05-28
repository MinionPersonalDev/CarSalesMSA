package com.minion.contract_service.contract.controller;

import com.minion.contract_service.contract.dto.ContractCreateRequest;
import com.minion.contract_service.contract.dto.ContractResponse;
import com.minion.contract_service.contract.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

	private final ContractService contractService;

	@PostMapping
	public ResponseEntity<ContractResponse> create(@Valid @RequestBody ContractCreateRequest request) {
		return ResponseEntity.ok(contractService.create(request));
	}
}