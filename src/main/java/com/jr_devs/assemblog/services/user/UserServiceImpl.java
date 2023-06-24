package com.jr_devs.assemblog.services.user;

import com.jr_devs.assemblog.models.User;
import com.jr_devs.assemblog.repositoryes.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final JpaUserRepository userRepository;

    @Autowired
    public UserServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User user) {
        this.checkDuplicate(user);
        return this.userRepository.save(user);
    }

    private void checkDuplicate(User user) {
        this.userRepository.findByUsername(user.getUsername()).ifPresent((findUser) -> {
            throw new IllegalStateException("Username Already exist");
        });
        this.userRepository.findByEmail(user.getEmail()).ifPresent((findUser) -> {
            throw new IllegalStateException("Email Already exist");
        });
    }
}
