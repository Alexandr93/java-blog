package com.myspringdemo.blog.services;

import com.myspringdemo.blog.models.ERole;
import com.myspringdemo.blog.models.Role;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.repo.RolesRepository;
import com.myspringdemo.blog.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    RolesRepository rolesRepository;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Autowired
    public UserService(RolesRepository rolesRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public Set<Role> getUserRoles(List<ERole> username){
        return username.stream()
                .map(r->rolesRepository.findByName(r))
                .collect(Collectors.toSet());
    }
    @Transactional
    public Set<Role> getUserRolesbyId(List<Integer> id){
        return id.stream()
                .map(r->rolesRepository.findById(r).get())
                .collect(Collectors.toSet());

    }


    public UserEntity createUser(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }
    //обновляем только поля которіе меняются в модели
    //возможно нужно сделать проверку по каждому полю чтобы было более универсально
    public void updateUser(UserEntity user){
        UserEntity userUpdate = userRepository.findByUsername(user.getUsername());
        userUpdate.setEnabled(user.isEnabled());
        userUpdate.setRoles(user.getRoles());

        userRepository.save(userUpdate);
    }
}
