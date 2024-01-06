/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author jsbal
 */
public class PersonaCensada {

    private String nombre;
    private String apellido;
    private int edad;
    private String dni;
    private String estadoCivil;
    private Censador censador;
    private String motivo;
    private String fecha;
    private String estadoEmocional;
    private Integer id;

    public PersonaCensada() {
    }

    public PersonaCensada(String nombre, String apellido, int edad, String dni, String estadoCivil, Censador censador, String motivo, String fecha, String estadoEmocional, Integer id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
        this.estadoCivil = estadoCivil;
        this.censador = censador;
        this.motivo = motivo;
        this.fecha = fecha;
        this.estadoEmocional = estadoEmocional;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Censador getCensador() {
        return censador;
    }

    public void setCensador(Censador censador) {
        this.censador = censador;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstadoEmocional() {
        return estadoEmocional;
    }

    public void setEstadoEmocional(String estadoEmocional) {
        this.estadoEmocional = estadoEmocional;
    }

    public Integer getId() {
        return id;
    }

  

    public void setId(int i) {
        this.id = id;
    }

 

}
