package com.telkomsel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "app_user", uniqueConstraints = { @UniqueConstraint(name = "user_email_uk", columnNames = {"email"}), @UniqueConstraint(name = "user_phone_uk", columnNames = {"phone_number"}) })
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer userId;

    @Column(name = "fullname", length = 255, nullable = false)
    private String fullname;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "password", length = 250, nullable = false)
    private String password;

    @Column(name = "address", length = 250, nullable = false)
    private String address;

    @Column(name = "enabled", length = 1, nullable = false)
    private boolean enabled;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "failed_attempt", length = 1)
    private int failedAttempt;

    @Column(name = "lock_time")
    private Date lockTime;

    @Column(name = "phone_number", length = 16)
    private String phoneNumber;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private AppRole role;

    @Column(name = "join_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "created_by", length = 50)
    private String createdBy;
}