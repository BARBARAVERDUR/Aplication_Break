
package com.estudioBreak.Break.controladores;

import com.estudioBreak.Break.entidades.Classes;
import com.estudioBreak.Break.exceptions.MyException;
import com.estudioBreak.Break.servicios.ClassService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/classes") //localhost:8080/classes
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class ClassController {
    
    @Autowired
    private ClassService classService;
    
    @GetMapping("/register")//localhost:8080/classes/register
    public String register(){
        return "class_form.html";
    }
    
    @PostMapping("/registration")
    public String registration(@RequestParam String className, ModelMap model)throws MyException{
        
        try{
            classService.createClass(className);
            
            model.put("success", "The class was created successfully");
            
        } catch (MyException ex) {
            
            model.put("error", ex.getMessage());
            
            return "class_form.html";
        }
        
        return "index.html";
    }
    
    
    @GetMapping("/list")
    public String classList(ModelMap model){
        
        List<Classes> classes = classService.classList();
        
        model.addAttribute("classes", classes);
        
        return "class_list.html";
        
    }
   
    
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable String id, ModelMap model)throws MyException{
        
        model.put("classes", classService.getOne(id));
        
        return "class_modify.html";
    }
    
    
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable String id,String className, ModelMap model) throws MyException{
        
        try{
            classService.modifyClass(id, className);
            
            return "redirect:../list";
            
        }catch (MyException ex){
            
            model.put("error",ex.getMessage());
            return "class_modify.html";
        }
    }



}
