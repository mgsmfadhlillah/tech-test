package com.telkomsel.services;

import com.telkomsel.entity.AppUser;
import com.telkomsel.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class ServicesUser {

    @Autowired private AppUserRepo userRepo;

    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000;

    public void increaseFailedAttempts(AppUser user){
        Integer newFailAttempts = user.getFailedAttempt() + 1;
        userRepo.updateFailedAttempts(newFailAttempts, user.getEmail());
    }
    public void resetFailedAttempts(String email) {
        userRepo.updateFailedAttempts(0, email);
    }
    public void lock(AppUser user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());

        userRepo.save(user);
    }
    public boolean unlockWhenTimeExpired(AppUser user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);

            userRepo.save(user);
            return true;
        }
        return false;
    }
}
