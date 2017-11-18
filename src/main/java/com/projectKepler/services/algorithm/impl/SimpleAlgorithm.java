/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.algorithm.impl;

import com.projectKepler.services.algorithm.Algorithm;
import com.projectKepler.services.entities.*;
import java.util.*;

/**
 *
 * @author blackphantom
 */
public class SimpleAlgorithm implements Algorithm {
    @Override
    public String[] getImpact(String course, HashMap<String, ArrayList<String>> graph,Syllabus planS) {
        int pendientes = 0;
        for (CourseStudent c : planS.getCourses()) {
            /*
            if (c.getEstado() == 'P' || c.getNombre().equals(course) || c.getCoReq().contains(course)) {
                pendientes += c.getCreditos();
            }*/
        }
        return new String[]{"Si cancela " + course + " le quedan: " + Integer.toString(pendientes) + " creditos por ver.", ""};
        
    }
}
