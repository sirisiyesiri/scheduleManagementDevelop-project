package com.example.schedulemanagementdevelop.user.service;

import com.example.schedulemanagementdevelop.ExceptionHandler.AlreadyLoggedInException;
import com.example.schedulemanagementdevelop.ExceptionHandler.AuthenticationRequiredException;
import com.example.schedulemanagementdevelop.ExceptionHandler.LoginFailedException;
import com.example.schedulemanagementdevelop.ExceptionHandler.NotExistUser;
import com.example.schedulemanagementdevelop.config.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse save(SessionUser sessionUser, CreateUserRequest request) {

        if(sessionUser != null) {   // 로그인 된 상태(=이미 회원가입된 상태)라면 회원가입 불가
            throw new AlreadyLoggedInException();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword()); // 비밀번호 암호화

        User user = new User(
                request.getUserName(),
                request.getEmail(),
                encodedPassword // 암호화된 비밀번호를 User 객체에 담기
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
    public ModifyUserResponse modify(SessionUser sessionUser, Long userId, ModifyUserRequest request) {

        if(sessionUser == null) {   // 로그인이 안 되어 있는 상태라면 해당 기능 사용불가
            throw new AuthenticationRequiredException();
        }

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
    public void delete(SessionUser sessionUser, Long userId) {

        if(sessionUser == null) {   // 로그인이 안 되어 있는 상태라면 해당 기능 사용 불가
            throw new AuthenticationRequiredException();
        }

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

        boolean isMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );
        // 입력한 비밀번호가 일치하는지 확인

        if(!isMatch) {  // 비밀번호가 일치하지 않는다면 예외 처리
            throw new LoginFailedException();
        }

        return new SessionUser(
                user.getId(),
                user.getEmail()
        );
    }
}
