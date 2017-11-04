/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.algorithm.impl;

import com.google.gson.Gson;
import com.projectKepler.services.algorithm.Algorithm;
import com.projectKepler.services.entities.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 *
 * @author blackphantom
 */
public class SimpleAlgorithm implements Algorithm {
    private HashMap<String,ArrayList<String>> graph = new HashMap<>();
    public String getImpact(String course,String url) throws IOException {
        InputStream is = new URL(url).openStream();
        Gson g = new Gson();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        Syllabus s = g.fromJson(rd, Syllabus.class);
        for(Course c : s.getCourses())
            graph.put(c.getNombre(), new ArrayList());
        for(Course c: s.getCourses()){
            if(graph.get(c.getPreReq())!=null && !graph.get(c.getPreReq()).equals("")){
                graph.get(c.getPreReq()).add(c.getNombre());
            }
            if(graph.get(c.getCoReq())!=null && !graph.get(c.getCoReq()).equals("")){
                graph.get(c.getCoReq()).add(c.getNombre());
            }
        }
        int pendientes=0;
        for(Course c: s.getCourses()) if(c.getEstado()=='P')
            pendientes+=c.getCreditos();
        return "Si cancela " + course + " le quedan: " +Integer.toString(pendientes)+" creditos por ver."; 
    }

}
