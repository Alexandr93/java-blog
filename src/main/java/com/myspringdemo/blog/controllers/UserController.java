package com.myspringdemo.blog.controllers;

import com.myspringdemo.blog.models.Role;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.repo.RolesRepository;
import com.myspringdemo.blog.repo.UserRepository;
import com.myspringdemo.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public String usersListShow(Model model){

        Iterable<UserEntity> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "users/user-list";
    }

    @GetMapping("/users/{username}")
    public String userDetails(@PathVariable String username,  Model model, Principal principal){
        UserEntity user = userRepository.findByUsername(username);
        Iterable<Role> roles = rolesRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);



        return "users/user-details";
    }


    @PostMapping("/users/{username}")
    public String userRoleAdd(@RequestParam List<Integer> roles, @PathVariable String username, Model model){


        UserEntity userUpdate = userRepository.findByUsername(username);

        /*Set<Role> roles= roleee.stream()
                .map(r->rolesRepository.findByName(r))
                .collect(Collectors.toSet());*///находим роли которая передается с віпадающего списка в базе
        //userUpdate.setRoles(new HashSet<>(roleee));
        if(!roles.isEmpty()) {

            userUpdate.setRoles(userService.getUserRolesbyId(roles));
            //userUpdate.setRoles(userService.getUserRoles(roleee));//добавляем в список
            userRepository.save(userUpdate);
        }
        else{
            userUpdate.setRoles(null);
        }

        userRepository.save(userUpdate);
        return "redirect:/users/"+username;
    }



    @GetMapping("/users/add")
    public String addUser(Model model){
        Iterable<Role> allRoles = rolesRepository.findAll();

        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);

        return "users/user-add";
    }


    @PostMapping("/users/add")
    public String userSave(@ModelAttribute UserEntity user
                         ){
                           // @ModelAttribute UserEntity user){





      /*  UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));*/
        user.setEnabled(true);
       // user.setRoles(userService.getUserRoles(roles));

        userRepository.save(user);



        return "redirect:/users";
    }
    @PostMapping("/users/{username}/deleteuser")
    public String userDelete(@PathVariable String username){
        UserEntity user = userRepository.findByUsername(username);
        userRepository.delete(user);
        return "redirect:/users";
    }
}
