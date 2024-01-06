/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Persona;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import logica.PersonaCensada;


/**
 *
 * @author jsbal
 */
public class PersonaControl extends DaoImplement<PersonaCensada> {

    private DynamicList<PersonaCensada> personas;
    private PersonaCensada persona;

    public PersonaControl() {
        super(PersonaCensada.class);
    }

    public DynamicList<PersonaCensada> getPersonas() {
        personas = all();
        return personas;
    }

    public void setPersonas(DynamicList<PersonaCensada> personas) {
        this.personas = personas;
    }

    public PersonaCensada getPersona() {
        if (persona == null) {
            persona = new PersonaCensada();
        }
        return persona;
    }

    public void setPersona(PersonaCensada persona) {
        this.persona = persona; 
    }

    public Boolean persist() {
        return persist(persona);
    }

}