/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Censador;

import controlador.DAO.DaoImplement;
import controlador.TDA.listas.DynamicList;
import logica.Censador;

/**
 *
 * @author jsbal
 */
public class CensadorControl extends DaoImplement<Censador> {

    private DynamicList<Censador> censadores;
    private Censador censador;

    public CensadorControl() {
        super(Censador.class);
    }

    public DynamicList<Censador> getCensadores() {
        censadores = all();
        return censadores;
    }

    public void setCensadores(DynamicList<Censador> censadores) {
        this.censadores = censadores;
    }

    public Censador getCensador() {
        if (censador == null) {
            censador = new Censador();
        }
        return censador;
    }

    public void setCensador(Censador censador) {
        this.censador = censador;
    }

    public Boolean persist() {
        return persist(censador);
    }
}
