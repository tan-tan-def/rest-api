package com.assignment.asm03.service;

import com.assignment.asm03.entity.BasicCondition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BasicConditionService {
    BasicCondition save(BasicCondition basicCondition);
    List<BasicCondition> findAll();
    BasicCondition findById(int id);
}
