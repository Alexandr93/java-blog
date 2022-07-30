package com.myspringdemo.blog.controllers;

import com.myspringdemo.blog.models.ERole;
import com.myspringdemo.blog.models.Role;
import com.myspringdemo.blog.models.UserEntity;
import com.myspringdemo.blog.pojo.UserModel;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class UserController {

    UserService userService;


    public UserController( UserService userService) {

        this.userService = userService;

    }

    //добавляем в модель все роли с глобальной видимостью
    @ModelAttribute
    public void getAllRoles(Model model){

        Iterable<Role> roles =  userService.getRolesList();
        model.addAttribute("allRoles", roles);
    }

    @GetMapping("/users")
    public String usersListShow(Model model){
        List<UserModel> users = userService.usersList();
        model.addAttribute("users", users);

        return "users/user-list";
    }

    @GetMapping("/users/{username}/edit")
    public String userDetails(@PathVariable String username,  Model model){
        UserModel user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "users/user-details";
    }


    @PostMapping("/users/{username}/edit")
    public String userEdit(@ModelAttribute UserEntity user, @PathVariable String username){
       userService.updateUser(user);
       return "redirect:/users/"+user.getUsername()+"/edit";
    }



    @GetMapping("/users/add")
    public String addUser(UserEntity user, Model model){
        model.addAttribute("user", user);
        return "users/user-add";
    }


    @PostMapping("/users/add")
    public String userCreate(@ModelAttribute("user")@Valid UserEntity user, BindingResult bindingResult){
                           //получаем юзера с формы, за счет конвертера получаем сразу роли, а не айдишники ролей
        if (bindingResult.hasErrors()){

            return "users/user-add";
        }

        userService.createUser(user);

        return "redirect:/users";
    }
    @PostMapping("/users/{username}/deleteuser")
    public String userDelete(@PathVariable String username){
        userService.deleteUser(username);
        return "redirect:/users";
    }

}
