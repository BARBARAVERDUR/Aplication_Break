
package com.estudioBreak.Break.servicios;

import com.estudioBreak.Break.entidades.Classes;
import com.estudioBreak.Break.exceptions.MyException;
import com.estudioBreak.Break.repositorios.ClassRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClassService {
    
    @Autowired
    private ClassRepository classRepository;
    
    @Transactional
    public void createClass(String className)throws MyException{
        
        validate(className);
        
        Classes classes = new Classes();
        
        classes.setClassName(className);
        classes.setClassDate(new Date());
        
        classRepository.save(classes);
    }    
      
    
    
    @Transactional(readOnly = true)    
    public List<Classes> classList() { 
        
        List<Classes> classes = new ArrayList();
        
        classes = classRepository.findAll();
        
        return classes;
           
    }
    
    @Transactional
    public void modifyClass(String className, String id)throws MyException{
       
        validate(className);
        
      
        Optional<Classes> response = classRepository.findById(id);
        
        if(response.isPresent()){
            
            Classes classes = response.get();
            
            classes.setClassName(className);
            
           classRepository.save(classes);
        }
        
    } 
    
    
    public Classes getOne(String id){
        return classRepository.getOne(id);
    }
    
    
    private void validate(String className) throws MyException {

        if (className.isEmpty() || className == null) {

            throw new MyException("Select the class type");
        }
       
    }

     
   
    
}
