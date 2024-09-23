package com.assignment.asm03.repository;

import com.assignment.asm03.entity.Hospital;
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
public class HospitalRepositoryTest {
    @Autowired
    private HospitalRepository hospitalRepository;

    @BeforeEach
    public void setUp(){
        Date date = new Date();
        Hospital hospital1 = new Hospital("Cho Ray", "Sai Gon", "0693125741", 15, "good", date);
        Hospital hospital2 = new Hospital("Bệnh viện Chợ Rẫy", "Hà Nội", "0241234567", 20, "excellent", date);
        Hospital hospital3 = new Hospital("Bệnh viện Đa Khoa", "Đà Nẵng", "0236123456", 30, "very good", date);
        Hospital hospital4 = new Hospital("Bệnh viện Nhi", "Hải Phòng", "0225123456", 10, "good", date);
        Hospital hospital5 = new Hospital("Bệnh viện Quân Y", "Cần Thơ", "0297123456", 25, "excellent", date);

        // Lưu các bệnh viện vào cơ sở dữ liệu
        hospitalRepository.save(hospital1);
        hospitalRepository.save(hospital2);
        hospitalRepository.save(hospital3);
        hospitalRepository.save(hospital4);
        hospitalRepository.save(hospital5);
    }

    @Test
    public void HospitalRepository_FindAllAndSort_ReturnListHospital(){
        List<Hospital> hospitals = hospitalRepository.findAllAndSort();

        Assertions.assertThat(hospitals.size()).isEqualTo(5);
        Assertions.assertThat(hospitals.get(0).getId()).isEqualTo(3);
        Assertions.assertThat(hospitals.get(1).getId()).isEqualTo(5);
        Assertions.assertThat(hospitals.get(2).getId()).isEqualTo(2);
        Assertions.assertThat(hospitals.get(3).getId()).isEqualTo(1);
        Assertions.assertThat(hospitals.get(4).getId()).isEqualTo(4);
    }
    @Test
    public void HospitalRepository_FindByNameAndAddress_ReturnListHospital(){
        List<Hospital> hospitals = hospitalRepository.findByNameAndAddress("Nhi","Hà Nội");

        Assertions.assertThat(hospitals.size()).isEqualTo(2);
        Assertions.assertThat(hospitals.get(0).getId()).isEqualTo(2);
        Assertions.assertThat(hospitals.get(1).getId()).isEqualTo(4);
    }
}
