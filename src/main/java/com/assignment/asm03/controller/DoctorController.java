package com.assignment.asm03.controller;

import com.assignment.asm03.entity.Appointment;
import com.assignment.asm03.entity.MedicalHistory;
import com.assignment.asm03.entity.Patient;
import com.assignment.asm03.entity.User;
import com.assignment.asm03.security.CustomUserDetail;
import com.assignment.asm03.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
@Tag(name = "Doctor Controller", description = "The functionality of doctor")
public class DoctorController {

    private final EmailService emailService;
    private final UserService userService;
    private final AppointmentService appointmentService;

    //5.2.2 View the list of patient
    @GetMapping("/list-patient")
    @Operation(summary = "5.2.2 View the list of patient", description = "View the list of patient")
    public ResponseEntity<?> viewPatients(@AuthenticationPrincipal CustomUserDetail principal){
        List<Appointment> appointments = appointmentService.findByDoctor(principal.getUser().getDoctor());
        Set<Patient> patients = new HashSet<>();
        for(Appointment appointment:appointments){
            patients.add(appointment.getPatient());
        }
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    //5.2.3 List appointment of doctor
    @GetMapping("/list-appointment")
    @Operation(summary = "5.2.3 List appointment of doctor", description = "List appointment of doctor")
    public ResponseEntity<?> acceptAppointment(@AuthenticationPrincipal CustomUserDetail principal){
        List<Appointment> appointments = appointmentService.findByDoctor(principal.getUser().getDoctor());
        return new ResponseEntity<>(appointments,HttpStatus.OK);
    }
    //5.2.3 Accept the appointment of patient
    @PutMapping("/accept-appointment/{appointmentId}")
    @Operation(summary = "5.2.3 Accept the appointment of patient", description = "Accept the appointment of patient")
    public ResponseEntity<?> acceptAppointment(@PathVariable int appointmentId, @AuthenticationPrincipal CustomUserDetail principal){
        Appointment appointment = appointmentService.findById(appointmentId);
        if(appointment.getDoctor().getId()!=principal.getUser().getDoctor().getId()){
            return new ResponseEntity<>("Vui lòng nhập ID Appointment tương ứng với doctor",HttpStatus.BAD_REQUEST);
        }

        appointment.setConfirmByDoctor(true);
        appointmentService.save(appointment);
        return new ResponseEntity<>("Đã nhận lịch khám", HttpStatus.OK);
    }

    //5.2.3 Cancel the appointment of patient
    @PutMapping("/cancel-appointment/{appointmentId}")
    @Operation(summary = "5.2.3 Cancel the appointment of patient", description = "Cancel the appointment of patient")
    public ResponseEntity<?> cancelAppointment(@PathVariable int appointmentId, @RequestParam String reason, @AuthenticationPrincipal CustomUserDetail principal){
        Appointment appointment = appointmentService.findById(appointmentId);
        if(appointment.getDoctor().getId()!=principal.getUser().getDoctor().getId()){
            return new ResponseEntity<>("Vui lòng ID Appointment tương ứng với doctor",HttpStatus.BAD_REQUEST);
        }
        appointment.setConfirmByDoctor(false);
        appointment.setReason(reason);
        appointmentService.save(appointment);
        return new ResponseEntity<>("Đã hủy lịch khám", HttpStatus.OK);
    }

    //6.1 Send medical information to the patient's email.
    @PostMapping("/send-pdf")
    @Operation(summary = "6.1 Send file pdf to patient's email", description = "Send file pdf to patient's email")
    public ResponseEntity<?> sendPdf(@RequestParam String email, @RequestParam String pathFile){
        User user = userService.findByEmail(email);
        if(user==null){
            return new ResponseEntity<>("Không tìm thấy email: "+ email, HttpStatus.NOT_FOUND);
        }
        try {
            emailService.sendEmailWithAttachment(user, pathFile);
            return new ResponseEntity<>("Gửi email thành công!", HttpStatus.OK);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Gửi mail không thành công \n Vui lòng nhập đúng email và đường dẫn file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
