/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.entities;

import java.util.List;

/**
 *
 * @author 2108310
 */
public class ProgramaAcademico {
    private String nombre;
    private List<PlanDeEstudios> planesDeEstudio;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PlanDeEstudios> getPlanDeEstudio() {
        return planesDeEstudio;
    }
    public void setPlanDeEstudio(List<PlanDeEstudios> planesDeEstudio) {
        this.planesDeEstudio = planesDeEstudio;
    }
}
