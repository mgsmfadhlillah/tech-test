package com.telkomsel.services.impl;

import com.telkomsel.entity.AppUser;
import com.telkomsel.repo.AppRoleRepo;
import com.telkomsel.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private AppRoleRepo appRoleRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser users = appUserRepo.findByEmailAndEnabled(email, true);
        if (users == null) {
            throw new UsernameNotFoundException("User " + email + " was not found in the database or disabled");
        }
//        else if(users.getIsVerified() == 0){
//            throw new UsernameNotFoundException("Email " + email + " need verification");
//        }
        List<String> roleNames = appRoleRepo.getRoleNames(users.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(users.getEmail(), users.getPassword(), grantList);
        return userDetails;
    }

}
