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
public class Solicitud {
    private int numero;
    private String justificacion;
    private String impacto;
    private String proyeccion;
    private String comentariosConsejero;
    private String estado;
    private boolean acuseRecibo;
    private boolean avalConsejero;
    private Asignatura asignatura;
    private boolean necesitaAcuseRecibo;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public String getProyeccion() {
        return proyeccion;
    }

    public void setProyeccion(String proyeccion) {
        this.proyeccion = proyeccion;
    }

    public String getComentariosConsejero() {
        return comentariosConsejero;
    }

    public void setComentariosConsejero(String comentariosConsejero) {
        this.comentariosConsejero = comentariosConsejero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isAcuseRecibo() {
        return acuseRecibo;
    }

    public void setAcuseRecibo(boolean acuseRecibo) {
        this.acuseRecibo = acuseRecibo;
    }

    public boolean isAvalConsejero() {
        return avalConsejero;
    }

    public void setAvalConsejero(boolean avalConsejero) {
        this.avalConsejero = avalConsejero;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public boolean isNecesitaAcuseRecibo() {
        return necesitaAcuseRecibo;
    }

    public void setNecesitaAcuseRecibo(boolean necesitaAcuseRecibo) {
        this.necesitaAcuseRecibo = necesitaAcuseRecibo;
    }
    
    
    
}