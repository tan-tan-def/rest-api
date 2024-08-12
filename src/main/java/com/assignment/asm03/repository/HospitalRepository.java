package com.assignment.asm03.repository;

import com.assignment.asm03.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
    @Query("SELECT h FROM Hospital h ORDER BY h.sumBooking DESC")
    List<Hospital> findAllAndSort();
    @Query("SELECT h FROM Hospital h WHERE h.name LIKE %:name% OR h.address LIKE %:address%")
    List<Hospital> findByNameAndAddress(@Param("name") String name, @Param("address") String address);
}
