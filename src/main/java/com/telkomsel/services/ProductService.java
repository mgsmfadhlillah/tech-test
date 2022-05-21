package com.telkomsel.services;

import com.telkomsel.entity.CnfProduct;

import java.util.Optional;

public interface ProductService {
    void save(CnfProduct category);
    void delete(CnfProduct category);
    Optional<CnfProduct> findById(Integer id);
    Iterable<CnfProduct> findAll();
}
