package com.projectkepler.test;

import com.projectkepler.services.ExcepcionServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelacionesFactory;
import com.projectkepler.services.ServiciosCancelaciones;
import com.projectkepler.services.entities.Acudiente;
import com.projectkepler.services.entities.ConsejeroAcademico;
import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Estudiante;
import com.projectkepler.services.entities.ProgramaAcademico;
import com.projectkepler.services.entities.Solicitud;
import com.projectkepler.services.entities.Syllabus;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
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
        String[] nemonico=new String[1];
        nemonico[0]="CALD";
        try{
            resultado=servicios.consultarImpactoByEstudianteAsignatura(2121465,nemonico);
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(resultado,"Si cancela [CALD] le quedan: 16 de 28 creditos por ver ademas le quedarian: 2 semestres por ver.");
    }

    @Test 
    public void consultarProyeccionByEstudianteTest(){
        String resultado="";
        String[] nemonico=new String[1];
        nemonico[0]="CALD";
        try{
            resultado=servicios.consultarProyeccionByEstudianteAsignatura(2121465,nemonico);
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(resultado,"[[CALD, FIMF], [CIED, FIEM]]");
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
        assertEquals(solicitudes.get(0).getNumero(),2);
        assertEquals(solicitudes.get(0).getAsignatura().getNemonico(),"CALD");
        assertEquals(solicitudes.get(0).getAsignatura().getNombre(),"Calculo Diferencial");
    }
    
    @Test
    public void actualizarComentariosTest() throws ExcepcionServiciosCancelaciones{
        try{
            servicios.actualizarComentariosSolicitud(2,"De acuerdo a las notas actuales de la asignatura FIMF si el estudiante se esfuerza lograra sacar adelante la materia");
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        Solicitud solicitud=servicios.consultarEstudiantePorSolicitud(2).getSolicitudes().get(0);
        assertEquals(solicitud.getComentarios(),"De acuerdo a las notas actuales de la asignatura FIMF si el estudiante se esfuerza lograra sacar adelante la materia");
    }

    @Test 
    public void cosultarAcudienteTest() throws ExcepcionServiciosCancelaciones{
        Acudiente acu=null;
        try{
            acu=servicios.consultarAcudientePorStudiante(2121465);
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(acu.getCorreo(),"yolanda@gmail.com");
        assertEquals(acu.getNombre(),"Yolanda");
    }
    
    @Test
    public void actualizarAcuseSolicitudTest() throws ExcepcionServiciosCancelaciones{
        try{
            servicios.actualizarAcuseSolicitud(1);
            servicios.actualizarAcuseSolicitud(3);
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        Solicitud solicitud=servicios.consultarEstudiantePorSolicitud(1).getSolicitudes().get(0);
        assertEquals(solicitud.isAcuseRecibo(),true);
        solicitud=servicios.consultarEstudiantePorSolicitud(3).getSolicitudes().get(0);
        assertEquals(solicitud.isAcuseRecibo(),false);
    }
    
    @Test
    public void consultarSolicitudesPorEstudianteTest(){
        List<Solicitud> solicitudes=new ArrayList<>();
        try{
            solicitudes=servicios.consultarSolicitudesPorEstudiante(173183);
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(solicitudes.size(),2);
        assertEquals(solicitudes.get(0).getNumero(),2);
        assertEquals(solicitudes.get(0).getAsignatura().getNemonico(),"CALD");
        assertEquals(solicitudes.get(0).getAsignatura().getNombre(),"Calculo Diferencial");
    }
    
    @Test
    public void consultarEstudiantePorSolicitud(){
        Estudiante estudiante=null;
        try{
            estudiante=servicios.consultarEstudiantePorSolicitud(2);
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(estudiante.getNombre(),"Pepito perez montenegro");
        assertEquals(estudiante.getSolicitudes().size(),2);
        assertEquals(estudiante.getSolicitudes().get(0).getNumero(),2);
        assertEquals(estudiante.getSolicitudes().get(0).getAsignatura().getNemonico(),"CALD");
        assertEquals(estudiante.getSolicitudes().get(0).getAsignatura().getNombre(),"Calculo Diferencial");
    }
    
    @Test 
    public void consultarEstudianteByIdTest(){
        Estudiante estudiante=null;
        try{
            estudiante=servicios.consultarEstudianteById(173183);
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(estudiante.getNombre(),"Pepito perez montenegro");
        assertEquals(estudiante.getSolicitudes().size(),2);
        assertEquals(estudiante.getSolicitudes().get(0).getAsignatura().getNombre(),"Calculo Diferencial");
        int numero=2;
        for (Solicitud s: estudiante.getSolicitudes()){
            assertEquals(s.getNumero(),numero);
            assertTrue(s.getAsignatura()!=null);
            numero++;
        }
    }
    
    @Test
    public void consultarSolicitudPorEstudianteNemonico(){
        Solicitud sol=null;
        try{
            sol=servicios.consultarSolicitudPorEstudianteYNemonico(173183,"CALD");
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(sol.getNumero(),2);
        assertEquals(sol.getAsignatura().getNemonico(),"CALD");
        assertEquals(sol.getAsignatura().getNombre(),"Calculo Diferencial");
    }
    
    @Test 
    public void consultarSolicitudesTest(){
        List<Solicitud> solicitudes=new ArrayList<>();
        try{
            solicitudes=servicios.consultarSolicitudes();
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(solicitudes.size(),3);
        assertEquals(solicitudes.get(0).getNumero(),1);
        assertEquals(solicitudes.get(0).getAsignatura().getNemonico(),"FFIS");
        assertEquals(solicitudes.get(0).getAsignatura().getNombre(),"Fundamentos de Fisica");
    }
    
    @Test 
    public void consultarConsjeroPorEstudianteTest(){
        ConsejeroAcademico consejero=null;
        try{
            consejero=servicios.consultarConsejeroPorEstudiante(79328);
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(consejero.getNombre(), "Claudia Patricia");
        assertEquals(consejero.getCorreo(), "claudia.patricia@escuelaing.edu.co");
        assertEquals(consejero.getEstudiantes().size(),1);
        assertEquals(consejero.getEstudiantes().get(0).getCodigo(),79328);
        assertEquals(consejero.getEstudiantes().get(0).getSolicitudes().size(),1);
        assertEquals(consejero.getEstudiantes().get(0).getSolicitudes().get(0).getNumero(),1);
        assertEquals(consejero.getEstudiantes().get(0).getSolicitudes().get(0).getAsignatura().getNombre(),"Fundamentos de Fisica");
    }
    
    @Test
    public void consultarSolicitudTest(){
        Solicitud sol=null;
        try{
            sol=servicios.consultarSolicitudPorNumero(1);
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
            assertEquals(sol.getJustificacion(),"Me consume mucho tiempo y estoy descuidando las otras materias" );
            assertEquals(sol.getAsignatura().getNombre(), "Fundamentos de Fisica");
    }
} 
