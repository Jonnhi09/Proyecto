/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services.entities;

import java.util.Date;

/**
 *
 * @author diana
 */
public class Solicitud {
    private int numero;
    private Date fecha;
    private String justificacion;
    private String impacto;
    private String proyeccion;
    private String comentarios;
    private String estado;
    private boolean acuseRecibo;
    private boolean avalConsejero;
    private CourseStudent asignatura;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios= comentarios;
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

    public CourseStudent getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(CourseStudent asignatura) {
        this.asignatura = asignatura;
    }

    public boolean isNecesitaAcuseRecibo() {
        return necesitaAcuseRecibo;
    }

    public void setNecesitaAcuseRecibo(boolean necesitaAcuseRecibo) {
        this.necesitaAcuseRecibo = necesitaAcuseRecibo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
