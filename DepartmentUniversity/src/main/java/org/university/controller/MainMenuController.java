package org.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainMenuController {
    
    @GetMapping()
    public String mainMenu() {        
        return "mainmenu";
    }
}
