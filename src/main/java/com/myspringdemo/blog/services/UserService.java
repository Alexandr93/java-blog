package com.myspringdemo.blog.services;

import com.myspringdemo.blog.models.ERole;
import com.myspringdemo.blog.models.Role;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.pojo.UserModel;
import com.myspringdemo.blog.repo.RolesRepository;
import com.myspringdemo.blog.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Transactional
    public Set<Role> getUserRoles(List<ERole> username) {
        return username.stream()
                .map(r -> rolesRepository.findByName(r))
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<Role> getUserRolesbyId(List<Long> id) {
        return id.stream()
                .map(r -> rolesRepository.findById(r).get())
                .collect(Collectors.toSet());

    }

    @Transactional
    public UserEntity createUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }
    @Transactional
    public void updateUser(UserEntity user) {
        UserEntity userUpdate = userRepository.findByUsername(user.getUsername());
        userUpdate.setEnabled(user.isEnabled());
        userUpdate.setRoles(user.getRoles());

        userRepository.save(userUpdate);
    }

    @Transactional
    public void deleteUser(String username) {
        UserEntity user = userRepository.findByUsername(username);

        userRepository.delete(user);
    }
    @Transactional
    public Iterable<Role> getRolesList() {
        return rolesRepository.findAll();
    }

    @Transactional
    public List<UserModel> usersList() {
        Iterable<UserEntity> users = userRepository.findAll();
        List<UserModel> usersList = new ArrayList<>();
        users.forEach(user -> usersList.add(UserModel.userEntityToUserModel(user)));
        return usersList;
    }
    @Transactional
    public UserModel findByUsername(String username) {


        return UserModel.userEntityToUserModel(userRepository.findByUsername(username));
    }

}
