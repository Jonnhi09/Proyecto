/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.test;

import com.google.gson.Gson;
import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelacionesFactory;
import com.projectKepler.services.algorithm.Algorithm;
import com.projectKepler.services.algorithm.impl.AdavancedAlgorithm;
import com.projectKepler.services.entities.Syllabus;
import com.projectKepler.services.graphRectificator.GraphRectificator;
import com.projectKepler.services.graphRectificator.impl.GraphRectificatorImpl;
import com.projectKepler.services.impl.ServiciosCancelacionesImpl;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author bp
 * 
 * CE1 : Cuando se cancele una materia, quede pendiente para el proximo semestre
 * Result :  Cantidad de semestres faltantes.
 * 
 * CE2 : Cuandose cancele una materia corequsito de otra deberia quitar las dos materias
 * Result : Cantidad de semestres faltantes.
 * 
 * CE3 : Cuando se cancele mas de una materia
 * Result : Cantidad de semestres faltantes.
 * 
 * 
 * 
 */
public class AdvancedAlgorithmTest {
    ServiciosCancelaciones sc = ServiciosCancelacionesFactory.getInstance().getTestingServiciosCancelaciones();
    Algorithm a = new AdavancedAlgorithm() ;
    GraphRectificator gRec = new  GraphRectificatorImpl();
    
    
    @Test
    public void CE1Test() throws ExcepcionServiciosCancelaciones{
        Gson g = new Gson();
        Syllabus s = g.fromJson(sc.consultarPlanDeEstudioByIdEstudiante(173183), Syllabus.class);
        Assert.assertEquals("esta canculando mal los semestres por ver","Le quedarian: 2 por ver." , a.getImpact("FIMF",gRec.verify(s) , s)[0]);
    }
    
    @Test
    public void CE2Test() throws ExcepcionServiciosCancelaciones{
        Gson g = new Gson();
        Syllabus s = g.fromJson(sc.consultarPlanDeEstudioByIdEstudiante(173183), Syllabus.class);
        Assert.assertEquals("esta canculando mal los semestres por ver","Le quedarian: 3 por ver." , a.getImpact("CALD",gRec.verify(s) , s)[0]);
    }
    
    @Test
    public void CE3Test() throws ExcepcionServiciosCancelaciones{
        Gson g = new Gson();
        Syllabus s = g.fromJson(sc.consultarPlanDeEstudioByIdEstudiante(173183), Syllabus.class);
        Assert.assertEquals("esta canculando mal los semestres por ver","Le quedarian: 3 por ver." , a.getImpact(new String[]{"CALD",""},gRec.verify(s) , s)[0]);
    }
    
    
}
