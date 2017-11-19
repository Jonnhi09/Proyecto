/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.entities;

import java.util.List;

/**
 *
 * @author danielagonzalez
 */
public class Universidad {
    private int totalCredits;
    private String nombre;
    private List<ProgramaAcademico> programas;

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ProgramaAcademico> getProgramas() {
        return programas;
    }

    public void setProgramas(List<ProgramaAcademico> programas) {
        this.programas = programas;
    }
}
