package com.telkomsel.services;

import com.telkomsel.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<AppUser> findAllUsers();
    List<AppUser> findAllByRole_RoleId(Integer id);
    List<AppUser> findAllByRole_RoleName(String id);
    List<AppUser> findAllByRole_RoleNameAndEnabled(String rolename, Boolean sts);
    AppUser save(AppUser user);
    AppUser findByEmail(String id);
    Optional<AppUser> findByEmailOptional(String email);
    Optional<AppUser> findById(Integer id);
    AppUser getById(Integer id);
}