/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services.graphRectificator.impl;

import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Syllabus;
import com.projectkepler.services.graphRectificator.GraphRectificator;
import java.util.*;

/**
 *
 * @author bp
 */
public class GraphRectificatorImpl implements GraphRectificator {

    private ArrayList<String> marked;
    private ArrayList<String> stack;
    boolean noHaveCycle = true;

    /**
     * *
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
    public HashMap<String, ArrayList<String>> verify(Syllabus plan1) {
        HashMap<String, ArrayList<String>> g1;
        g1 = makeGraph(plan1);
        for (String n : g1.keySet()) {
            marked = new ArrayList<>();
            stack = new ArrayList<>();
            check(g1, n);
        }
        if (noHaveCycle) {
            return g1;
        } else {
            return null;
        }

    }

    /**
     * *
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
            for (String ss : c.getPreReq()) {
                if (graph.containsKey(ss)) {
                    graph.get(c.getNemonico()).add(ss);
                }
            }
            for (String ss : c.getCoReq()) {
                if (graph.containsKey(ss)) {
                    graph.get(c.getNemonico()).add(ss);
                }
            }
        }

        return graph;
    }

}
