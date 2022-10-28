
package com.estudioBreak.Break.servicios;


import com.estudioBreak.Break.entidades.Image;
import com.estudioBreak.Break.exceptions.MyException;
import com.estudioBreak.Break.repositorios.ImageRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;
    
    
    //Método para guardar uma imagem
    public Image save(MultipartFile file) throws MyException{
        if (file != null) {
            try {
                
                Image image = new Image();
                
                image.setMime(file.getContentType());
                
                image.setNameImage(file.getName());
                
                image.setContent(file.getBytes());
                
                return imageRepository.save(image);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    //me quede aqui
    public Image actualizar(MultipartFile file, String idImage) throws MyException{
         if (file != null) {
            try {
                
                Image imagen = new Image();
                
                if (idImage != null) {
                    Optional<Image> response = imageRepository.findById(idImage);
                    
                    if (response.isPresent()) {
                        imagen = response.get();
                    }
                }
                
                imagen.setMime(file.getContentType());
                
                imagen.setNameImage(file.getName());
                
                imagen.setContent(file.getBytes());
                
                return imageRepository.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
        
    }
    
    @Transactional(readOnly = true)
	public List<Image> listarTodos() {
		return imageRepository.findAll();
	}
    
}
