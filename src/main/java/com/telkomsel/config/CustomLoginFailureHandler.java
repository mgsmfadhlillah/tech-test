package com.telkomsel.config;

import com.telkomsel.entity.AppUser;
import com.telkomsel.repo.AppUserRepo;
import com.telkomsel.services.ServicesUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired private ServicesUser userService;
    @Autowired private AppUserRepo userRepo;

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("username");
        AppUser user = userRepo.findByEmail(email);
        System.out.println("email : "+email);

        if (user.isEnabled() && user.isAccountNonLocked()) {
            System.out.println(user.getFailedAttempt());
            System.out.println(ServicesUser.MAX_FAILED_ATTEMPTS);
            if (user.getFailedAttempt() < ServicesUser.MAX_FAILED_ATTEMPTS - 1) {
                userService.increaseFailedAttempts(user);
            } else {
                userService.lock(user);
                exception = new LockedException("Your account has been locked due to 3 failed attempts."
                        + " It will be unlocked after 24 hours.");
            }
        }

        System.out.println(exception.toString());

        super.setDefaultFailureUrl("/do/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
