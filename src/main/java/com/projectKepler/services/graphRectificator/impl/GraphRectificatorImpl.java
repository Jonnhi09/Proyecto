/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.graphRectificator.impl;

import com.projectKepler.services.entities.CourseStudent;
import com.projectKepler.services.entities.Syllabus;
import com.projectKepler.services.graphRectificator.GraphRectificator;
import java.util.*;

/**
 *
 * @author bp
 */
public class GraphRectificatorImpl implements GraphRectificator {

    private ArrayList<String> marked;
    private ArrayList<String> stack;
    boolean noHaveCycle = true;
    
    /***
     * 
     * @param graph el grafo del plan de estudios
     * @param node la materia a retirar
     */
    public void check(HashMap<String, ArrayList<String>> graph, String node) {
        marked.add(node);
        stack.add(node);

        for (String s : graph.get(node)) {
            if (!marked.contains(s)) {
                check(graph, s);
            } else if (stack.contains(s)) {
                noHaveCycle = false;
                return;
            }
        }
        stack.remove(node);
    }

    @Override
    public HashMap<String, ArrayList<String>> verify(Syllabus plan1, Syllabus plan2) {
        HashMap<String, ArrayList<String>> g1, g2;
        g1 = makeGraph(plan1);
        g2 = makeGraph(plan2);
        for (String n : g1.keySet()) {
            marked = new ArrayList<>();
            stack = new ArrayList<>();
            check(g1, n);
        }
        if (noHaveCycle && g1.equals(g2)) {
            return g1;
        } else {
            return null;
        }

    }
    /***
     * 
     * @param s el plan de estudios a convertir
     * @return el grafo como un hashmap 
     */
    private HashMap<String, ArrayList<String>> makeGraph(Syllabus s) {
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        for (CourseStudent c : s.getCourses()) {
            graph.put(c.getNemonico(), new ArrayList());
        }
        for (CourseStudent c : s.getCourses()) {
            if (graph.containsKey(c.getPreReq())) {
                graph.get(c.getNemonico()).add(c.getPreReq());
            }
            if (graph.containsKey(c.getCoReq())) {
                graph.get(c.getNemonico()).add(c.getCoReq());
            }
        }

        return graph;
    }

}
