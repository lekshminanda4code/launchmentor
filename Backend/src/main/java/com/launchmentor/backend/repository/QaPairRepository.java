package com.launchmentor.backend.repository;

import com.launchmentor.backend.entity.QaPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QaPairRepository extends JpaRepository<QaPair, Long> {

    List<QaPair> findByRoleIgnoreCase(String role);

    List<QaPair> findByRoleIgnoreCaseOrRoleIgnoreCase(String role1, String role2);
}
