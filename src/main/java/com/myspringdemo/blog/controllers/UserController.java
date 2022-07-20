package com.myspringdemo.blog.controllers;

import com.myspringdemo.blog.models.Role;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.repo.RolesRepository;
import com.myspringdemo.blog.repo.UserRepository;
import com.myspringdemo.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


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

    //добавляем в модель все роли с глобальной видимостью
    @ModelAttribute
    public void getAllRoles(Model model){
        Iterable<Role> roles = rolesRepository.findAll();
        model.addAttribute("allRoles", roles);
    }

    @GetMapping("/users")
    public String usersListShow(Model model){

        Iterable<UserEntity> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "users/user-list";
    }

    @GetMapping("/users/{username}/edit")
    public String userDetails(@PathVariable String username,  Model model){
        UserEntity user = userRepository.findByUsername(username);


        model.addAttribute("user", user);
        return "users/user-details";
    }


    @PostMapping("/users/{username}/edit")
    public String userRoleAdd(@ModelAttribute UserEntity user, @PathVariable String username){

        //костыль чтобы пароль менялся на старый
        user.setPassword(userRepository.findByUsername(user.getUsername()).getPassword());
        userRepository.save(user);
        return "redirect:/users/"+user.getUsername()+"/edit";
    }



    @GetMapping("/users/add")
    public String addUser(UserEntity user, Model model){


        model.addAttribute("user", user);


        return "users/user-add";
    }


    @PostMapping("/users/add")
    public String userSave(@ModelAttribute("user")@Valid UserEntity user, BindingResult bindingResult){
                           //получаем юзера с формы, за счет конвертера получаем сразу роли, а не айдишники ролей
        if (bindingResult.hasErrors()){

            return "users/user-add";
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));


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
