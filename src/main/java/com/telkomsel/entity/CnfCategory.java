package com.telkomsel.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cnf_category", uniqueConstraints = { @UniqueConstraint(name = "cnf_category_uk", columnNames = {"category_name","subcategory_name"})})
public class CnfCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", updatable = false, nullable = false)
    private Integer categoryId;

    @Column(name = "category_name", length = 80, nullable = false)
    private String category_name;

    @Column(name = "subcategory_name", length = 80, nullable = false)
    private String subcategory_name;
}
