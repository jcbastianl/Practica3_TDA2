/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control.Persona;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import logica.PersonaCensada;

/**
 *
 * @author jsbal
 */
public class PersonaControladora extends DaoImplement<PersonaCensada> {

    private DynamicList<PersonaCensada> listaPersonas;
    private PersonaCensada persona;

    public PersonaControladora() {
        super(PersonaCensada.class);
    }

    public DynamicList<PersonaCensada> getListaPersonas() {
        listaPersonas = all();
        return listaPersonas;
    }

    public void setListaPersonas(DynamicList<PersonaCensada> listaPersonas) {
        this.listaPersonas = listaPersonas;
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
        persona.setId(all().getLenght() + 1);
        return persist(persona);
    }


    

   public DynamicList<PersonaCensada> busquedaLineal(String texto, DynamicList<PersonaCensada> personas, String criterio) throws EmptyException {
    DynamicList<PersonaCensada> resultados = new DynamicList<>();

    for (int i = 0; i < personas.getLenght(); i++) {
        PersonaCensada persona = personas.getInfo(i);

        // Realizar la búsqueda según el criterio especificado
        switch (criterio) {
            case "nombre":
                if (persona.getNombre().equalsIgnoreCase(texto)) {
                    resultados.add(persona);
                }
                break;
            case "estadocivil":
                if (persona.getEstadoCivil().equalsIgnoreCase(texto)) {
                    resultados.add(persona);
                }
                break;
            case "apellido":
                if (persona.getApellido().equalsIgnoreCase(texto)) {
                    resultados.add(persona);
                }
                break;
            case "edad":
                // Si la edad es un entero, podrías buscarla con un valor numérico
                // Si la edad es una cadena, se puede hacer una búsqueda similar a las anteriores
                if (Integer.toString(persona.getEdad()).equalsIgnoreCase(texto)) {
                    resultados.add(persona);
                }
                break;
            default:
                // Manejar el caso si el criterio proporcionado no coincide con ninguno esperado
                System.out.println("Criterio de búsqueda no válido");
                break;
        }
    }
    return resultados;
}
// Método para guardar persona

    public boolean guardar() {
        if (listaPersonas == null) {
            listaPersonas = new DynamicList<>();
        }
        listaPersonas.add(getPersona());
        return true;
    }

    // Verificar posición disponible
    public int posVerificar() throws EmptyException {
        int bandera = 0;
        for (int i = 0; i < listaPersonas.getLenght(); i++) {
            if (listaPersonas.getInfo(i) == null) {
                bandera = i;
                break;
            }
        }
        return bandera;
    }

    // Imprimir lista de personas
    public void imprimir() throws EmptyException {
        for (int i = 0; i < listaPersonas.getLenght(); i++) {
            System.out.println(listaPersonas.getInfo(i));
        }
    }
}
