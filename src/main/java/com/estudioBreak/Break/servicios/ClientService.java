
package com.estudioBreak.Break.servicios;

import com.estudioBreak.Break.entidades.Classes;
import com.estudioBreak.Break.entidades.Client;
import com.estudioBreak.Break.entidades.Image;
import com.estudioBreak.Break.enumeraciones.Rol;
import com.estudioBreak.Break.exceptions.MyException;
import com.estudioBreak.Break.repositorios.ClassRepository;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.estudioBreak.Break.repositorios.ClientRepository;
import com.estudioBreak.Break.repositorios.ImageRepository;
import java.util.Date;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClientService implements UserDetailsService{
    
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private ClassRepository classRepository;
    
    @Autowired
    private ImageRepository imageRepository;
    
    @Autowired
    private ImageService imageService;
    
    
    //Criamos um novo cliente y persistimos no banco de dados
    @Transactional
    public void register(MultipartFile file,String dni,String name, String email,String password, String password2, Double price, String id) throws MyException{
        
        validate(dni, name, email, password, password2, price, id);
        
        
        Optional<Client> response = clientRepository.findById(dni);       
        Optional<Classes> classResponse = classRepository.findById(id);
                
        Client client = new Client();        
        Classes classes = new Classes();
        
        if(classResponse.isPresent()){
            
            classes = classResponse.get();
        }
                
        client.setDni(dni);
        client.setName(name);
        client.setEmail(email);
        client.setPassword(new BCryptPasswordEncoder().encode(password));
        client.setClasses(classes);
        client.setPrice(price);
        client.setDisDate(new Date());
        client.setRol(Rol.USER);
        
        Image image = imageService.save(file);
        
        client.setImage(image);
        
        clientRepository.save(client);
        
    }
    
    
    //Mostramos os usuarios registrados
    public List<Client> userList(){
        
        List<Client> clientes = new ArrayList();
        
        clientes = clientRepository.findAll();
        
        return clientes;
    }
    
    //Buscamos e modificamos a informacao de um usuário existente
    @Transactional
    public void modifyUser(MultipartFile file,String dni ,String name, String email,String password, String password2, Double price, String id )throws MyException{
        
        validate(dni, name, email, password, password2, price, id); //validamos que los campos esten compltos
        
        Optional<Client> response = clientRepository.findById(dni);
        Optional<Classes> classResponse = classRepository.findById(id);//El optional es un contenedor que devuelve un boolean
        
        Classes classes = new Classes();
        Client client = new Client(); //instanciamos el objeto vacio
        
        if(classResponse.isPresent()){ //verificamos que esté presente
            
            classes = classResponse.get(); //le asignamos el valor que nos devuelve el repositorio
        }
        
        if(response.isPresent()){ 
            
            client = response.get();
            
            client.setDni(dni);
            client.setName(name);
            client.setEmail(email);
            client.setPassword(new BCryptPasswordEncoder().encode(password));
            client.setClasses(classes);
            client.setPrice(price);
            
            String idImage = null;
            if(client.getImage() != null){
                
                idImage =client.getImage().getId();
            }
            
            Image image = imageService.actualizar(file, idImage);
            
            client.setImage(image);
            
            clientRepository.save(client); //persistimos en la base de datos
        }
        
    }
    
     public Client getOne(String id){
        return clientRepository.getOne(id);
    }
    
    @Transactional
    public void changeRol(String dni) {
        Optional<Client> response = clientRepository.findById(dni);

        if (response.isPresent()) {

            Client client = response.get();

            if (client.getRol().equals(Rol.USER)) {

                client.setRol(Rol.ADMIN);

            } else if (client.getRol().equals(Rol.ADMIN)) {
                client.setRol(Rol.USER);
            }
        }
    }
     
    
    //Fazemos a validacao da informacao
    private void validate(String dni, String name, String email,String password, String password2, Double price,String id ) throws MyException{
        
        if(dni.isEmpty() || dni == null){
            
            throw new MyException("The dni number can´t be empty or null");
        }
        if(name.isEmpty() || name == null){
            
            throw new MyException("The name can´t be empty or null");
        }
        if(email.isEmpty() || email == null){
            
            throw new MyException("The email adress can´t be empty or null");
        }
        
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MyException("Password cannot be empty, and must be longer than 5 digits");
        }

        if (!password.equals(password2)) {
            throw new MyException("The passwords entered must be the same");
        }
        if( id.isEmpty() || id == null){
            
            throw new MyException("Select the class type");
        }
        
      
         if(price == null){
            
            throw new MyException("Insert the price of class");
        }
       
        
    }
    
    //Captamos as informações da sessão
   @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Client client = clientRepository.searchByEmail(email);
        
        if(client != null){
            
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + client.getRol().toString());
            
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes(); 
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("clientsession", client);
            
            return new User(client.getEmail(), client.getPassword(), permisos);
        }else{
            return null;
        }
        
    }

   
    
    
}
