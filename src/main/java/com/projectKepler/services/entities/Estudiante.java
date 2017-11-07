/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.entities;

import java.util.List;
import static javax.ws.rs.client.Entity.json;


/**
 *
 * @author diana
 */
public class Estudiante {
    private int codigo;
    private String nombre;
    private int numeroPlanEstudio;
    private int numeroMatriculas;
    private String correo;
    private Acudiente acudiente;
    private List<Solicitud> solicitudes;
    private String programa;
    private String doblePrograma;

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

    public int getNumeroPlanEstudio() {
        return numeroPlanEstudio;
    }

    public void setNumeroPlanEstudio(int numeroPlanEstudio) {
        this.numeroPlanEstudio = numeroPlanEstudio;
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

    public Acudiente getAcudiente() {
        return acudiente;
    }

    public void setAcudiente(Acudiente acudiente) {
        this.acudiente = acudiente;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getDoblePrograma() {
        return doblePrograma;
    }

    public void setDoblePrograma(String doblePrograma) {
        this.doblePrograma = doblePrograma;
    }
    
    
}
