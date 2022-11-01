package com.estudioBreak.Break.controladores;

import com.estudioBreak.Break.entidades.Client;
import com.estudioBreak.Break.servicios.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageController {
    
    @Autowired
    ClientService clientService;
    
    
    @GetMapping("/profile/{dni}")
    public ResponseEntity<byte[]> userImage (@PathVariable String dni){
        
        Client client =clientService.getOne(dni);
        
        byte[] image = client.getImage().getContent();
        
        HttpHeaders headers = new HttpHeaders(); // Esta cabecera informa ao navegador que returnamos uma imagem
        
        headers.setContentType(MediaType.IMAGE_JPEG);
               
        //retornamos uma ResponseEntity en formato de bytes = imagem
        return new ResponseEntity<>(image,headers,HttpStatus.OK);
    }
    
    
}
