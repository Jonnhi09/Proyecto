package com.projectkepler.test;

import com.projectkepler.services.ExcepcionServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelacionesFactory;
import com.projectkepler.services.ServiciosCancelaciones;
import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Estudiante;
import com.projectkepler.services.entities.ProgramaAcademico;
import com.projectkepler.services.entities.Solicitud;
import com.projectkepler.services.entities.Syllabus;
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
        List<String> asignaturas=new ArrayList<>();
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
        assertEquals(resultado,"Le quedarian: 3 por ver.");
    }

    @Test 
    public void consultarProyeccionByEstudianteTest(){
        String resultado="";
        try{
            resultado=servicios.consultarProyeccionByEstudianteAsignatura(79328,"FFIS");
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(resultado,"[[CALD, FFIS], [CIED, FIMF], [FIEM]]");
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
            e.toString();
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
    
    @Test
    public void actualizarComentariosTest() throws ExcepcionServiciosCancelaciones{
        System.out.println(servicios.consultarEstudiantePorSolicitud(3).getSolicitudes().get(0).getComentarios());
        try{
            servicios.actualizarComentariosSolicitud(3,"De acuerdo a las notas actuales de la asignatura FIMF si el estudiante se esfuerza lograra sacar adelante la materia");
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        Solicitud solicitud=servicios.consultarEstudiantePorSolicitud(3).getSolicitudes().get(0);
        assertEquals(solicitud.getComentarios(),"De acuerdo a las notas actuales de la asignatura FIMF si el estudiante se esfuerza lograra sacar adelante la materia");
    }
} 