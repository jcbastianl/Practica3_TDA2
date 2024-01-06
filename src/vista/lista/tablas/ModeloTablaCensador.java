/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.lista.tablas;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import javax.swing.table.AbstractTableModel;
import logica.Censador;


/**
 *
 * @author jsbal
 */
public class ModeloTablaCensador extends AbstractTableModel {
    private DynamicList<Censador> censadores;

    @Override
    public int getRowCount() {
        return censadores.getLenght();
    }

    @Override
    public int getColumnCount() {
        return 7; // Hay 7 atributos en la clase Censador
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Censador c = censadores.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (c != null) ? c.getId() : "";
                case 1:
                    return (c != null) ? c.getNombre() : "";
                case 2:
                    return (c != null) ? c.getApellido() : "";
                case 3:
                    return (c != null) ? c.getDni() : "";
                case 4:
                    return (c != null) ? c.getDireccion() : "";
                case 5:
                    return (c != null) ? c.getTelefono() : "";
                case 6:
                    return (c != null) ? c.getCorreo() : "";
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
                return "ID Censador";
            case 1:
                return "Nombre";
            case 2:
                return "Apellido";
            case 3:
                return "DNI";
            case 4:
                return "Dirección";
            case 5:
                return "Teléfono";
            case 6:
                return "Correo";
            default:
                return null;
        }
    }

    public DynamicList<Censador> getCensadores() {
        return censadores;
    }

    public void setCensadores(DynamicList<Censador> censadores) {
        this.censadores = censadores;
    }
}
