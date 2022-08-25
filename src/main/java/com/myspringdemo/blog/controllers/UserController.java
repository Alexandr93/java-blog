package com.myspringdemo.blog.controllers;

import com.myspringdemo.blog.dto.blog.Message;
import com.myspringdemo.blog.dto.user.RolesNamesDto;
import com.myspringdemo.blog.dto.user.UserCreateDto;
import com.myspringdemo.blog.dto.user.UserListDto;
import com.myspringdemo.blog.dto.user.UserModel;
import com.myspringdemo.blog.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {

    UserService userService;


    public UserController(UserService userService) {

        this.userService = userService;

    }

    //добавляем в модель все роли с глобальной видимостью
   /* @ModelAttribute
    public void getAllRoles(Model model){

        Iterable<RolesDto> roles =  userService.getRolesList();
        model.addAttribute("allRoles", roles);
    }*/

    @GetMapping("/users")
    public String usersListShow(Model model) {
        List<UserListDto> users = userService.usersList();
        model.addAttribute("users", users);

        return "users/user-list";
    }

    @GetMapping("/users/{username}/edit")
    public String userDetails(@PathVariable String username, Model model) {
        UserModel user = userService.findByUsername(username);
        List<RolesNamesDto> roles = userService.getUserRoles();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", user);
        return "users/user-details";
    }


    @PostMapping("/users/{username}/edit")
    public String userEdit(@ModelAttribute UserModel user, @PathVariable String username, HttpSession session) {
        userService.updateUser(user);
        session.setAttribute("message", new Message("User successfully updated", true, "success"));
        return "redirect:/users/" + user.getUsername() + "/edit";
    }


    @GetMapping("/users/add")
    public String addUser(Model model) {
        UserCreateDto user = new UserCreateDto();
        List<RolesNamesDto> roles = userService.getUserRoles();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", user);

        return "users/user-add";
    }


    @PostMapping("/users/add")
    public String userCreate(@ModelAttribute("user") @Valid UserCreateDto user, BindingResult bindingResult, HttpSession session) {
        //получаем юзера с формы, за счет конвертера получаем сразу роли, а не айдишники ролей

        if (bindingResult.hasErrors()) {
            System.out.println("Huinya");
            //return "users/user-add";
        }
        //user.getRoles().forEach(rolesDto -> System.out.println(rolesDto.getName()));
        userService.createUser(user);
        session.setAttribute("message", new Message("User successfully added", true, "success"));
        return "redirect:/users";
    }

    @PostMapping("/users/{username}/deleteuser")
    public String userDelete(@PathVariable String username, HttpSession session) {
        userService.deleteUser(username);
        session.setAttribute("message", new Message("User successfully deleted", true, "danger"));
        return "redirect:/users";
    }

}
