/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.algorithm.impl;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.projectKepler.services.algorithm.Algorithm;
import com.projectKepler.services.entities.*;
import com.projectKepler.services.graphRectificator.GraphRectificator;
import com.projectKepler.services.graphRectificator.impl.GraphRectificatorImpl;
import com.sun.corba.se.impl.orbutil.graph.GraphImpl;
import java.util.*;

/**
 *
 * @author blackphantom
 */
public class SimpleAlgorithm implements Algorithm {

    //@Inject
    private GraphRectificator gRec = new GraphRectificatorImpl();

    private HashMap<String, ArrayList<String>> graph = new HashMap<>();

    @Override
    public String[] getImpact(String course, String json, String verify) {
        Gson g = new Gson();

        Syllabus s = g.fromJson(json, Syllabus.class);
        Syllabus s2 = g.fromJson(verify, Syllabus.class);
        if (gRec.verify(makeGraph(s), makeGraph(s2))) {
            int pendientes = 0;
            for (Course c : s.getCourses()) {
                if (c.getEstado() == 'P' || c.getNombre().equals(course) || c.getCoReq().contains(course)) {
                    pendientes += c.getCreditos();
                }
            }
            return new String[]{"Si cancela " + course + " le quedan: " + Integer.toString(pendientes) + " creditos por ver.", ""};
        }
        //TODO: make a exception if the graph have cycles or the graph is not the same.
        return null;
    }

    private HashMap<String, ArrayList<String>> makeGraph(Syllabus s) {
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        for (Course c : s.getCourses()) {
            graph.put(c.getNombre(), new ArrayList());
        }
        for (Course c : s.getCourses()) {
            if (graph.containsKey(c.getPreReq())) {
                graph.get(c.getNombre()).add(c.getPreReq());
            }
            if (graph.containsKey(c.getCoReq())) {
                graph.get(c.getNombre()).add(c.getCoReq());
            }
        }
        System.out.println(Arrays.asList(graph));

        return graph;
    }
}
