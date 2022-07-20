package com.myspringdemo.blog.controllers;

import com.myspringdemo.blog.models.Role;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.repo.RolesRepository;
import com.myspringdemo.blog.repo.UserRepository;
import com.myspringdemo.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RolesController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RolesRepository rolesRepository;



    @PostMapping("/users/{username}/deleterole")
    public String userRoleDelete(@RequestParam String roleee, @PathVariable String username, @ModelAttribute("user") UserEntity user){
        UserEntity userUpdate = userRepository.findByUsername(username);
        Role role = rolesRepository.findByName(roleee);//находим роль которая передается с віпадающего списка в базе

        userUpdate.deleteRole(role);//добавляем в список
        userRepository.save(userUpdate);

        return "redirect:/users/"+username+"/edit";
    }
}
