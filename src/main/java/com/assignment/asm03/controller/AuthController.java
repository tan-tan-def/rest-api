package com.assignment.asm03.controller;

import com.assignment.asm03.common.Field;
import com.assignment.asm03.common.RandomNumber;
import com.assignment.asm03.common.ShowError;
import com.assignment.asm03.entity.*;
import com.assignment.asm03.model.UserDTO;
import com.assignment.asm03.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "The functionality of registration and login")
public class AuthController {
    private final AuthService authService;
    private final BCryptPasswordEncoder encoder;
    private final UserService userService;
    private final RoleService roleService;
    private final PatientService patientService;
    private final BasicConditionService basicConditionService;
    private final MedicalHistoryService medicalHistoryService;
    //5.1.1 Login
    @PostMapping("/auth/login")
    @Operation(summary = "5.1.1, 5.2.1, 5.3.1 Login and Generate token", description = "Generate the token after entering the correct email and password")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password ){
        User user = userService.findByEmail(email);
        Map<String,String> response = new HashMap<>();
        if(user!=null && !user.isActive()){
            response.put("Message: ","Tài khoản của bạn đã bị khóa.");
            response.put("Lí do: ", user.getDescription());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        response.put("token: ", authService.attemptLogin(email, password).getAccessToken());
        response.put("message: ", "Đăng nhập thành công");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    //5.1.2 Register a new user
    @PostMapping("/auth/sign-up")
    @Operation(summary = "5.1.2. Add user",description = "Add new user")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userDTO, BindingResult result){
//        Map<String, Object> response = new HashMap<>();

        //show error message
        if(result.hasErrors()){
            String errorMessage = ShowError.showError(result);
            return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
        }

        //save user
        User user = createUser(userDTO);
        userService.save(user);

        //save patient
//        MedicalHistory medicalHistory = medicalHistoryService.findById(RandomNumber.randomNumber(medicalHistoryService.findAll().size()));
        MedicalHistory medicalHistory = medicalHistoryService.findById(5);
        Patient patient = createPatient(user);
        patient.setMedicalHistory(medicalHistory);
        patientService.save(patient);

        //save BasicCondition
//        BasicCondition basicCondition = basicConditionService.findById(RandomNumber.randomNumber(basicConditionService.findAll().size()));
        BasicCondition basicCondition = basicConditionService.findById(5);
        basicCondition.setPatient(patient);
        basicConditionService.save(basicCondition);
//        response.put("message: ", "Đăng kí thành công");


        return new ResponseEntity<>("Đăng kí thành công" , HttpStatus.CREATED);
    }
    //Create new User
    private User createUser(UserDTO userDTO){
        Role role = roleService.findById(Field.ROLE_USER);
        User user = new User(userDTO.getName(), userDTO.getGender(), userDTO.getEmail(), userDTO.getPhone(), userDTO.getAddress(), encoder.encode(userDTO.getPassword()), role, true);
        return user;
    }
    //Create Patient
    private Patient createPatient(User user){
        Patient patient = new Patient();
        patient.setUser(user);
        return patient;
    }
}
