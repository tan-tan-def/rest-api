package com.assignment.asm03.service;

import com.assignment.asm03.entity.BasicCondition;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.BasicConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasicConditionServiceImpl implements BasicConditionService{
    private final BasicConditionRepository basicConditionRepository;

    @Override
    public BasicCondition save(BasicCondition basicCondition) {
        return basicConditionRepository.save(basicCondition);
    }

    @Override
    public List<BasicCondition> findAll() {
        return basicConditionRepository.findAll();
    }

    @Override
    public BasicCondition findById(int id) {
        Optional<BasicCondition> result = basicConditionRepository.findById(id);
        BasicCondition basicCondition;
        if(!result.isPresent()){
            throw new NotFoundException("Không tìm thấy bệnh lí cơ bản");
        }
        basicCondition = result.get();
        return basicCondition;
    }
}
