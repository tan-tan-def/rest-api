package com.assignment.asm03.repository;

import com.assignment.asm03.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecializationsRepository extends JpaRepository<Specialization, Integer> {
    @Query("SELECT s FROM Specialization s ORDER BY s.sumBooking DESC")
    List<Specialization> findAllAndSort();

    @Query("SELECT s FROM Specialization s WHERE s.name LIKE %?1%")
    List<Specialization> searchSpecialization(String keyword);

    @Query("SELECT s FROM Specialization s WHERE s.name LIKE %:name% OR s.fee BETWEEN :minFee AND :maxFee")
    List<Specialization> findByNameAndFeeRange(@Param("name") String name, @Param("minFee") int minFee, @Param("maxFee") int maxFee);
}
