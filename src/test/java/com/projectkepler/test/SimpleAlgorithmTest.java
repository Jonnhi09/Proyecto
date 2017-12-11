package com.projectkepler.test;

import com.google.gson.Gson;
import com.projectkepler.services.ExcepcionServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelacionesFactory;
import com.projectkepler.services.algorithm.impl.SimpleAlgorithm;
import com.projectkepler.services.entities.Syllabus;
import com.projectkepler.services.graphRectificator.GraphRectificator;
import com.projectkepler.services.graphRectificator.impl.GraphRectificatorImpl;
import org.junit.Assert;

import org.junit.Test;
import com.projectkepler.services.algorithm.ImpactAnalizer;

/**
 *
 * @author blackphantom
 *
 *
 * Clases de equivalencia.
 *
 * CE1 Cuando se cancela una materia, s,18e agregue en el proximo semestre y se
 * sume. Resultado: cantidad Creditos cursos pendientes.
 *
 * CE2 Cuando se cancele una materia que es correquisito de otras materia y se
 * estan viendo, las otras materias se toman en cuenta para el siguiente
 * semestre. Resultado : cantidad creditos cursos pendientes + las otras
 * materias.
 *
 *
 *
 */
public class SimpleAlgorithmTest {

    private ImpactAnalizer a = new SimpleAlgorithm();
    private ServiciosCancelaciones sc= ServiciosCancelacionesFactory.getInstance().getTestingServiciosCancelaciones();
    private GraphRectificator gRec = new  GraphRectificatorImpl();
    
    @Test
    public void TestCE1() throws ExcepcionServiciosCancelaciones {
        Gson g = new Gson();
        Syllabus s = g.fromJson(sc.consultarPlanDeEstudioByIdEstudiante(173183), Syllabus.class);
        Assert.assertEquals("No esta cancelando las dos materias",a.getImpact("FIMF", gRec.verify(s), s,18)[0] , "Si cancela FIMF le quedan: 16 de 28 creditos por ver");
    
    }

    @Test
    public void TestCE2() throws ExcepcionServiciosCancelaciones {
        Gson g = new Gson();
        Syllabus s = g.fromJson(sc.consultarPlanDeEstudioByIdEstudiante(173183), Syllabus.class);
        Assert.assertEquals("No esta cancelando las dos materias",a.getImpact("CALD", gRec.verify(s), s,18)[0] , "Si cancela CALD le quedan: 16 de 28 creditos por ver");
    
    }
    
    @Test
    public void TestCE3() throws ExcepcionServiciosCancelaciones{
        Gson g = new Gson();
        Syllabus s = g.fromJson(sc.consultarPlanDeEstudioByIdEstudiante(173183), Syllabus.class);
        Assert.assertEquals("No esta cancelando las dos materias",a.getImpact(new String[]{"FIMF","CALD"}, gRec.verify(s), s,18)[0] , "Si cancela [FIMF, CALD] le quedan: 16 de 28 creditos por ver");
    }
}

