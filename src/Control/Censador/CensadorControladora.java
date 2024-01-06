/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control.Censador;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import logica.Censador;

/**
 *
 * @author jsbal
 */
public class CensadorControladora  extends DaoImplement<Censador>{
     private Censador censador = new Censador(); // Instancia de un objeto Censador

    private DynamicList<Censador> censadores; // Lista dinámica de censadores

    public CensadorControladora() {
        super(Censador.class); // Llamada al constructor de la clase padre con la clase Censador
    }

    // Método que permite guardar un censador en la lista
    public boolean guardar() {
        if (censadores == null) {
            censadores = new DynamicList<>(); // Crear la lista si aún no está inicializada
        }
        censadores.add(getCensador()); // Agregar el censador actual a la lista

        return true;
    }

    // Verificar la posición disponible para guardar un censador
    public int posVerificar() throws EmptyException {
        int bandera = 0;
        for (int i = 0; i < censadores.getLenght(); i++) {
            if (censadores.getInfo(i) == null) {
                bandera = i;
                break;
            }
        }
        return bandera;
    }

    // Imprimir la lista de censadores
    public void imprimir() throws EmptyException {
        for (int i = 0; i < censadores.getLenght(); i++) {
            System.out.println(censadores.getInfo(i));
        }
    }

    // Obtener el censador actual
    public Censador getCensador() {
        if (censador == null) {
            censador = new Censador(); // Crear un nuevo censador si aún no está inicializado
        }
        return censador;
    }

    // Establecer el censador actual
    public void setCensador(Censador censador) {
        this.censador = censador;
    }

    // Obtener la lista de censadores
    public DynamicList<Censador> getCensadores() {
        return censadores;
    }

    // Establecer la lista de censadores
    public void setCensadores(DynamicList<Censador> censadores) {
        this.censadores = censadores;
    }
}
