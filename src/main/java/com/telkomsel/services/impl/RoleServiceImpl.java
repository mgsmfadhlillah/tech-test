package com.telkomsel.services.impl;

import com.telkomsel.entity.AppRole;
import com.telkomsel.repo.AppRoleRepo;
import com.telkomsel.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired
  private AppRoleRepo repo;

  public AppRole findByRoleName(String role) { return repo.findByRoleName(role); }
}
