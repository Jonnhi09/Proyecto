/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.graphRectificator;

import com.projectKepler.services.entities.Syllabus;
import java.util.*;

/**
 *
 * @author bp
 */
public interface GraphRectificator {
    //TODO: put the java DOC
    public HashMap<String,ArrayList<String>> verify(Syllabus plan1,Syllabus plan2);
}
