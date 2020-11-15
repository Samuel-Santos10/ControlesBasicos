package com.example.controlesbasicos;

public class Usuario {

    int id;
    String Nombre, Apellido, Usurio, Password;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String usurio, String password) {
        Nombre = nombre;
        Apellido = apellido;
        Usurio = usurio;
        Password = password;
    }

    public boolean isNull(){
        if(Nombre.equals("") && Apellido.equals("") && Usurio.equals("") && Password.equals("")){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", Usurio='" + Usurio + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getUsurio() {
        return Usurio;
    }

    public void setUsurio(String usurio) {
        Usurio = usurio;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
