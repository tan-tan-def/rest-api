package com.assignment.asm03.service;

import com.assignment.asm03.entity.BasicCondition;
import com.assignment.asm03.repository.BasicConditionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class BasicConditionServiceTest {
    @Mock
    private BasicConditionRepository basicConditionRepository;

    @InjectMocks
    private BasicConditionServiceImpl basicConditionService;
    private List<BasicCondition> basicConditions;
    private BasicCondition condition3;

    @BeforeEach
    public void setUp() {
        basicConditions = new ArrayList<>();
        // Tạo 5 đối tượng BasicCondition riêng rẽ
        BasicCondition condition1 = new BasicCondition(1, "Cảm cúm", "Bệnh lý viêm đường hô hấp cấp tính", "Nghỉ ngơi, uống thuốc hạ sốt", "Nhẹ");
        BasicCondition condition2 = new BasicCondition(2, "Tiểu đường", "Bệnh rối loạn chuyển hóa đường", "Kiểm soát đường huyết", "Nặng");
        condition3 = new BasicCondition(3, "Huyết áp cao", "Bệnh lý tăng huyết áp", "Thuốc hạ huyết áp", "Vừa");
        BasicCondition condition4 = new BasicCondition(4, "Viêm phổi", "Viêm nhiễm phổi", "Kháng sinh, nghỉ ngơi", "Nặng");
        BasicCondition condition5 = new BasicCondition(5, "Gout", "Bệnh lý chuyển hóa acid uric", "Thuốc giảm đau, chế độ ăn kiêng", "Vừa");
        basicConditions.add(condition1);
        basicConditions.add(condition2);
        basicConditions.add(condition3);
        basicConditions.add(condition4);
        basicConditions.add(condition5);

    }
    @Test
    public void BasicCondition_Save_ReturnBasicCondition(){
        BasicCondition condition6 = new BasicCondition(6, "Ho", "Bệnh lý viêm đường hô hấp cấp tính", "Nghỉ ngơi, uống thuốc hạ sốt", "Nhẹ");
        when(basicConditionRepository.save(condition6)).thenReturn(condition6);
        BasicCondition saveCondition = basicConditionService.save(condition6);
        Assertions.assertThat(saveCondition.getId()).isEqualTo(condition6.getId());
    }
    @Test
    public void BasicCondition_FindAll_ReturnListBasicCondition(){
        when(basicConditionRepository.findAll()).thenReturn(basicConditions);
        List<BasicCondition> basicConditionList = basicConditionService.findAll();
        Assertions.assertThat(basicConditionList).hasSize(5);
    }
    @Test
    public void BasicCondition_FindById_ReturnBasicCondition(){
        int basicConditionId = 3;
        when(basicConditionRepository.findById(basicConditionId)).thenReturn(Optional.of(condition3));
        BasicCondition basicCondition = basicConditionService.findById(basicConditionId);
        Assertions.assertThat(basicCondition).isNotNull();
    }
}
