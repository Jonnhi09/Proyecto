/*

* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services.algorithm.impl;

import com.google.gson.Gson;
import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Syllabus;
import java.util.*;
import com.projectkepler.services.algorithm.ImpactAnalizer;

/**
 *
 * @author blackphantom
 */
public class AdavancedAlgorithm implements ImpactAnalizer {

    private ArrayList<ArrayList<String>> proyection;
    private Syllabus syllabus;
    public int maxCre;
    @Override
    public String[] getImpact(String course, HashMap<String, ArrayList<String>> graph, Syllabus planS,int maxCredits) {
        proyection = new ArrayList<>();
        maxCre=maxCredits;
        Gson g = new Gson();
        syllabus = planS;
        int x = solveYears(makeActualGraph(syllabus, course), syllabus.getTotalCredits());
        Collections.reverse(proyection);
        return new String[]{"Le quedarian: " + x + " por ver.", proyection.toString()};
        
    }

    @Override
    public String[] getImpact(String courses[], HashMap<String, ArrayList<String>> graph, Syllabus planS,int maxCredits) {
        proyection = new ArrayList<>();
        maxCre=maxCredits;
        Gson g = new Gson();
        syllabus = planS;
        int x = solveYears(makeActualGraph(syllabus, courses), syllabus.getTotalCredits());
        Collections.reverse(proyection);
        return new String[]{"Le quedarian: " + x + " por ver.", proyection.toString()};
    }

    private int solveYears(HashMap<String, ArrayList<String>> graph, int total) {
        if (total <= 0 || graph.isEmpty()) {
            return 0;
        } else {
            ArrayList<String> matOk = getMatsOk(graph);
            ArrayList<ArrayList<String>> config = getAllConfigs(matOk);
            int mini = Integer.MAX_VALUE;
            ArrayList<String> theBest = null;
            for (ArrayList<String> c : config) {
                int res = 1 + solveYears(diff(graph, c), total - sumCreds(c));
                if (mini > res) {
                    mini = res;
                    theBest = (ArrayList<String>) c.clone();
                }
            }
            proyection.add(theBest);
            return mini;
        }

    }

    private HashMap<String, ArrayList<String>> makeActualGraph(Syllabus s, String course) {
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        for (CourseStudent c : s.getCourses()) {
            if (c.getEstado() == 'P' || c.getNemonico().equals(course) || Arrays.asList(c.getCoReq()).contains(course)) {
                graph.put(c.getNemonico(), new ArrayList());
            }
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

    private HashMap<String, ArrayList<String>> makeActualGraph(Syllabus s, String courses[]) {
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        for (CourseStudent c : s.getCourses()) {
            if (c.getEstado() == 'P' || Arrays.asList(courses).contains(c.getNemonico()) || isCoReq(c.getCoReq(), courses)) {
                graph.put(c.getNemonico(), new ArrayList());
            }
        }
        for (CourseStudent c : s.getCourses()) {
            if (c.getEstado() == 'P' || Arrays.asList(courses).contains(c.getNemonico()) || isCoReq(c.getCoReq(), courses)) {
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
        for (String c : graph.keySet()) {
            if (isCoReqOf(res,c) && !havePreReqIn(res,c)) {
                res.add(c);
            }
        }
         
        return res;
    }
    private ArrayList<ArrayList<String>> res1 = new ArrayList<>();

    private ArrayList<ArrayList<String>> getAllConfigs(ArrayList<String> matOk) {
        int r = matOk.size();
        res1 = new ArrayList<>();
        while (res1.isEmpty()) {
            combinationUtil(matOk, matOk.size(), r, 0, new ArrayList<String>(), 0);
            r--;
        }
        return res1;
    }

    public void combinationUtil(List<String> arr, int n, int r, int index, ArrayList<String> data, int i) {
        if (index == r) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < r; j++) {
                temp.add(data.get(j));
            }
            int sumcre = sumCreds(temp);
            if (sumcre <= maxCre) { 
                res1.add(temp);
            }
            return;
        }
        if (i >= n) {
            return;
        }
        data.add(index, arr.get(i));
        combinationUtil(arr, n, r, index + 1, data, i + 1);
        combinationUtil(arr, n, r, index, data, i + 1);
    }

    private HashMap<String, ArrayList<String>> diff(HashMap<String, ArrayList<String>> graph, ArrayList<String> string) {
        HashMap<String, ArrayList<String>> res = (HashMap<String, ArrayList<String>>) graph.clone();
        for (String s : string) {
            res.remove(s);
        }

        for (String s : res.keySet()) {
            for (String p : string) {
                res.get(s).remove(p);
            }
        }

        return res;
    }

    private int sumCreds(ArrayList<String> c) {
        HashMap<String, Integer> matsCred = new HashMap<>();
        for (CourseStudent co : syllabus.getCourses()) {
            matsCred.put(co.getNemonico(), co.getCreditos());
        }
        int res = 0;
        for (String s : c) {
            res += matsCred.get(s);
        }
        return res;
    }

    private boolean isCoReq(String[] coReq, String[] courses) {
        for (String s : courses) {
            if (Arrays.asList(coReq).contains(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCoReqOf(ArrayList<String> res, String course) {
        for(CourseStudent c : syllabus.getCourses())
            if(c.getNemonico().equals(course))
                return isCoReq(c.getCoReq(),res.toArray(new String[res.size()])) ;
        return false;
    }

    private boolean havePreReqIn(ArrayList<String> res, String course) {
        for(CourseStudent c : syllabus.getCourses())
            if(c.getNemonico().equals(course))
                return isCoReq(c.getPreReq(),res.toArray(new String[res.size()])) ;
        return false;
    }

}
