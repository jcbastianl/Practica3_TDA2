/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import controlador.TDA.listas.DynamicList;
import java.util.UUID;

/**
 *
 * @author jsbal
 */
public class Censador {

    private String id;
    private String nombre;
    private String apellido;
    private String dni;
    private String direccion;
    private String telefono;
    private String correo;
    private DynamicList<PersonaCensada> personaCensada;
    
   
   
   
    public Censador() {
    this.id = UUID.randomUUID().toString();
  }

    public Censador(String id, String nombre, String apellido, String dni, String direccion, String telefono, String correo, DynamicList<PersonaCensada> personaCensada) {
       
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.personaCensada = personaCensada;
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public DynamicList<PersonaCensada> getPersonaCensada() {
        return personaCensada;
    }

    public void setPersonaCensada(DynamicList<PersonaCensada> personaCensada) {
        this.personaCensada = personaCensada;
    }

  
  
}
