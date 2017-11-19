/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.graphRectificator;

import com.projectKepler.services.entities.Syllabus;
import java.util.*;

/**
 *
 * @author bp
 */
public interface GraphRectificator {
    /***
     * verificar si un plan de estudios no tiene ciclos y si coincide con el del programa .
     * @param plan1 el plan de estudios actual del estudiante
     * @param plan2 el plan de estudios del programa 
     * @return si el grafo no tiene ciclos y concuerda con el plan de estudios del programa retorna el grafo.
     */
    public HashMap<String,ArrayList<String>> verify(Syllabus plan1);
}
