package com.projectKepler.test;

import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelacionesFactory;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.entities.CourseStudent;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.ProgramaAcademico;
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
    public void consultarProgramaTest (){
        String programa="";
        try{
            programa=servicios.consultarPlanDeEstudioByIdEstudiante(173183);
            servicios.actualizarJustificacionById(2121465, "mis notas en primer y segundo tercio son muy bajas y no he aprendido lo suficiente", "CALD");
            
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(programa,"{ \"programa\": \"ing. sistemas\", \"version\": 13, \"courses\": [ { \"nombre\": \"PREM\", \"creditos\": 4, \"PreRec\": \"\", \"coReq\": \"\", \"historialNotas\": [35], \"tercios\": [21, 46, 40], \"estado\":\"A\" }, { \"nombre\": \"CALD\", \"creditos\": 4, \"preReq\": \"PREM\", \"coReq\": \"\", \"historialNotas\": [], \"tercios\": [34], \"estado\":\"V\" }, { \"nombre\": \"CIED\", \"creditos\": 4, \"preReq\": \"CALD\", \"coReq\": \"\", \"historialNotas\": [], \"tercios\": [], \"estado\":\"P\" }, { \"nombre\": \"FFIS\", \"creditos\": 4, \"preReq\": \"\", \"coReq\": \"\", \"historialNotas\": [50], \"tercios\": [50,50,50], \"estado\":\"A\" }, { \"nombre\": \"FIMF\", \"creditos\": 4, \"preReq\": \"FFIS\", \"coReq\": \"CALD\", \"historialNotas\": [], \"tercios\": [20], \"estado\":\"V\" }, { \"nombre\": \"FIEM\", \"creditos\": 4, \"preReq\": \"FIMF\", \"coReq\": \"CIED\", \"historialNotas\": [], \"tercios\": [], \"estado\":\"P\" } ] }");
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
            assertEquals(asignaturas.get(i),resultado.get(i).getNombre());
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
        assertEquals(resultado,"Si cancela FFIS le quedan: 20 creditos por ver.");
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
        
        assertEquals(resultado.get(0).getCourses().length,6);
        assertEquals(resultado.get(0).getCourses()[0].getTercios()[0],21);
        for (CourseStudent c:resultado.get(1).getCourses()){
            assertEquals(c.getEstado(),'P');
        }
    }
    
    @Test
    public void actualizarYConsultarCreditosProgramaTest(){
        int creditos=0;
        try{
            servicios.actualizarNumeroMaximoDeCreditos(22, "Ingenieria de sistemas");
            creditos=servicios.consultarNumeroMaximoDeCreditos("Ingenieria de sistemas");
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
        assertEquals(asig.get(0).getNombre(),"PREM");
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
        assertEquals(programas.get(0).getPlanDeEstudio().get(0).getId(),8);
        assertEquals(programas.get(0).getPlanDeEstudio().get(1).getId(),13);
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
        assertEquals(programa.getCourses().length,6);
    }
    
    @Test
    public void consultarPlanesDeEstudioPorProgramaTest(){
        ArrayList<Integer> planes= new ArrayList<>();
        ArrayList<Integer> resultado= new ArrayList<>();
        planes.add(13);
        planes.add(8);
        try{
            resultado=servicios.consultarPlanesDeEstudiosPorPrograma("Ingenieria de sistemas");
            assertEquals(resultado.size(),2);
        }catch(ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        for (int i=0;i<resultado.size();i++){
            assertEquals(resultado.get(i),planes.get(i));
        }
        
    }
    
    @Test
    public void actualizarSyllabusTest(){
        Syllabus programa=null;
        try{
            programa=servicios.consultarPlanDeEstudios("Ingenieria de sistemas", 13);
            programa.getCourses()[0].setNemonico("PREC");
            servicios.actualizarSyllabusEstudianteTabla(programa);
            programa=servicios.consultarPlanDeEstudios("Ingenieria de sistemas", 13);
            assertEquals(programa.getCourses()[0].getNemonico(),"PREC");
            programa.getCourses()[0].setPreReq("CIED");
            servicios.actualizarSyllabusEstudianteTabla(programa);
        }catch(ExcepcionServiciosCancelaciones e){
            assertEquals(e.getMessage(),"El plan de estudios esta mal formado");
        }
        
    }
} 

