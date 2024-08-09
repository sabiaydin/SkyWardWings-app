package com.example.skyWardWingss.dao.repository;

import com.example.skyWardWingss.dao.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {

    Authority findByName(String role);
}
