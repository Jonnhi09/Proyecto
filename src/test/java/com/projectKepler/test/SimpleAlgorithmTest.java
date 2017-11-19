package com.projectKepler.test;

import com.google.gson.Gson;
import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelacionesFactory;
import com.projectKepler.services.algorithm.Algorithm;
import com.projectKepler.services.algorithm.impl.SimpleAlgorithm;
import com.projectKepler.services.entities.Syllabus;
import com.projectKepler.services.graphRectificator.GraphRectificator;
import com.projectKepler.services.graphRectificator.impl.GraphRectificatorImpl;
import org.junit.Assert;

import org.junit.Test;

/**
 *
 * @author blackphantom
 *
 *
 * Clases de equivalencia.
 *
 * CE1 Cuando se cancela una materia, se agregue en el proximo semestre y se
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

    private Algorithm a = new SimpleAlgorithm();
    private ServiciosCancelaciones sc= ServiciosCancelacionesFactory.getInstance().getTestingServiciosCancelaciones();
    private GraphRectificator gRec = new  GraphRectificatorImpl();
    
    
    @Test
    public void TestCE1() throws ExcepcionServiciosCancelaciones {
        Assert.assertEquals("no esta contando bien los creditos por ver",sc.consultarImpactoByEstudianteAsignatura(79328, "FFIS") , "Si cancela FFIS le quedan: 20 creditos por ver de 28.");
    }

    @Test
    public void TestCE2() throws ExcepcionServiciosCancelaciones {
        Assert.assertEquals("no esta contando bien los creditos por ver",sc.consultarImpactoByEstudianteAsignatura(173183, "CALD") , "Si cancela CALD le quedan: 16 creditos por ver de 28.");
    }
    
    @Test
    public void TestCE3() throws ExcepcionServiciosCancelaciones{
        Gson g = new Gson();
        Syllabus s = g.fromJson(sc.consultarPlanDeEstudioByIdEstudiante(173183), Syllabus.class);
        Assert.assertEquals("No esta cancelando las dos materias",a.getImpact(new String[]{"FIMF","CALD"}, gRec.verify(s), s)[0] , "Si cancela [FIMF, CALD] le quedan: 16 creditos por ver de 28.");
    }
}

