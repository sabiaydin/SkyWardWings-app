package com.example.skyWardWingss.dao.repository;

import com.example.skyWardWingss.dao.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
