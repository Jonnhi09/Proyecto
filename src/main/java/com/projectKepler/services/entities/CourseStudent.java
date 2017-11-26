/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services.entities;

/**
 *
 * @author blackphantom
 */
public class CourseStudent {
    private String nombre;
    private int codigoEstudiante;
    private int creditos ;
    private String[] preReq;
    private String[] coReq;
    private int[] historialNotas;
    private int[] tercios;
    private char estado;
    private int numCancelaciones;
    private String nemonico;
    


    public String getNombre() {
        return nombre;
    }
    
    public int getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(int codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public int getCreditos() {
        return creditos;
    }

    public String[] getPreReq() {
        return preReq;
    }

    public String[] getCoReq() {
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
    
    public int getNumCancelaciones() {
        return numCancelaciones;
    }

    public void setNumCancelaciones(int numCancelaciones) {
        this.numCancelaciones = numCancelaciones;
    }

    public String getNemonico() {
        return nemonico;
    }

    public void setNemonico(String nemonico) {
        this.nemonico = nemonico;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public void setPreReq(String[] preReq) {
        this.preReq = preReq;
    }

    public void setCoReq(String[] coReq) {
        this.coReq = coReq;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
    
    
}
