/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.algorithm;

import com.projectKepler.services.entities.Syllabus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author blackphantom
 */
public interface Algorithm {
    //TODO: put the javaDoc
    public String[] getImpact(String course,HashMap<String,ArrayList<String>> graph, Syllabus planS);
}
