package com.assignment.asm03.service;

import com.assignment.asm03.entity.Role;
import com.assignment.asm03.entity.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User findByEmailForAnnotation(String email);
    User save(User user);
    User findById(int id);
    void deleteById(int id);
    List<User> findAll();
}
