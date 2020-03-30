package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Гл авная страница");
        return "home";
    }
    //какую страницу хотим отслеживать
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Cтраница про нас");
        //какой шаблон будем вызывать
        return "about";
    }
}