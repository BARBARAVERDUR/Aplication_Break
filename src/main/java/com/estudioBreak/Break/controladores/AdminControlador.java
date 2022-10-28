
package com.estudioBreak.Break.controladores;

import com.estudioBreak.Break.entidades.Client;
import com.estudioBreak.Break.exceptions.MyException;
import com.estudioBreak.Break.servicios.ClientService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private ClientService clientService;
    
    
    @GetMapping("/dashboard")
    public  String panelAdministrativo(){
        
        return "home.html";
    }
    
      @GetMapping("/list")
    public String clientList(ModelMap model){
        
        List<Client> client = clientService.userList();

        model.addAttribute("client", client);
        
        return "client_list.html";
    }
    
    //Troca o Rol do User   
    @GetMapping("/changeRol/{dni}")
    public String changeRol(@PathVariable String dni){
        
        clientService.changeRol(dni);
        
        return "redirect:/admin/list";
    }
    
    
  
}
