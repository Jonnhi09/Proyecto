/*

* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.algorithm.impl;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.projectKepler.services.algorithm.Algorithm;
import com.projectKepler.services.entities.Course;
import com.projectKepler.services.entities.Syllabus;
import com.projectKepler.services.graphRectificator.GraphRectificator;
import com.projectKepler.services.graphRectificator.impl.GraphRectificatorImpl;
import java.util.*;

/**
 *
 * @author blackphantom
 */
public class AdavancedAlgorithm implements Algorithm {

    //@Inject
    private GraphRectificator gRec = new GraphRectificatorImpl();
    private int sem;
    private ArrayList<ArrayList<String>> proyection;
    private Syllabus syllabus;

    @Override
    public String[] getImpact(String course, String json,String verify) {
        proyection = new ArrayList<>();
        for (int i = 0; i<60 ; i++)
            proyection.add(new ArrayList<String>());
        Gson g = new Gson();
        syllabus = g.fromJson(json, Syllabus.class);
        sem = syllabus.getCreditsSemester();
        HashMap<String, ArrayList<String>> graph = makeGraph(syllabus);
        if (gRec.verify(graph,makeGraph(g.fromJson(verify, Syllabus.class)))) {
            return new String[]{Integer.toString(solveYears(makeActualGraph(syllabus, course), syllabus.getTotalCredits(), 0)), "JAJAJAJ"};
        }
        //TODO: make an Exception if the graph have a cycle
        return new String[]{"NO", "Funciona"};
    }

    private int solveYears(HashMap<String, ArrayList<String>> graph, int total, int semester) {
        if (total <= 0) {
            return 0;
        } else {
            ArrayList<String> matOk = getMatsOk(graph);
            ArrayList<ArrayList<String>> config = getAllConfigs(matOk);
            int mini = 24;
            for (ArrayList<String> c : config) {
                int res = 1 + solveYears(diff(graph, c), total - sumCreds(c), semester++);
                if (mini > res) {
                    mini = res;
                    proyection.set(semester, c);
                }
            }
            return mini;
        }

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

    private HashMap<String, ArrayList<String>> makeActualGraph(Syllabus s, String course) {
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        for (Course c : s.getCourses()) {
            if (c.getEstado() == 'P' || c.getNombre().equals(course) || c.getCoReq().equals(course)) {
                graph.put(c.getNombre(), new ArrayList());
            }
        }
        for (Course c : s.getCourses()) {
            if (c.getEstado() == 'P' || c.getNombre().equals(course) || c.getCoReq().equals(course)) {
                if (graph.containsKey(c.getPreReq())) {
                    graph.get(c.getNombre()).add(c.getPreReq());
                }
                if (graph.containsKey(c.getCoReq())) {
                    graph.get(c.getNombre()).add(c.getCoReq());
                }
            }
        }
        return graph;
    }

    private ArrayList<String> getMatsOk(HashMap<String, ArrayList<String>> graph) {
        ArrayList<String> res = new ArrayList<>();
        for (String c : graph.keySet()) {
            if (graph.get(c).isEmpty()) {
                res.add(c);
            }
        }
        return res;
    }

    private ArrayList<ArrayList<String>> getAllConfigs(ArrayList<String> matOk) {
        int total = (int) Math.pow(2d, Double.valueOf(matOk.size()));
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        for (int i = 1; i <total; i++) {
            ArrayList<String> temp =new ArrayList<>();
            String code = Integer.toBinaryString(total | total-i).substring(1);
            for (int j = 0; j < matOk.size(); j++) {
                if (code.charAt(j) == '1') {
                    temp.add(matOk.get(j));
                }
            }
            if (sumCreds(temp) <= sem) {
                res.add(temp);
                //FIXME: se puede redu el numero de iteraciones. recordar que se esta haciendo al revez
            }
                
        }
        System.err.println(res);
        return res;
    }

    private HashMap<String, ArrayList<String>> diff(HashMap<String, ArrayList<String>> graph, ArrayList<String> string) {
        HashMap<String, ArrayList<String>> res = (HashMap<String, ArrayList<String>>) graph.clone();
        for(String s:string)
            res.remove(s);
        
        for (String s : res.keySet()) {
            for(String p : string)
                res.get(s).remove(p);
        }
            
        return res;
    }

    private int sumCreds(ArrayList<String> c) {
        HashMap<String, Integer> matsCred = new HashMap<>();
        for (Course co : syllabus.getCourses()) {
            matsCred.put(co.getNombre(), co.getCreditos());
        }
        int res = 0;
        for (String s : c) {
            res += matsCred.get(s);
        }
        return res;
    }

}
