package com.telkomsel.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@DynamicUpdate
@Table(name = "cnf_bank", uniqueConstraints = { @UniqueConstraint(name = "cnf_bank_uk", columnNames = {"bank_name"})})
public class CnfBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id", updatable = false, nullable = false)
    private Integer bankId;

    @Column(name = "bank_name", length = 50, nullable = false)
    private String bank_name;

    @Column(name = "account_number", length = 20, nullable = false)
    private String account_number;

    @Column(name = "account_name", length = 200, nullable = false)
    private String account_name;

    @Column(name = "balance", length = 15, nullable = false)
    private Integer balance;
}
