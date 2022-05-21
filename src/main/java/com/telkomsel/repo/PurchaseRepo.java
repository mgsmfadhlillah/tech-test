package com.telkomsel.repo;

import com.telkomsel.entity.ActPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepo extends JpaRepository<ActPurchase, Integer> {
}
