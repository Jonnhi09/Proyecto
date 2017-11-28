/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services.algorithm;

import com.projectkepler.services.entities.Syllabus;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author blackphantom
 */
public interface ImpactAnalizer {
    /***
     * entrega el impacto del estudiante al cancelar una materia.
     * @param course el curso que se va a cancelar
     * @param graph el grafo del curso 
     * @param planS el plan de estudio
     * @return un arreglo de strings, el cual contiene el impacto y la proyeccion respectivamente
     */
    public String[] getImpact(String course,HashMap<String,ArrayList<String>> graph, Syllabus planS,int maxCredits);
    public String[] getImpact(String courses[],HashMap<String,ArrayList<String>> graph, Syllabus planS,int maxCredits);
}
