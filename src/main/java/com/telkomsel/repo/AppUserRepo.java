package com.telkomsel.repo;

import com.telkomsel.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Integer> {
    AppUser findByEmail(String email);
    Optional<AppUser> findByEmailOptional(String email);
    AppUser findByEmailAndEnabled(String email, Boolean sts);
    List<AppUser> findAllByRole_RoleId(Integer id);
    List<AppUser> findAllByRole_RoleName(String rolename);
    List<AppUser> findAllByRole_RoleNameAndEnabled(String rolename, Boolean sts);
    List<AppUser> findAll();

    @Query(value = "UPDATE app_user u SET u.failed_attempt = ?1 WHERE u.email = ?2", nativeQuery = true)
    void updateFailedAttempts(int failAttempts, String email);
    public AppUser findByEmailAndEnabledIsTrue(String email);

}
