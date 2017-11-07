/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.controller.managedbeans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author dxmortem
 */
@ManagedBean(name = "solCancelBean")
@SessionScoped
public class SolCancelBean {
    List<String> materia;
    String estudiante;
    String materiaSeleccionada;
    boolean panelVisibility;
    
    
    public String getMateriaSeleccionada() {
        return materiaSeleccionada;
    }

    public void setMateriaSeleccionada(String materiaSeleccionada) {
        this.materiaSeleccionada = materiaSeleccionada;
    }
    
    public List<String> getMateria() {
        return materia;
    }

    public void setMateria(List<String> materia) {
        this.materia = materia;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public boolean isPanelVisibility() {
        return panelVisibility;
    }

    public void setPanelVisibility(boolean panelVisibility) {
        this.panelVisibility = panelVisibility;
    }
    
    /**
     * Creates a new instance of SolCancelBean
     */
    public SolCancelBean() {
        this.panelVisibility = false;
        this.materia = new ArrayList<>();
        for(int i = 0; i<10; i++){
            materia.add("Materia " + i);
        }
        estudiante = "Pepito";
    }
    
    public void makePanelVisible(){
        setPanelVisibility(true);
    }
    
    public void makePanelInvisible(){
        setPanelVisibility(false);
    }
    
    public void analisis(){
        //algo
        makePanelVisible();
    }
    
}
