package com.assignment.asm03.service;

import com.assignment.asm03.entity.Role;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {
    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("USER");
    }

    @Test
    public void RoleService_Save_ReturnSavedRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role savedRole = roleService.save(role);

        assertNotNull(savedRole);
        assertEquals(role.getId(), savedRole.getId());
        assertEquals(role.getName(), savedRole.getName());
    }

    @Test
    public void RoleService_FindAll_ReturnListOfRoles() {
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role));

        List<Role> roles = roleService.findAll();

        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals(role.getId(), roles.get(0).getId());
    }

    @Test
    public void FindById_ExistingId_ReturnRole() {
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        Role foundRole = roleService.findById(1);

        assertNotNull(foundRole);
        assertEquals(role.getId(), foundRole.getId());
    }

    @Test
    public void FindById_NonExistingId_ThrowNotFoundException() {
        when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            roleService.findById(99);
        });

        assertEquals("Can't find this Role", exception.getMessage());
    }
}
