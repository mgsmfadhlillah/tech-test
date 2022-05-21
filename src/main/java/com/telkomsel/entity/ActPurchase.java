package com.telkomsel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "act_purchase")
public class ActPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id", updatable = false, nullable = false)
    private Integer purchaseId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private CnfProduct product;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private CnfBank bank;

    @Column(name = "quantity", length = 4, nullable = false)
    private Integer qty;

    @Column(name = "price", length = 12, nullable = false)
    private Integer price;

    @Column(name = "payment_code", length = 3, nullable = false)
    private Integer payment_code;

    @Column(name = "status", length = 1, nullable = false)
    private String status;

    @Column(name = "purchase_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchase_date;

    @Column(name = "transfer_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transfer_date;

    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expired_date;


}
