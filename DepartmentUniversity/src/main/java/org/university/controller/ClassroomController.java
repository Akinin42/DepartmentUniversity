package org.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.university.dto.ClassroomDto;
import org.university.service.ClassroomService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/classrooms")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ClassroomController {
    
    private static final String REDIRECT = "redirect:/classrooms";    
    ClassroomService classroomService;
    
    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("classroom", new ClassroomDto());
        model.addAttribute("classrooms", classroomService.findAllClassrooms());
        return "classrooms";
    }
    
    @PostMapping()
    public String add(@ModelAttribute("classroom") ClassroomDto classroom) {        
        classroomService.addClassroom(classroom);  
        return REDIRECT;
    }
    
    @DeleteMapping()
    public String delete(@ModelAttribute("classroom") ClassroomDto classroom) {        
        classroomService.delete(classroom);
        return REDIRECT;
    }
}
