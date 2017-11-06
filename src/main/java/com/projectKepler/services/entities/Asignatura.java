/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.entities;

/**
 *
 * @author diana
 */
public class Asignatura {
    
    private String nemonico;
    private String nombre;
    private String programa;
    private int numCancelaciones;
    private int numPerdidas;
    
    public String getNemonico() {
        return nemonico;
    }

    public void setNemonico(String nemonico) {
        this.nemonico = nemonico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public int getNumCancelaciones() {
        return numCancelaciones;
    }

    public void setNumCancelaciones(int numCancelaciones) {
        this.numCancelaciones = numCancelaciones;
    }

    public int getNumPerdidas() {
        return numPerdidas;
    }

    public void setNumPerdidas(int numPerdidas) {
        this.numPerdidas = numPerdidas;
    }
    
}
