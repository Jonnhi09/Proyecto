/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.graphRectificator.impl;

import com.projectKepler.services.graphRectificator.GraphRectificator;
import java.util.*;

/**
 *
 * @author bp
 */
public class GraphRectificatorImpl implements GraphRectificator{
    private ArrayList<String> marked;
    private ArrayList<String> stack;
    boolean noHaveCycle=true;
    
    @Override
    public boolean verify(HashMap<String,ArrayList<String>> graph,HashMap<String,ArrayList<String>> graph2) {
        
        for(String n: graph.keySet()){
            marked=new ArrayList<>();
            stack=new ArrayList<>();
            check(graph, n);
        }
        return noHaveCycle && graph.equals(graph2);
    }
    
    public void check(HashMap<String,ArrayList<String>> graph, String node){
        marked.add(node);
        stack.add(node);
        
        for(String s: graph.get(node)){
            if(!marked.contains(s))
                check(graph,s);
            else if(stack.contains(s)){
                noHaveCycle = false;
                return;
            }
        }
        stack.remove(node);
    }
    
    
}
