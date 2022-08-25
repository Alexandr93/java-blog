package com.myspringdemo.blog.services;

import com.myspringdemo.blog.dto.user.*;
import com.myspringdemo.blog.models.Role;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.repo.RolesRepository;
import com.myspringdemo.blog.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final RoleNameToRoleConverter roleNameToRoleConverter;




    @Transactional
    public List<RolesNamesDto> getUserRoles() {
        List<RolesNamesDto> rolesDtoList = roleNameToRoleConverter.
                getRolesMap().values().stream().map(role -> modelMapper.map(role, RolesNamesDto.class)).collect(Collectors.toList());


        return rolesDtoList;

      /*  return username.stream()
                .map(r -> rolesRepository.findByName(r))
                .collect(Collectors.toSet());*/
    }

    @Transactional
    public Set<Role> getUserRolesbyId(List<Long> id) {
        return id.stream()
                .map(r -> rolesRepository.findById(r).get())
                .collect(Collectors.toSet());

    }

    @Transactional
    public UserCreateDto createUser(UserCreateDto userCreateDto) {

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        UserEntity user = convertUserCreateDtoToUserEntity(userCreateDto);

        return convertToUserCreateDto(userRepository.save(user));

    }

    @Transactional
    public void updateUser(UserModel userModel) {


        UserEntity userUpdate = userRepository.findByUsername(userModel.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userUpdate.setEnabled(userModel.isEnabled());
        userUpdate.setRoles(userModel.getRoles().stream().map(roleNameToRoleConverter::convert).map(rolesDto -> modelMapper.map(rolesDto, Role.class)).collect(Collectors.toSet()));

        userRepository.save(userUpdate);


    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userRepository.delete(user);
    }


    @Transactional
    public List<UserListDto> usersList() {
        Iterable<UserEntity> users = userRepository.findAll();
        List<UserListDto> usersList = new ArrayList<>();
        users.forEach(user -> usersList.add(modelMapper.map(user, UserListDto.class)));
        return usersList;
    }


    //@Transactional
    public Set<Role> findRoleById(Set<Long> roleId) {
        return roleId.stream().map(s -> rolesRepository.findById(s).get()).collect(Collectors.toSet());


    }

    @Transactional
    public UserModel findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        return convertToUserModelDto(user);

    }


    public UserModel convertToUserModelDto(UserEntity user) {
        modelMapper.typeMap(UserEntity.class, UserModel.class).addMappings(mapping -> {
            mapping.map(source -> user.getRoles().stream().map(Role::getId).collect(Collectors.toSet()), UserModel::setRoles);

        });
        return modelMapper.map(user, UserModel.class);
    }


    public UserEntity convertToUserEntity(UserModel userModel) {
        modelMapper.typeMap(UserModel.class, UserEntity.class).addMappings(mapping -> {

            mapping.map(source -> (userModel.getRoles().stream().map(roleNameToRoleConverter::convert).collect(Collectors.toSet())), UserEntity::setRoles);

        });

        return modelMapper.map(userModel, UserEntity.class);
    }


    public UserCreateDto convertToUserCreateDto(UserEntity user) {
        modelMapper.typeMap(UserEntity.class, UserCreateDto.class).addMappings(mapping -> {

            mapping.map(source -> user.getRoles().stream().map(Role::getId).collect(Collectors.toSet()), UserCreateDto::setRoles);


        });

        return modelMapper.map(user, UserCreateDto.class);
    }


    public UserEntity convertUserCreateDtoToUserEntity(UserCreateDto userCreateDto) {
        modelMapper.typeMap(UserCreateDto.class, UserEntity.class).addMappings(mapping -> {

            mapping.map(source -> (userCreateDto.getRoles().stream().map(roleNameToRoleConverter::convert).collect(Collectors.toSet())), UserEntity::setRoles);

        });

        UserEntity userEntity = modelMapper.map(userCreateDto, UserEntity.class);
        System.out.println("Hello " + userEntity.getRoles());
        return userEntity;
    }


    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter::apply).collect(Collectors.toList());
    }

}
