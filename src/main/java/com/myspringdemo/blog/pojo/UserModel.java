package com.myspringdemo.blog.pojo;

import com.myspringdemo.blog.models.Role;
import com.myspringdemo.blog.models.UserEntity;
import lombok.Data;

import java.util.Set;

@Data
public class UserModel {
   private Long id;
    private String username;
    private boolean enabled;
    private Set<Role> roles;


    public static UserEntity userModelToUserEntity(UserModel userModel){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userModel.getId());
        userEntity.setUsername(userModel.getUsername());
        userEntity.setEnabled(userModel.isEnabled());
        userEntity.setRoles(userModel.getRoles());
        return userEntity;

    }

    public static UserModel userEntityToUserModel(UserEntity userEntity){
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setEnabled(userEntity.isEnabled());
        userModel.setRoles(userEntity.getRoles());
        return userModel;

    }
}
