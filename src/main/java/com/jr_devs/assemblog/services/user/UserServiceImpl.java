package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.User;
import com.jr_devs.assemblog.models.UserDto;
import com.jr_devs.assemblog.repositoryes.JpaUserRepository;
import com.jr_devs.assemblog.token.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public String login(UserDto UserForm) {
        User user = userRepository.findByEmail(UserForm.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Not found Email"));

        if(!passwordEncoder.matches(UserForm.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Not Match Password");
        }

        return jwtProvider.createToken(user.getEmail());
    }

    public User join(UserDto userDto) {
        this.checkDuplicate(userDto);

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();

        return this.userRepository.save(user);
    }

    private void checkDuplicate(UserDto userDto) {
        this.userRepository.findByUsername(userDto.getUsername()).ifPresent((findUser) -> {
            throw new IllegalStateException("Username Already exist");
        });
        this.userRepository.findByEmail(userDto.getEmail()).ifPresent((findUser) -> {
            throw new IllegalStateException("Email Already exist");
        });
    }
}
