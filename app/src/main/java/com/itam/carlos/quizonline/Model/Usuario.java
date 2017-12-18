package com.itam.carlos.quizonline.Model;

/**
 * Created by carlos on 17/12/17.
 */

public class Usuario {
    private String nombre;
    private String password;
    private String mail;

    public Usuario() {}

    public Usuario(String nombre, String password, String mail) {
        this.nombre = nombre;
        this.password = password;
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
