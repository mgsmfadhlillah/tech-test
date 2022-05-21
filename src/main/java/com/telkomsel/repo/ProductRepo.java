package com.telkomsel.repo;

import com.telkomsel.entity.CnfProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<CnfProduct, Integer> {
}
