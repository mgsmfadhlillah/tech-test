package com.telkomsel.services;

import com.telkomsel.entity.ActPurchase;
import com.telkomsel.entity.CnfProduct;

import java.util.Optional;

public interface PurchaseService {
    void save(ActPurchase purchase);
    void saveWithProduct(ActPurchase purchase, CnfProduct product);
    void delete(ActPurchase purchase);
    Optional<ActPurchase> findById(Integer id);
    Iterable<ActPurchase> findAll();
}
