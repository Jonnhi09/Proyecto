/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.test;

import com.google.gson.Gson;
import com.projectkepler.services.ExcepcionServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelacionesFactory;
import com.projectkepler.services.algorithm.impl.AdvancedAlgorithm;
import com.projectkepler.services.entities.Syllabus;
import com.projectkepler.services.graphRectificator.GraphRectificator;
import com.projectkepler.services.graphRectificator.impl.GraphRectificatorImpl;
import junit.framework.Assert;
import org.junit.Test;
import com.projectkepler.services.algorithm.ImpactAnalizer;

/**
 *
 * @author bp
 * 
 * CE1 : Cuando se cancele una materia, quede pendiente para el proximo semestre
 * Result :  Cantidad de semestres faltantes, con su respectiva proyeccion.
 * 
 * CE2 : Cuandose cancele una materia corequsito de otra deberia quitar las dos materias
 * Result : Cantidad de semestres faltantes, con su respectiva proyeccion.
 * 
 * CE3 : Cuando se cancele mas de una materia
 * Result : Cantidad de semestres faltantes, con su respectiva proyeccion.
 * 
 * 
 */
public class AdvancedAlgorithmTest {
    ServiciosCancelaciones sc = ServiciosCancelacionesFactory.getInstance().getTestingServiciosCancelaciones();
    ImpactAnalizer a = new AdvancedAlgorithm() ;
    GraphRectificator gRec = new  GraphRectificatorImpl();
    
    
    @Test
    public void CE1Test() throws ExcepcionServiciosCancelaciones{
        Gson g = new Gson();
        Syllabus s = g.fromJson(sc.consultarPlanDeEstudioByIdEstudiante(173183), Syllabus.class);
        Assert.assertEquals("esta calculando mal los semestres por ver","Si cancela FIMF le quedan: 16 de 28 creditos por ver" + " ademas le quedarian: " + 2 + " semestres por ver." , a.getImpact("FIMF",gRec.verify(s) , s,18)[0]);
        Assert.assertEquals("no esta dando proyeccion correcta","[[CALD, FIMF], [CIED, FIEM]]", a.getImpact("FIMF",gRec.verify(s) , s,18)[1]);
    }
    
    @Test
    public void CE2Test() throws ExcepcionServiciosCancelaciones{
        Gson g = new Gson();
        Syllabus s = g.fromJson(sc.consultarPlanDeEstudioByIdEstudiante(173183), Syllabus.class);
        Assert.assertEquals("no esta teniendo en cuenta el corequsito","Si cancela CALD le quedan: 16 de 28 creditos por ver" + " ademas le quedarian: " + 2 + " semestres por ver." , a.getImpact("CALD",gRec.verify(s) , s,18)[0]);
        Assert.assertEquals("No esta dando bien la proyeccion","[[CALD, FIMF], [CIED, FIEM]]" , a.getImpact("CALD",gRec.verify(s) , s,18)[1]);
    }
    
    @Test
    public void CE3Test() throws ExcepcionServiciosCancelaciones{
        Gson g = new Gson();
        Syllabus s = g.fromJson(sc.consultarPlanDeEstudioByIdEstudiante(173183), Syllabus.class);
        Assert.assertEquals("no esta cancelando mas de 1 materia","Si cancela [CALD, FIMF] le quedan: 16 de 28 creditos por ver" + " ademas le quedarian: " + 2 + " semestres por ver." , a.getImpact(new String[]{"CALD","FIMF"},gRec.verify(s) , s,18)[0]);
        Assert.assertEquals("No esta dando bien la proyeccion","[[CALD, FIMF], [CIED, FIEM]]" , a.getImpact(new String[]{"CALD","FIMF"},gRec.verify(s) , s,18)[1]);

    }
    
    
}
