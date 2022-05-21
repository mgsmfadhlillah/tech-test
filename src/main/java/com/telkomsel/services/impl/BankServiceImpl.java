package com.telkomsel.services.impl;

import com.telkomsel.entity.CnfBank;
import com.telkomsel.repo.BankRepo;
import com.telkomsel.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {
    @Autowired private BankRepo repo;

    @Transactional
    public void save(CnfBank bank){
        repo.saveAndFlush(bank);
    }

    @Transactional
    public void delete(CnfBank bank){
        repo.delete(bank);
    }

    public Optional<CnfBank> findById(Integer id){
        return repo.findById(id);
    }

    public Iterable<CnfBank> findAll() { return repo.findAll(); }
}
