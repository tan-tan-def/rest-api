package com.assignment.asm03.service;

import com.assignment.asm03.entity.User;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user==null){
            throw new NotFoundException("Không tìm thấy người dùng với email: " +email);
        }
        return user;
    }

    @Override
    public User findByEmailForAnnotation(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(int id) {
        Optional<User> result = userRepository.findById(id);
        if(!result.isPresent()){
            throw new NotFoundException("User not found");
        }
        User user = result.get();
        return user;
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
