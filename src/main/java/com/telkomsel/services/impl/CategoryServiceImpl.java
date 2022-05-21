package com.telkomsel.services.impl;

import com.telkomsel.entity.CnfCategory;
import com.telkomsel.repo.CategoryRepo;
import com.telkomsel.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired private CategoryRepo repo;

    @Transactional
    public void save(CnfCategory bank){
        repo.saveAndFlush(bank);
    }

    @Transactional
    public void delete(CnfCategory bank){
        repo.delete(bank);
    }

    public Optional<CnfCategory> findById(Integer id){
        return repo.findById(id);
    }

    public Iterable<CnfCategory> findAll() { return repo.findAll(); }
}
