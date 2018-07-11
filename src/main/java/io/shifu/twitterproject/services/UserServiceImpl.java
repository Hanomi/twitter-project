package io.shifu.twitterproject.services;

// implementation of userService for User

import io.shifu.twitterproject.model.Role;
import io.shifu.twitterproject.model.User;
import io.shifu.twitterproject.repository.RoleRepository;
import io.shifu.twitterproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(User user) {
        // не вк юзер
        user.setVkId(0);
        // кодируем пароль
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // присваеваем роль по умолчанию
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(1L));
        user.setRoles(roles);
        // сохраняем
        userRepository.save(user);
    }

    @Override
    public void changePassword(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmationToken(null);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void activationUser(User user) {
        // активируем пользователя
        user.setConfirmationToken(null);
        user.setResetToken(null);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void saveVk(User user) {
        // присваеваем роль по умолчанию
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(1L));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void saveChange(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByNick(String nick) {
        return userRepository.findByNick(nick);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }

    @Override
    public User findByVkId(Long id) {
        return userRepository.findByVkId(id);
    }

    @Override
    public User findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }
}
