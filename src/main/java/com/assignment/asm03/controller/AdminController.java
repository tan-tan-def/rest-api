package com.assignment.asm03.controller;

import com.assignment.asm03.common.DateTimeHelper;
import com.assignment.asm03.common.Field;
import com.assignment.asm03.common.ShowError;
import com.assignment.asm03.entity.*;
import com.assignment.asm03.model.DoctorIsAddedByAdmin;
import com.assignment.asm03.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "The functionality of admin")
public class AdminController {
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;
    private final HospitalService hospitalService;
    private final SpecializationsService specializationsService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    //5.3.2 Lock patient's account
    @PutMapping("/lock-user/{id}")
    @Operation(summary = "5.3.2. Lock Patient", description = "Lock User")
    public ResponseEntity<?> lockUser(@PathVariable int id, @RequestParam String description){
        return lock(id,description,"USER", Field.ROLE_USER);
    }
    //5.3.2 Unlock patient's account
    @PutMapping("/unlock-user/{id}")
    @Operation(summary = "5.3.2 Unlock Patient", description = "Unlock User")
    public ResponseEntity<?> unlockUser(@PathVariable int id){
        return unlock(id,"USER", Field.ROLE_USER);
    }
    //5.3.5 Lock doctor's account
    @PutMapping("/lock-doctor/{id}")
    @Operation(summary = "5.3.5. Lock Doctor", description = "Lock Doctor")
    public ResponseEntity<?> lockDoctor(@PathVariable int id, @RequestParam String description){
        return lock(id, description,"DOCTOR", Field.ROLE_DOCTOR);
    }
    //5.3.5 Unlock doctor's account
    @PutMapping("/unlock-doctor/{id}")
    @Operation(summary = "5.3.5 Unlock Doctor", description = "Unlock Doctor")
    public ResponseEntity<?> unlockDoctor(@PathVariable int id){
        return unlock(id,"DOCTOR", Field.ROLE_DOCTOR);
    }
    //Lock USER or DOCTOR
    private ResponseEntity<?> lock(int id, String description, String role, int idRole){
        User user = userService.findById(id);
        if(user.getRole().getId()!=idRole){
            return new ResponseEntity<>("Vui lòng chọn tài khoản "+role, HttpStatus.BAD_REQUEST);
        }
        user.setActive(false);
        user.setDescription(description);
        user.setUpdatedAt(DateTimeHelper.getCurrentDateTime());
        userService.save(user);
        return new ResponseEntity<>("Khóa tài khoản "+ user.getName()+" thành công", HttpStatus.OK);
    }
    //Unlock USER OR DOCTOR
    private ResponseEntity<?> unlock(int id, String role, int idRole){
        User user = userService.findById(id);
        if(user.getRole().getId()!=idRole){
            return new ResponseEntity<>("Vui lòng chọn tài khoản "+ role, HttpStatus.BAD_REQUEST);
        }
        user.setActive(true);
        user.setDescription("Đã mở khóa");
        user.setUpdatedAt(DateTimeHelper.getCurrentDateTime());
        userService.save(user);
        return new ResponseEntity<>("Hủy khóa "+user.getName()+ " thành công", HttpStatus.OK);
    }
    //5.3.4 Add Doctor
    @PostMapping("/add-doctor")
    @Operation(summary = "5.3.4 Add account's doctor", description = "Add doctor")
    public ResponseEntity<?> addDoctor(@Valid @RequestBody DoctorIsAddedByAdmin doctorIsAddedByAdmin, BindingResult result){
        if(result.hasErrors()){
            String errorMessage = ShowError.showError(result);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        //Save User
        Role role = roleService.findById(Field.ROLE_DOCTOR);
        User user = new User(doctorIsAddedByAdmin.getName(), doctorIsAddedByAdmin.getGender(), doctorIsAddedByAdmin.getEmail(), doctorIsAddedByAdmin.getPhone(), doctorIsAddedByAdmin.getAddress(), encoder.encode(doctorIsAddedByAdmin.getPassword()), role,true);
        userService.save(user);

        //Save Doctor
        Hospital hospital = hospitalService.findById(doctorIsAddedByAdmin.getHospitalId());
        Specialization specialization = specializationsService.findById(doctorIsAddedByAdmin.getSpecializationId());
        Doctor doctor = new Doctor(doctorIsAddedByAdmin.getGeneralIntroduction(), doctorIsAddedByAdmin.getEducationalBackground(), doctorIsAddedByAdmin.getAchievements(),hospital,specialization,user );
        doctorService.save(doctor);

        return new ResponseEntity<>("Tạo mới bác sĩ thành công", HttpStatus.CREATED);
    }
    //6.2 View detailed appointment information for each patient.
    @GetMapping("/appointment-detail-patient/{userId}")
    @Operation(summary = "6.2 Detailed appointment information of patient", description = "Detailed appointment information of patient")
    public ResponseEntity<?> viewDetailPatient(@PathVariable int userId){
        Patient patient = patientService.findByUserId(userId);
        List<Appointment> appointments = appointmentService.findByPatient(patient);
        if(appointments.isEmpty()){
            return new ResponseEntity<>("Người dùng "+ patient.getUser().getName()+ " không có lịch khám",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments,HttpStatus.OK);
    }
    //6.3 View detailed appointment information for each doctor.
    @GetMapping("/appointment-detail-doctor/{doctorId}")
    @Operation(summary = "6.3 Detailed appointment information of doctor", description = "Detailed appointment information of doctor")
    public ResponseEntity<?> viewDetailDoctor(@PathVariable int doctorId){
        Doctor doctor = doctorService.findByUserId(doctorId);
        List<Appointment> appointments = appointmentService.findByDoctor(doctor);
        if(appointments.isEmpty()){
            return new ResponseEntity<>("Bác sĩ "+doctor.getUser().getName()+" không có lịch khám",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments,HttpStatus.OK);
    }

}
