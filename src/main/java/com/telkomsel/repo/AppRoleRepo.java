package com.telkomsel.repo;

import com.telkomsel.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRoleRepo extends JpaRepository<AppRole, Integer> {

    @Query(value = "select r.role_name from app_role r join app_user u on u.role_id = r.role_id where u.user_id = :userId", nativeQuery = true)
    List<String> getRoleNames(@Param("userId") Integer userId);
    Iterable<AppRole> findAllByRoleId(Integer id);
    AppRole findByRoleName(String role);
}
