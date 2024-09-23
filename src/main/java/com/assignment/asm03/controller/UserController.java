package com.assignment.asm03.controller;

import com.assignment.asm03.common.CurrencyFormatter;
import com.assignment.asm03.entity.*;
import com.assignment.asm03.security.CustomUserDetail;
import com.assignment.asm03.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Controller", description = "The functionality of user")
public class UserController {

    private final HospitalService hospitalService;
    private final SpecializationsService specializationsService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final int rangeFee=100000;

    //5.1.4. Show the list of outstanding specialization
    @GetMapping("/list-specialization")
    @Operation(summary = "5.1.4 List of well-known specialization", description = "The specialization with the highest booking")
    public ResponseEntity<?> listOfSpecialization(){
        List<Specialization> specializations = specializationsService.findAllAndSort();
        if(specializations.isEmpty()){
            return new ResponseEntity<>("Không có chuyên khoa nào được tìm thấy", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(specializations, HttpStatus.OK);
    }
    //5.1.5. Show the list of outstanding hospital
    @GetMapping("/list-hospital")
    @Operation(summary = "5.1.5 List of well-known hospitals", description = "The hospital with the highest booking")
    public ResponseEntity<?> listOfHospital(){
        List<Hospital> hospitals = hospitalService.findAllAndSort();
        if(hospitals.isEmpty()){
            return new ResponseEntity<>("Không có bệnh viện nào được tìm thấy", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }
    //5.1.6 Show personal information
    @GetMapping("/show-user")
    @Operation(summary = "5.1.6 Show user", description = "Change user's information")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal CustomUserDetail principal){
        Patient patient = patientService.findByPatientWithAppointments(principal.getUser().getPatient().getId());
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }
    //5.1.7 General Search Hospital
    @GetMapping("/search-general-hospital")
    @Operation(summary = "5.1.7 General search hospital", description = "Search the hospital")
    public ResponseEntity<?> generalSearchHospital(@RequestParam String location, @RequestParam String hospital){
        Map<String, Object> response = new HashMap<>();
        List<Hospital> hospitals = hospitalService.findByNameAndAddress(hospital,location);
        if(hospitals.isEmpty()){
            return new ResponseEntity<>("Không tìm thấy bệnh viện "+hospital+" với vị trí "+location, HttpStatus.NO_CONTENT);
        }
        response.put("message ", "Tìm kiếm thành công với vị trí "+location+", bệnh viện "+hospital);
        response.put("data ", hospitals);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //5.1.7 General Search Specialization
    @GetMapping("/search-general-specialization")
    @Operation(summary = "5.1.7 General search specialization", description = "Search the specialization")
    public ResponseEntity<?> generalSearchSpecialization(@RequestParam String specialization,
                                                         @RequestParam int fee){
        if(fee<=10000){
            return new ResponseEntity<>("Vui lòng nhập mệnh giá trên 10000", HttpStatus.BAD_REQUEST);
        }
        List<Specialization> specializations = specializationsService.findByNameAndFeeRange(specialization,fee-rangeFee,fee+rangeFee);
        if(specializations.isEmpty()){
            return new ResponseEntity<>("Không tìm thấy chuyên khoa "+specialization+" với mức phí "+fee, HttpStatus.NO_CONTENT);
        }
        Map<String,Object> response = new HashMap<>();
        response.put("message", "Tìm kiếm thành công với chuyên khoa "+specialization+" và phí "+fee);
        response.put("data",specializations);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //5.1.8 Search list of specializations
    @PostMapping("/search-specialization")
    @Operation(summary = "5.1.8 List of specialization which you search", description = "The specialization you search")
    public ResponseEntity<?> listOfSpecializationSearch(@RequestParam String valueToSearch){
        List<Specialization> specializations = specializationsService.searchSpecialization(valueToSearch);
        Map<String, Object> response = new HashMap<>();
        if(specializations.isEmpty()){
            response.put("message","Không có tìm kiếm phù hợp với từ khóa: "+ valueToSearch);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }else {
            response.put("message", "Danh sách các chuyên ngành cho từ khóa: " + valueToSearch);
            response.put("data", specializations);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
    //5.1.9 Booking an appointment
    @PostMapping("/booking-appointment")
    @Operation(summary = "5.1.9 Booking an Appointment", description = "Booking a new Appointment")
    public ResponseEntity<?> bookingAppointment(@RequestParam String time, @RequestParam String reason, @RequestParam int doctor_id, @AuthenticationPrincipal CustomUserDetail principal){
        Doctor doctor = doctorService.findByUserId(doctor_id);

        //Booking an appointment
        String fee = CurrencyFormatter.formatCurrency(doctor.getSpecialization().getFee(), "VND");
        Appointment appointment = new Appointment(time, fee, reason, doctor, principal.getUser().getPatient());
        appointment.setNameDoctor(doctor.getUser().getName());
        appointment.setNamePatient(principal.getUser().getName());
        appointmentService.save(appointment);

        //increase sum booking
        increaseSumBookingOfHospitalAndSpecialization(doctor);

        Map<String, Object> response = showInformationAfterBooking(appointment);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Map<String, Object> showInformationAfterBooking(Appointment appointment){
        Map<String, Object> map = new HashMap<>();
        map.put("Tên bệnh nhân: ", appointment.getPatient().getUser().getName());
        map.put("Giới tính bệnh nhân: ", appointment.getPatient().getUser().getGender());
        map.put("Số điện thoại bệnh nhân: ", appointment.getPatient().getUser().getPhone());
        map.put("Đia chỉ bệnh nhân: ", appointment.getPatient().getUser().getAddress());
        map.put("Lí do thăm khám: ", appointment.getReason());
        map.put("Tên bác sĩ: ", appointment.getDoctor().getUser().getName());
        map.put("Khung giờ khám: ", appointment.getAppointmentTime());
        map.put("Giá khám: ", appointment.getMedicalFee());
        return map;
    }
    private void increaseSumBookingOfHospitalAndSpecialization(Doctor doctor){
        doctor.getHospital().setSumBooking(doctor.getHospital().getSumBooking()+1);
        doctor.getSpecialization().setSumBooking(doctor.getSpecialization().getSumBooking()+1);
        hospitalService.save(doctor.getHospital());
        specializationsService.save(doctor.getSpecialization());
    }

}
