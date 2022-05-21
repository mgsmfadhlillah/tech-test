package com.telkomsel.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse {
  private HttpStatus status_code;
  private String status_desc;
  private String content;
}