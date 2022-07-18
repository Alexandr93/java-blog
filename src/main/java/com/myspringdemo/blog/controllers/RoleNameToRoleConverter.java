package com.myspringdemo.blog.controllers;

import com.myspringdemo.blog.models.Role;

import com.myspringdemo.blog.repo.RolesRepository;
import com.myspringdemo.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RoleNameToRoleConverter implements Converter<Integer, Role>
{

    RolesRepository rolesRepository;

    public void setRolesMap() {
        rolesRepository.findAll().forEach(role -> {

            rolesMap.put(role.getId(), role);
        });
    }

    private  Map<Integer, Role> rolesMap = new HashMap<>();
    @Autowired
    public RoleNameToRoleConverter( RolesRepository rolesRepository) {
        this.rolesRepository =rolesRepository;
        setRolesMap();
    }

    public Map<Integer, Role> getRolesMap() {
        return rolesMap;
    }


    @Override
    public Role convert(Integer source) {
        
        return rolesMap.get(source);
        
    }
}
