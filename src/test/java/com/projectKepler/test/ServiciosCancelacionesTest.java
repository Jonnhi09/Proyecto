package com.projectKepler.test;

import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelacionesFactory;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.entities.CourseStudent;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.ProgramaAcademico;
import com.projectKepler.services.entities.Solicitud;
import com.projectKepler.services.entities.Syllabus;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

import org.junit.Test;

public class ServiciosCancelacionesTest {
    private ServiciosCancelaciones servicios;
    
    @Before
    public void setUp(){
        servicios=ServiciosCancelacionesFactory.getInstance().getTestingServiciosCancelaciones();
    }
    
    @Test 
    public void consultarAsignaturaByIdEstudianteTest(){
        List<String> asignaturas=new ArrayList<String>();
        List<CourseStudent> resultado=null;
        try{
            resultado=servicios.consultarAsignaturasByIdEstudiante(2121465);
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        asignaturas.add("CALD");
        asignaturas.add("FIMF");
        assertEquals(asignaturas.size(),resultado.size());
        for (int i=0; i<asignaturas.size();i++){
            assertEquals(asignaturas.get(i),resultado.get(i).getNemonico());
        }

    }

    @Test 
    public void consultarImpactoByEstudianteTest(){
        String resultado="";
        try{
            resultado=servicios.consultarImpactoByEstudianteAsignatura(79328,"FFIS");
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(resultado,"Si cancela FFIS le quedan: 20 creditos por ver de 28.");
    }

    @Test 
    public void consultarProyeccionByEstudianteTest(){
        String resultado="";
        try{
            resultado=servicios.consultarProyeccionByEstudianteAsignatura(79328,"FFIS");
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(resultado,"FFIS,CALD,ALLI,LCAL y una electiva");
    }

    @Test 
    public void obtenerSyllabusEstudianteTest(){
        List<Syllabus> resultado= null;
        try{
            resultado=servicios.obtenerSyllabusEstudiante(79328);
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        
        assertEquals(resultado.get(0).getCourses().length,7);
        assertEquals(resultado.get(0).getCourses()[0].getTercios()[0],21);
        for (CourseStudent c:resultado.get(1).getCourses()){
            assertEquals(c.getEstado(),'P');
        }
    }
    
    @Test
    public void actualizarYConsultarCreditosProgramaTest(){
        int creditos=0;
        try{
            servicios.actualizarNumeroMaximoDeCreditos(22);
            creditos=servicios.consultarNumeroMaximoDeCreditos();
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(creditos,22);
    }
    
    @Test
    public void consultarEstudiantePorCorreoTest(){
        Estudiante estudiante=null;
        try{
            estudiante=servicios.consultarEstudianteByCorreo("diana.sanchez-m@mail.escuelaing.edu.co");
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(estudiante.getCorreo(),"diana.sanchez-m@mail.escuelaing.edu.co");
    }
    
    @Test
    public void consultarAsignaturasSinSolicitudTest(){
        List<CourseStudent> asig=null;
        try{
            asig=servicios.consultarAsignaturasSinSolicitudByIdEStudiante(79328);
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(asig.get(0).getNemonico(),"PREM");
    }
    
    @Test 
    public void consultarProgramasAcademicosTest(){
        List<ProgramaAcademico> programas=null;
        try{
            programas=servicios.consultarTodosProgramasAcademicos();
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(programas.size(),1);
        assertEquals(programas.get(0).getNombre(),"Ingenieria de sistemas");
        assertEquals(programas.get(0).getPlanDeEstudio().size(),2);
    }
    
    @Test
    public void consultarPlanDeEstudios(){
        Syllabus programa=null;
        try{
            programa=servicios.consultarPlanDeEstudios("Ingenieria de sistemas", 13);
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(programa.getPrograma(),"Ingenieria de sistemas");
        assertEquals(programa.getVersion(),13);
        assertEquals(programa.getCourses().length,7);
    }
    
    @Test
    public void consultarPlanesDeEstudioPorProgramaTest(){
        ArrayList<Integer> resultado= new ArrayList<>();
        try{
            resultado=servicios.consultarPlanesDeEstudiosPorPrograma("Ingenieria de sistemas");
            assertEquals(resultado.size(),2);
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        
    }
    
    @Test
    public void consultarSolicitudesConsejero() throws ExcepcionServiciosCancelaciones{
        List<Solicitud> solicitudes=new ArrayList<>();
        try{
            solicitudes=servicios.consultarSolicitudesDeCancelaciones("claudia.patricia@escuelaing.edu.co");
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(solicitudes.size(),3);
    }
    
} 
