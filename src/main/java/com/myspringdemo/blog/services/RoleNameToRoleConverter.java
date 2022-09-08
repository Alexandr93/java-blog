package com.myspringdemo.blog.services;

import com.myspringdemo.blog.models.Role;

import com.myspringdemo.blog.repo.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoleNameToRoleConverter implements Converter<Long, Role>
{

    private final RolesRepository rolesRepository;


    @Autowired
    public RoleNameToRoleConverter(RolesRepository rolesRepository) {
        this.rolesRepository =rolesRepository;
        setRolesMap();
    }
    public void setRolesMap() {
        rolesRepository.findAll().forEach(role -> {

            rolesMap.put(role.getId(), role);
        });
    }

    private  Map<Long, Role> rolesMap = new HashMap<>();


    public Map<Long, Role> getRolesMap() {
        return rolesMap;
    }


    @Override
    public Role convert(Long source) {
         //return rolesRepository.findById(source).orElse(null);
        return rolesMap.get(source);
        
    }
}
