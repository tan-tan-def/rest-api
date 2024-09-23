package com.assignment.asm03.repository;

import com.assignment.asm03.entity.Role;
import com.assignment.asm03.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @BeforeEach
    public void setUp(){
        Role roleDoctor = new Role("DOCTOR");
        Role roleUser = new Role("USER");
        Role roleAdmin = new Role("ADMIN");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleDoctor);
        roleRepository.save(roleUser);

        User user1 = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", roleDoctor, true);
        User user2 = new User("Tran Thi Mai", "Female", "tranmait@gmail.com", "0912345678", "Hà Nội", "password456", roleUser, true);
        User user3 = new User("Le Van An", "Male", "levanan@example.com", "0976543210", "Đà Nẵng", "mypassword789", roleAdmin, true);
        User user4 = new User("Pham Minh Tu", "Female", "phaminhtu@example.com", "0901234567", "Nha Trang", "abc123", roleUser, false);
        User user5 = new User("Hoang Van Khoa", "Male", "hoangkhoa@example.com", "0922334455", "Cần Thơ", "securePass2023", roleDoctor, true);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
    }
    @Test
    public void UserRepository_FindByEmail_ReturnUser(){
        User user = userRepository.findByEmail("levanan@example.com");

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isEqualTo(3);
    }
    @Test
    public void UserRepository_FindByEmail_ReturnNullUser(){
        User user = userRepository.findByEmail("levanam@example.com");

        Assertions.assertThat(user).isNull();
    }
}
