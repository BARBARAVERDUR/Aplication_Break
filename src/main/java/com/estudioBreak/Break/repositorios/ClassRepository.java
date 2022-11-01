package com.estudioBreak.Break.repositorios;

import com.estudioBreak.Break.entidades.Classes;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Classes, String>{
   
    //Pesquisamos as aulas s/nome
    @Query
    ("SELECT c FROM Classes c WHERE c.className =: className")
    public Classes findByName(@Param("className")String className);
    
    //Pesquisamos todas as aulas s/data
    @Query
    ("SELECT c FROM Classes c WHERE c.classDate =: classDate")
    public List<Classes> searchClass (@Param("classDate")Date classDate);

}
