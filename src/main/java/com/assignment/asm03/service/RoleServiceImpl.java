package com.assignment.asm03.service;

import com.assignment.asm03.entity.Role;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    @Override
    public Role save(Role roles) {
        return roleRepository.save(roles);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(int id) {
        Optional<Role> result = roleRepository.findById(id);
        Role roles = null;
        if(!result.isPresent()){
            throw new NotFoundException("Can't find this Role");
        }
        roles = result.get();
        return roles;
    }
}
