/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myspringdemo.blog.controllers;

/**
 *
 * @author admin
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

	@GetMapping("/")
	public String home(Model model) {
		//model.addAttribute("title", "Main page");
		return "redirect:/blog";

	}
        
        @GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About us");
		return "redirect:/blog";
	}

}


