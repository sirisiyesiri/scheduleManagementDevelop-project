package com.example.schedulemanagementdevelop.user.service;

import com.example.schedulemanagementdevelop.ExceptionHandler.LoginFailedException;
import com.example.schedulemanagementdevelop.ExceptionHandler.NotExistUser;
import com.example.schedulemanagementdevelop.user.dto.*;
import com.example.schedulemanagementdevelop.user.entity.User;
import com.example.schedulemanagementdevelop.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User user = new User(
                request.getUserName(),
                request.getEmail(),
                request.getPassword()
        );

        User savedUser = userRepository.save(user);

        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetOneUserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                NotExistUser::new
        );

        return new GetOneUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetAllUserResponse> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new GetAllUserResponse(
                        user.getId(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                )).toList();
    }

    @Transactional
    public ModifyUserResponse modify(Long userId, ModifyUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                NotExistUser::new
        );


        user.modifyInfo(
                request.getUserName(),
                request.getEmail()
        );

        return new ModifyUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long userId) {
        boolean existence = userRepository.existsById(userId);

        if(!existence) {
            throw new NotExistUser();
        }

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                LoginFailedException::new
        );

        if(!request.getPassword().equals(user.getPassword())) {
            throw new LoginFailedException();
        }

        return new SessionUser(
                user.getId(),
                user.getEmail()
        );
    }
}
