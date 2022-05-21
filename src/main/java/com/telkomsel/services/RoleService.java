package com.telkomsel.services;

import com.telkomsel.entity.AppRole;

public interface RoleService {
  AppRole findByRoleName(String role);
}
