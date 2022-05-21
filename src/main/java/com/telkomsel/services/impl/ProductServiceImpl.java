package com.telkomsel.services.impl;

import com.telkomsel.entity.CnfCategory;
import com.telkomsel.entity.CnfProduct;
import com.telkomsel.repo.ProductRepo;
import com.telkomsel.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired private ProductRepo repo;

    @Transactional
    public void save(CnfProduct product){
        repo.saveAndFlush(product);
    }

    @Transactional
    public void delete(CnfProduct product){
        repo.delete(product);
    }

    public Optional<CnfProduct> findById(Integer id){
        return repo.findById(id);
    }

    public Iterable<CnfProduct> findAll() { return repo.findAll(); }
}
