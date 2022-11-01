package com.estudioBreak.Break.repositorios;

import com.estudioBreak.Break.entidades.Client;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String>{
    
    //pesquisar usuario por numero de dni
   @Query
   ("SELECT c FROM Client c WHERE c.dni = :dni") 
   public Client searchByDni(@Param("dni") String dni);    
   
    //pesquisar usuario por endereco do email
   @Query
   ("SELECT c FROM Client c WHERE c.email = :email")
   public Client searchByEmail(@Param("email")String email);
   
   
   //pesquisar usuarios presentes numa aula s/data
  @Query
  ("SELECT c FROM Client c WHERE c.classes.classDate =:classDate")
   public List<Date> searchByDate(@Param("classDate")Date classDate);
      
}
