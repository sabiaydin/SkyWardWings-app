package com.example.skyWardWingss.dao.repository;

import com.example.skyWardWingss.dao.entity.CardDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDetailRepository extends JpaRepository<CardDetail,Long> {
}
