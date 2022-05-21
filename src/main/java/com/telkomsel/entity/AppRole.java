package com.telkomsel.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "app_role", uniqueConstraints = { @UniqueConstraint(name = "role_uk", columnNames = "role_name") })
public class AppRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", updatable = false, nullable = false)
    private Integer roleId;

    @Column(name = "role_name", length = 50, nullable = false)
    private String roleName;

}