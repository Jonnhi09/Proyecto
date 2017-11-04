/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.algorithm;

import java.io.IOException;

/**
 *
 * @author blackphantom
 */
public interface Algorithm {
    public String getImpact(String course,String url) throws IOException; 
}
