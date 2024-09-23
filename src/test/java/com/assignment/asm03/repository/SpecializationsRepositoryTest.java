package com.assignment.asm03.repository;

import com.assignment.asm03.entity.Specialization;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SpecializationsRepositoryTest {

    @Autowired
    private SpecializationsRepository specializationsRepository;

    @BeforeEach
    public void setUp(){
        Date date = new Date();

        Specialization specialization1 = new Specialization("Pediatrician", "Providing attentive care", 6, 500000, date);
        Specialization specialization2 = new Specialization("Cardiologist", "Heart specialist", 8, 700000, date);
        Specialization specialization3 = new Specialization("Dermatologist", "Skin specialist", 5, 600000, date);
        Specialization specialization4 = new Specialization("Neurologist", "Brain and nervous system specialist", 7, 750000, date);
        Specialization specialization5 = new Specialization("Orthopedic", "Bone and joint specialist", 9, 800000, date);

        specializationsRepository.save(specialization1);
        specializationsRepository.save(specialization2);
        specializationsRepository.save(specialization3);
        specializationsRepository.save(specialization4);
        specializationsRepository.save(specialization5);
    }
    @Test
    public void SpecializationRepository_FindAllAndSort_ReturnListSpecialization(){
        List<Specialization> specializations = specializationsRepository.findAllAndSort();

        Assertions.assertThat(specializations).hasSize(5);

        Assertions.assertThat(specializations.get(0).getId()).isEqualTo(5);
        Assertions.assertThat(specializations.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(specializations.get(2).getId()).isEqualTo(4);
        Assertions.assertThat(specializations.get(3).getId()).isEqualTo(1);
        Assertions.assertThat(specializations.get(4).getId()).isEqualTo(3);
    }
    @Test
    public void SpecializationRepository_SearchSpecialization_ReturnListSpecialization(){
        List<Specialization> specializations = specializationsRepository.searchSpecialization("o");

        Assertions.assertThat(specializations.size()).isEqualTo(4);
        Assertions.assertThat(specializations.get(0).getId()).isEqualTo(2);
    }
    @Test
    public void SpecializationRepository_FindByNameAndFeeRange_ReturnListSpecialization(){
        List<Specialization> specializations = specializationsRepository.findByNameAndFeeRange("a", 790000,810000);

        Assertions.assertThat(specializations).hasSize(4);
        Assertions.assertThat(specializations.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(specializations.get(1).getId()).isEqualTo(2);
        Assertions.assertThat(specializations.get(2).getId()).isEqualTo(3);
        Assertions.assertThat(specializations.get(3).getId()).isEqualTo(5);
    }
}
