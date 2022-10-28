
package com.estudioBreak.Break.controladores;

import com.estudioBreak.Break.entidades.Classes;
import com.estudioBreak.Break.entidades.Client;
import com.estudioBreak.Break.exceptions.MyException;
import com.estudioBreak.Break.servicios.ClassService;
import com.estudioBreak.Break.servicios.ClientService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador { //localhost:8080/
    
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private ClassService classService;
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
    @GetMapping("/register")
    public String register(ModelMap modelo){
        
        List<Classes> classes = classService.classList();
        
        modelo.addAttribute("classes", classes);
        return "register.html";
    }
    
    @PostMapping("/registration")
    public String registro(MultipartFile file,@RequestParam String dni,@RequestParam String name, @RequestParam String email, @RequestParam String password, String password2, Double price,String id,ModelMap model){
        
        try {
            clientService.register(file,dni, name, email, password, password2, price, id);
            
            model.put("success", "Successfully registered user");
            return "index.html";   
     
        } catch (MyException ex) {
           
           List<Classes> classes = classService.classList(); 
            
           model.put("error", ex.getMessage());
           model.addAttribute("classes", classes);
           model.put("name", name);
           model.put("email",email);
            
           return "register.html";
       }
    }
    
    
   @GetMapping("/login")
    public String login(@RequestParam(required = false)String error, ModelMap modelo){
        if(error != null){
            modelo.put("error", "User or Password invalid");
        }
        
        return "login.html";
    }
    
  
    
   @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
   @GetMapping("/home")
   public String inicio(HttpSession session){
       
       Client logueado = (Client) session.getAttribute("clientsession");
       
       if(logueado.getRol().toString().equals("ADMIN")){
           
           return "redirect:/admin/dashboard";
       }
       return "home.html";
   }
   
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/profile")
    public String profile(ModelMap modelo,HttpSession session){
        Client client = (Client) session.getAttribute("clientsession");
        
         modelo.put("client", client);
        
         List<Classes> classes = classService.classList(); 
         modelo.addAttribute("classes", classes);
         
        return "client_modify.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/modify/{dni}")
    public String modify(MultipartFile file,@PathVariable String dni,String name, String email, String password,String password2, Double price, String id, ModelMap model) throws MyException{
        
        try{
            clientService.modifyUser(file,dni, name, email, password, password2, price, id);
            
            model.put("success", "Updated user successfully!");
            return "redirect:../home";
            
        }catch (MyException ex){
            
            model.put("error",ex.getMessage());
            model.put("name", name);
            model.put("dni", dni);
            model.put("email", email);
            
            List<Classes> classes = classService.classList();
            model.addAttribute("classes", classes);
            model.put("file", file);

            return "client_modify.html";
        }
    }

   
}
