/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.entities;

/**
 *
 * @author blackphantom
 */
public class Course {
    private String nombre;
    private int creditos ;
    private String preReq;
    private String coReq;
    private int[] historialNotas;
    private int[] tercios;
    private char estado;
    

    public Course(String nombre, int creditos, String preReq, String coReq, int[] historialNotas, int[] tercios, char estado) {
        this.nombre = nombre;
        this.creditos = creditos;
        this.preReq = preReq;
        this.coReq = coReq;
        this.historialNotas = historialNotas;
        this.tercios = tercios;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public String getPreReq() {
        return preReq;
    }

    public String getCoReq() {
        return coReq;
    }

    public int[] getHistorialNotas() {
        return historialNotas;
    }

    public int[] getTercios() {
        return tercios;
    }

    public char getEstado() {
        return estado;
    }

    public void setHistorialNotas(int[] historialNotas) {
        this.historialNotas = historialNotas;
    }

    public void setTercios(int[] tercios) {
        this.tercios = tercios;
    }
    
    
}
