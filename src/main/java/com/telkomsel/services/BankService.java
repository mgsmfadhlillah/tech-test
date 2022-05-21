package com.telkomsel.services;

import com.telkomsel.entity.CnfBank;

import java.util.Optional;

public interface BankService {
    void save(CnfBank bank);
    void delete(CnfBank bank);
    Optional<CnfBank> findById(Integer id);
    Iterable<CnfBank> findAll();
}
