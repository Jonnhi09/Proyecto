/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services.entities;

import java.util.List;

/**
 *
 * @author 2108310
 */
public class ProgramaAcademico {
    private String nombre;
    private List<PlanDeEstudios> planesDeEstudio;
    private List<Estudiante> estudiantes;

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

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }    
}
