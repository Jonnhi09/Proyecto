/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.test;

import com.google.gson.Gson;
import com.projectkepler.services.entities.Syllabus;
import com.projectkepler.services.graphRectificator.GraphRectificator;
import com.projectkepler.services.graphRectificator.impl.GraphRectificatorImpl;
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
"    \"version\": 13,        \n" +
"    \"courses\": [\n" +
"        {\n" +
"            \"nombre\": \"preca\" ,\n" +
" 	    \"nemonico\": \"PREM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [],\n" +
"            \"coReq\": [],\n" +
"            \"historialNotas\": [35],\n" +
"            \"tercios\": [21, 46, 40],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"calculo 1\" ,\n" +
" 	    \"nemonico\": \"CALD\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [\"PREM\"],\n" +
"            \"coReq\": [],\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [34],\n" +
"            \"estado\":\"V\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"calculo 2\" ,\n" +
" 	    \"nemonico\": \"CIED\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [\"CALD\"],\n" +
"            \"coReq\": [],\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"fundamentos fisica\" ,\n" +
" 	    \"nemonico\": \"FFIS\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [],\n" +
"            \"coReq\": [],\n" +
"            \"historialNotas\": [50],\n" +
"            \"tercios\": [50,50,50],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"fisica 1\" ,\n" +
" 	    \"nemonico\": \"FIMF\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [\"FFIS\"],\n" +
"            \"coReq\": [\"CALD\"],\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [20],\n" +
"            \"estado\":\"V\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"fisica 2\" ,\n" +
" 	    \"nemonico\": \"FIEM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [\"FIMF\"],\n" +
"            \"coReq\": [\"CIED\"],\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        }\n" +
"    ]\n" +
"}", Syllabus.class);
        //Syllabus s2 = g.fromJson("", Syllabus.class);
        HashMap<String,ArrayList<String>> a;
        a= gRec.verify(s);
        Assert.assertFalse(a==null);
        
    }
    
    
    @Test
    public void TestC2() {
        Gson g = new Gson();
        gRec = new GraphRectificatorImpl() ;
        Syllabus s = g.fromJson("{\n" +
"    \"programa\": \"ing. sistemas\",\n" +
"    \"version\": 13,        \n" +
"    \"courses\": [\n" +
"        {\n" +
"            \"nombre\": \"preca\" ,\n" +
" 	    \"nemonico\": \"PREM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [\"PREM\"],\n" +
"            \"coReq\": [],\n" +
"            \"historialNotas\": [30],\n" +
"            \"tercios\": [30,30,30],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"calculo 1\" ,\n" +
" 	    \"nemonico\": \"CALD\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [\"PREM\"],\n" +
"            \"coReq\": [],\n" +
"            \"historialNotas\": [-1],\n" +
"            \"tercios\": [15],\n" +
"            \"estado\":\"V\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"calculo 2\" ,\n" +
" 	    \"nemonico\": \"CIED\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [\"CALD\"],\n" +
"            \"coReq\": [],\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"fundamentos fisica\" ,\n" +
" 	    \"nemonico\": \"FFIS\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [],\n" +
"            \"coReq\": [],\n" +
"            \"historialNotas\": [35],\n" +
"            \"tercios\": [35,45,30],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"fisica 1\" ,\n" +
" 	    \"nemonico\": \"FIMF\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [\"FFIS\"],\n" +
"            \"coReq\": [\"CALD\"],\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [45],\n" +
"            \"estado\":\"V\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"fisica 2\" ,\n" +
" 	    \"nemonico\": \"FIEM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": [\"FIMF\"],\n" +
"            \"coReq\": [\"CIED\"],\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        }\n" +
"    ]\n" +
"}", Syllabus.class);
        Assert.assertTrue(null==gRec.verify(s));
        
    }
}

