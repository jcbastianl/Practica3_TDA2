/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.lista.tablas;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import javax.swing.table.AbstractTableModel;
import logica.Censador;

import logica.PersonaCensada;


/**
 *
 * @author jsbal
 */
public class ModeloTablaPersonaCensada extends AbstractTableModel {
    private DynamicList<PersonaCensada> personas;

    @Override
    public int getRowCount() {
        return personas.getLenght();
    }

    @Override
    public int getColumnCount() {
        return 9; // Hay 9 atributos en la clase Persona
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            PersonaCensada persona = personas.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (persona != null) ? persona.getNombre() : "";
                case 1:
                    return (persona != null) ? persona.getApellido() : "";
                case 2:
                    return (persona != null) ? persona.getEdad() : "";
                case 3:
                    return (persona != null) ? persona.getDni() : "";
                case 4:
                    return (persona != null) ? persona.getEstadoCivil() : "";
                case 5:
                    return (persona != null && persona.getCensador() != null) ? persona.getCensador().getNombre(): "";
                case 6:
                    return (persona != null && persona.getCensador() != null) ? persona.getCensador().getId(): "";
                case 7:
                    return (persona != null) ? persona.getFecha() : "";
                case 8:
                    return (persona != null) ? persona.getEstadoEmocional() : "";
                case 9:
                    return (persona != null) ? persona.getMotivo() : "";
                default:
                    return null;
            }
        } catch (EmptyException ex) {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nombre";
            case 1:
                return "Apellido";
            case 2:
                return "Edad";
            case 3:
                return "DNI";
            case 4:
                return "Estado Civil";
            case 5:
                return "Censador";
            case 6:
                return "Id_Censador";
            case 7:
                return "Fecha";
            case 8:
                return "Estado Emocional";
            case 9:
                return "ID Persona";
            default:
                return null;
        }
    }

    public DynamicList<PersonaCensada> getPersonas() {
        return personas;
    }

    public void setPersonas(DynamicList<PersonaCensada> personas) {
        this.personas = personas;
    }

    
public void setPersonaCensada(DynamicList<PersonaCensada> busquedaLineal) {
        this.personas= busquedaLineal;
    }
    
}