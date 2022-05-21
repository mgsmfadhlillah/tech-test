package com.telkomsel.services;

import com.telkomsel.entity.CnfCategory;

import java.util.Optional;

public interface CategoryService {
    void save(CnfCategory bank);
    void delete(CnfCategory bank);
    Optional<CnfCategory> findById(Integer id);
    Iterable<CnfCategory> findAll();
}
