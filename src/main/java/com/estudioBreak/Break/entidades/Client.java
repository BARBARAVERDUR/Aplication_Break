
package com.estudioBreak.Break.entidades;

import com.estudioBreak.Break.enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Client {
    
    @Id
    private String dni;
    private String name;
    private String email;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
   
    @ManyToOne
    private Classes classes;
    
    private Double price;
    
    @Temporal(TemporalType.DATE)
    private Date disDate; //data de inscripcao
    
    @OneToOne
    private Image image;    

    public Client() {
    }

    public Client(String dni, String name, String email, String password, Rol rol, Classes classes, Double price, Date disDate, Image image) {
        this.dni = dni;
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.classes = classes;
        this.price = price;
        this.disDate = disDate;
        this.image = image;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
   
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Date getDisDate() {
        return disDate;
    }

    public void setDisDate(Date disDate) {
        this.disDate = disDate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    
}

   
