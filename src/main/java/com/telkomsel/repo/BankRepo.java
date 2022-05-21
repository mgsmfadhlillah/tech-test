package com.telkomsel.repo;

import com.telkomsel.entity.CnfBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepo extends JpaRepository<CnfBank, Integer> {
}
