package com.assignment.asm03.service;

import com.assignment.asm03.entity.Role;

import java.util.List;

public interface RoleService {
    Role save(Role roles);
    List<Role> findAll();
    Role findById(int id);

}
