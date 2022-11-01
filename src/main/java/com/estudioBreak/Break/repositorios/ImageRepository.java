 package com.estudioBreak.Break.repositorios;

import com.estudioBreak.Break.entidades.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String>{
    
}
