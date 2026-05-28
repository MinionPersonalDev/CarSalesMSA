package com.minion.contract_service.contract.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minion.contract_service.contract.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
