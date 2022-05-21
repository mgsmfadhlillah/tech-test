package com.telkomsel.services.impl;

import com.telkomsel.entity.ActPurchase;
import com.telkomsel.entity.CnfProduct;
import com.telkomsel.repo.ProductRepo;
import com.telkomsel.repo.PurchaseRepo;
import com.telkomsel.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired private PurchaseRepo purchaseRepo;
    @Autowired private ProductRepo productRepo;

    @Transactional
    public void save(ActPurchase purchase){
        purchaseRepo.saveAndFlush(purchase);
    }

    @Transactional
    public void saveWithProduct(ActPurchase purchase, CnfProduct product){
        productRepo.save(product);
        purchaseRepo.save(purchase);
        productRepo.flush();
        purchaseRepo.flush();
    }

    @Transactional
    public void delete(ActPurchase purchase){
        purchaseRepo.delete(purchase);
    }

    public Optional<ActPurchase> findById(Integer id){
        return purchaseRepo.findById(id);
    }

    public Iterable<ActPurchase> findAll() { return purchaseRepo.findAll(); }
}
