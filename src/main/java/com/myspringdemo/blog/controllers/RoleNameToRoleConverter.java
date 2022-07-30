package com.myspringdemo.blog.controllers;

import com.myspringdemo.blog.models.Role;

import com.myspringdemo.blog.repo.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
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
          return rolesRepository.findById(source).orElse(null);
      //  return rolesMap.get(source);
        
    }
}
