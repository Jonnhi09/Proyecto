/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.sampleapp.test;

import com.google.gson.Gson;
import com.projectKepler.services.entities.Course;
import com.projectKepler.services.entities.Syllabus;
import com.projectKepler.services.graphRectificator.GraphRectificator;
import com.projectKepler.services.graphRectificator.impl.GraphRectificatorImpl;
import java.util.ArrayList;
import java.util.Arrays;
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
        HashMap<String,ArrayList<String>> a,b;
        a= makeGraph(s);
        b=makeGraph(s2);
        Assert.assertTrue(gRec.verify(a,b));
        
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
        Assert.assertFalse(gRec.verify(makeGraph(s),makeGraph(s2)));
        
    }
}
