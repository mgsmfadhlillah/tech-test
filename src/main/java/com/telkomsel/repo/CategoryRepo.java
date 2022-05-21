package com.telkomsel.repo;

import com.telkomsel.entity.CnfCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<CnfCategory, Integer> {
}
