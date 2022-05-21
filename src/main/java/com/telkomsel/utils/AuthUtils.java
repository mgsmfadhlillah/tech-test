package com.telkomsel.utils;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthUtils {

    public static String getUsername(User user) {
        StringBuilder sb = new StringBuilder();

        sb.append(user.getUsername());

        Collection<GrantedAuthority> authorities = user.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            boolean first = true;
            for (GrantedAuthority a : authorities) {
                if (first) {
                    //sb.append(a.getAuthority());
                    first = false;
                } else {
                    //sb.append(", ").append(a.getAuthority());
                }
            }
        }
        return sb.toString();
    }

    public static String getRole(User user) {
        StringBuilder sb = new StringBuilder();

        Collection<GrantedAuthority> authorities = user.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            boolean first = true;
            for (GrantedAuthority a : authorities) {
                if (first) {
                    sb.append(a.getAuthority());
                    first = false;
                } else {
                    sb.append(", ").append(a.getAuthority());
                }
            }
        }
        return sb.toString();

    }

}
