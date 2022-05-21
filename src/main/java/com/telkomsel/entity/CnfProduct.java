package com.telkomsel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cnf_product", uniqueConstraints = { @UniqueConstraint(name = "cnf_product_uk", columnNames = {"product_name"})})
public class CnfProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false, nullable = false)
    private Integer productId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CnfCategory category;

    @Column(name = "product_name", length = 80, nullable = false)
    private String product_name;

    @Column(name = "quantity", length = 4, nullable = false)
    private Integer qty;

    @Column(name = "price", length = 12, nullable = false)
    private Integer price;
}
