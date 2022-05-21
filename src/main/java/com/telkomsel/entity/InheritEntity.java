package com.telkomsel.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class InheritEntity {

//  @Column(name = "enabled")
//  private boolean enabled;
//  @Column(name = "order", length = 2)
//  private int order;

  @Column(name = "created_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private Date createdDate;
  @Column(name = "created_by", length = 50)
  private String createdBy;

  @Column(name = "modified_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private Date modifiedDate;
  @Column(name = "modified_by", length = 50)
  private String modifiedBy;
}
