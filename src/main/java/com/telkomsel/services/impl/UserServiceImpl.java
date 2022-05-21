package com.telkomsel.services.impl;

import com.telkomsel.entity.AppUser;
import com.telkomsel.repo.AppUserRepo;
import com.telkomsel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AppUserRepo repo;

    public List<AppUser> findAllUsers() {
        return repo.findAll();
    }
    public List<AppUser> findAllByRole_RoleId(Integer id) { return repo.findAllByRole_RoleId(id); }
    public List<AppUser> findAllByRole_RoleName(String id) { return repo.findAllByRole_RoleName(id); }
    public AppUser save(AppUser user) { return repo.saveAndFlush(user); }
    public AppUser findByEmail(String email) { return repo.findByEmail(email); }
    public Optional<AppUser> findByEmailOptional(String email) { return repo.findByEmailOptional(email); }
    public Optional<AppUser> findById(Integer id) { return repo.findById(id); }
    public List<AppUser> findAllByRole_RoleNameAndEnabled(String rolename, Boolean sts) { return repo.findAllByRole_RoleNameAndEnabled(rolename, sts); }
    public AppUser getById(Integer id) { return repo.getById(id); }

}
