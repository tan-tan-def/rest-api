package com.assignment.asm03.repository;

import com.assignment.asm03.entity.BasicCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicConditionRepository extends JpaRepository<BasicCondition, Integer> {
}
