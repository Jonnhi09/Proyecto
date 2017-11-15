/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.test;

import com.google.gson.Gson;
import com.projectKepler.services.entities.Syllabus;
import com.projectKepler.services.graphRectificator.GraphRectificator;
import com.projectKepler.services.graphRectificator.impl.GraphRectificatorImpl;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author blackphantom
 *
 *
 * Clases de equivalencia.
 *
 * CE1 Cuando Hay un grafo sin ciclos, y cumple con el programa . Resultado:
 * true.
 *
 * CE2 Cuando tiene ciclos, o no cumple con el programa . Resultado : cantidad
 * creditos cursos pendientes + las otras materias.
 *
 *
 *
 */
public class GraphRectificatorTest {

    private GraphRectificator gRec = new GraphRectificatorImpl();

    @Test
    public void TestC1() {
        Gson g = new Gson();

        Syllabus s = g.fromJson("{\n" +
"    \"programa\": \"ing. sistemas\",\n" +
"    \"vertion\": 13,        \n" +
"    \"courses\": [\n" +
"        {\n" +
"            \"nombre\": \"PREM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preRec\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CALD\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"PREM\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        }\n" +
"    ]\n" +
"}", Syllabus.class);
        Syllabus s2 = g.fromJson("{\n" +
"    \"programa\": \"ing. sistemas\",\n" +
"    \"vertion\": 13,        \n" +
"    \"courses\": [\n" +
"        {\n" +
"            \"nombre\": \"PREM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preRec\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CALD\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"PREM\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        }\n" +
"    ]\n" +
"}", Syllabus.class);
        HashMap<String,ArrayList<String>> a;
        a= gRec.verify(s, s2);
        Assert.assertFalse(a==null);
        
    }
    
    
    @Test
    public void TestC2() {
        Gson g = new Gson();
        gRec = new GraphRectificatorImpl() ;
        Syllabus s = g.fromJson("{\n" +
"    \"programa\": \"ing. sistemas\",\n" +
"    \"vertion\": 13,        \n" +
"    \"courses\": [\n" +
"        {\n" +
"            \"nombre\": \"PREM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"CALD\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CALD\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"PREM\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        }\n" +
"    ]\n" +
"}", Syllabus.class);
        Syllabus s2 = g.fromJson("{\n" +
"    \"programa\": \"ing. sistemas\",\n" +
"    \"vertion\": 13,        \n" +
"    \"courses\": [\n" +
"        {\n" +
"            \"nombre\": \"PREM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preRec\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CALD\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"PREM\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        }\n" +
"    ]\n" +
"}", Syllabus.class);
        Assert.assertTrue(null==gRec.verify(s, s2));
        
    }
}
