package com.telkomsel.config;

import com.telkomsel.entity.AppUser;
import com.telkomsel.repo.AppUserRepo;
import com.telkomsel.services.ServicesUser;
import com.telkomsel.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired private ServicesUser userService;
    @Autowired private AppUserRepo appUserRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User auth = (User) authentication.getPrincipal();
        String email = AuthUtils.getUsername(auth);
        AppUser user = appUserRepo.findByEmail(email);

        if(user.isAccountNonLocked()){
            if (user.getFailedAttempt() > 0) {
                userService.resetFailedAttempts(user.getEmail());
            }
        }

        super.setDefaultTargetUrl("/faktur");
        super.onAuthenticationSuccess(request, response, authentication);
    }

}