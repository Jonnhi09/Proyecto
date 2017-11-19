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
    public String[] getImpact(String course, HashMap<String, ArrayList<String>> graph, Syllabus planS) {
        return new String[]{"Si cancela " + course + " le quedan: " + Integer.toString(credits(course, planS)) + " de "+Integer.toString(planS.getTotalCredits()), ""};

    }
    @Override
    public String[] getImpact(String[] courses, HashMap<String, ArrayList<String>> graph, Syllabus planS) {
        int x=0;
        for(String s: courses)
            x+=credits(s, planS);
        return new String[]{"Si cancela " + Arrays.toString(courses) + " le quedan: " + Integer.toString(x) + " de "+Integer.toString(planS.getTotalCredits()), ""};
    }

    public int credits(String course,Syllabus planS) {
        int pendientes = 0;
        for (CourseStudent c : planS.getCourses()) {
            if (c.getEstado() == 'P' || c.getNemonico().equals(course) || Arrays.asList(c.getCoReq()).contains(course)) {
                pendientes += c.getCreditos();
            }
        }
        return pendientes;
    }
    
}