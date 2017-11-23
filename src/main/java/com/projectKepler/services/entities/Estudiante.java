/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.entities;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author diana
 */
public class Estudiante {
    private int codigo;
    private String nombre;
    private int numeroMatriculas;
    private String correo;
    private List<Solicitud> solicitudes;
    private Acudiente acudiente;
    private String asignaturas;
    private int versionPlanDeEstudio;
    private String programaAcademico;
    
    
    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroMatriculas() {
        return numeroMatriculas;
    }

    public void setNumeroMatriculas(int numeroMatriculas) {
        this.numeroMatriculas = numeroMatriculas;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public int getVersionPlanDeEstudio() {
        return versionPlanDeEstudio;
    }

    public void setVersionPlanDeEstudio(int versionPlanDeEstudio) {
        this.versionPlanDeEstudio = versionPlanDeEstudio;
    }

    public String getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(String asignaturas) {
        this.asignaturas = asignaturas;
    }

    public String getProgramaAcademico() {
        return programaAcademico;
    }

    public void setProgramaAcademico(String programa) {
        this.programaAcademico = programa;
    }

    public Acudiente getAcudiente() {
        return acudiente;
    }

    public void setAcudiente(Acudiente acudiente) {
        this.acudiente = acudiente;
    }
}
