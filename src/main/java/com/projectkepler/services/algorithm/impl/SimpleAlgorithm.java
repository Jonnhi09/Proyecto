/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services.algorithm.impl;


import com.projectkepler.services.entities.Syllabus;
import com.projectkepler.services.entities.CourseStudent;
import java.util.*;
import com.projectkepler.services.algorithm.ImpactAnalizer;

/**
 *
 * @author blackphantom
 */
public class SimpleAlgorithm implements ImpactAnalizer {

    @Override
    public String[] getImpact(String course, HashMap<String, ArrayList<String>> graph, Syllabus planS,int maxCredits) {
        return new String[]{"Si cancela " + course + " le quedan: " + Integer.toString(credits(course, planS)) + " de "+Integer.toString(planS.getTotalCredits()) + " creditos por ver",""};

    }
    @Override
    public String[] getImpact(String[] courses, HashMap<String, ArrayList<String>> graph, Syllabus planS,int maxCredits) {
        int x= credits(courses, planS);
        return new String[]{"Si cancela " + Arrays.toString(courses) + " le quedan: " + Integer.toString(x) + " de "+Integer.toString(planS.getTotalCredits()) + " creditos por ver", ""};
    }
    private int credits(String course,Syllabus planS) {
        int pendientes = 0;
        for (CourseStudent c : planS.getCourses()) {
            if (c.getEstado() == 'P' || c.getNemonico().equals(course) || Arrays.asList(c.getCoReq()).contains(course)) {
                pendientes += c.getCreditos();
            }
        }
        return pendientes;
    }

    private int credits(String[] course,Syllabus planS) {
        int pendientes = 0;
        for (CourseStudent c : planS.getCourses()) {
            if (c.getEstado() == 'P' || Arrays.asList(course).contains(c.getNemonico()) || isCoReq(c.getCoReq(), course)) {
                pendientes += c.getCreditos();
            }
        }
        return pendientes;
    }
    private boolean isCoReq(String[] coReq, String[] courses) {
        for (String s : courses) {
            if (Arrays.asList(coReq).contains(s)) {
                return true;
            }
        }
        return false;
    }
    
}