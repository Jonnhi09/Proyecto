/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.projectkepler.persistence.AcudienteDAO;
import com.projectkepler.persistence.EstudianteDAO;
import com.projectkepler.persistence.SolicitudDAO;
import com.projectkepler.persistence.UniversidadDAO;
import com.projectkepler.services.ExcepcionServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelaciones;
import com.projectkepler.services.algorithm.Algorithm;
import com.projectkepler.services.entities.Acudiente;
import com.projectkepler.services.entities.ConsejeroAcademico;
import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Estudiante;
import com.projectkepler.services.entities.PlanDeEstudios;
import com.projectkepler.services.entities.ProgramaAcademico;
import com.projectkepler.services.entities.Solicitud;
import com.projectkepler.services.entities.Syllabus;
import com.projectkepler.services.graphRectificator.GraphRectificator;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.apache.ibatis.exceptions.PersistenceException;

/**
 *
 * @author danielagonzalez
 */
public class ServiciosCancelacionesImpl implements ServiciosCancelaciones{

    
    @Inject 
    private Algorithm algo ;
    
    @Inject 
    private GraphRectificator gRec;
    
    @Inject
    private EstudianteDAO estudiante;
    
    @Inject
    private SolicitudDAO solicitud;
    
    @Inject
    private UniversidadDAO universidad;
    
    @Inject 
    private AcudienteDAO acudiente;

    @Transactional
    @Override
    public String consultarPlanDeEstudioByIdEstudiante(int codigo) throws ExcepcionServiciosCancelaciones {
        String programa="";
        List<CourseStudent> asignaturas=null;
        try{
            programa=estudiante.loadEstudianteById(codigo).getAsignaturas();
            Gson g = new Gson();
            Type materias = new TypeToken<List<CourseStudent>>(){}.getType(); 
            asignaturas=g.fromJson(programa, materias);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el plan de estudios del estudiante "+codigo);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el plan de estudios del estudiante "+codigo,e);
        }
        CourseStudent[] materiasPlan=consultarPlanDeEstudios(estudiante.loadEstudianteProgramaById(codigo),estudiante.loadEstudianteVersionById(codigo)).getCourses();
        ArrayList<String> materiasEnEstudiante=new ArrayList<>();
        for (CourseStudent c:asignaturas){
            materiasEnEstudiante.add(c.getNemonico());
        }
        for (int j=0;j<materiasPlan.length;j++){
            for (int i=0;i<asignaturas.size();i++){
                if (materiasPlan[j].getNemonico().equals(asignaturas.get(i).getNemonico())){
                    asignaturas.get(i).setCoReq(materiasPlan[j].getCoReq());
                    asignaturas.get(i).setPreReq(materiasPlan[j].getPreReq());
                    asignaturas.get(i).setCreditos(materiasPlan[j].getCreditos());
                }else{
                    if ( !(materiasEnEstudiante.contains(materiasPlan[j].getNemonico())) ){
                        asignaturas.add(materiasPlan[j]);
                        materiasEnEstudiante.add(materiasPlan[j].getNemonico());
                    }
                }  
            }  
        }
        CourseStudent[] asignaturasSyllabus = new CourseStudent[asignaturas.size()];
        asignaturas.toArray(asignaturasSyllabus);
        Gson p = new GsonBuilder().setPrettyPrinting().create();
        String update= p.toJson(new Syllabus(estudiante.loadEstudianteProgramaById(codigo),estudiante.loadEstudianteVersionById(codigo),universidad.consultarPlanDeEstudios(estudiante.loadEstudianteProgramaById(codigo), estudiante.loadEstudianteVersionById(codigo)).getTotalCreditos(),asignaturasSyllabus));
        return update;
        
    }

    @Transactional
    @Override
    public List<CourseStudent> consultarAsignaturasByIdEstudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones {
        List<CourseStudent> asignaturas=new ArrayList<>();
        List<Syllabus> planes=null;
        try{
            planes=obtenerSyllabusEstudiante(codigoEstudiante);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e); 
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las asignaturas del estudiante "+codigoEstudiante);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las asignaturas del estudiante "+codigoEstudiante, e);
        }
        for (CourseStudent c: planes.get(0).getCourses()){
            String cadena="V";
            char estado=cadena.charAt(0);
            if (c.getEstado()==estado){
                asignaturas.add(c);
            }
        }
        return asignaturas;
    }

    @Transactional
    @Override
    public String consultarImpactoByEstudianteAsignatura(int codigoEstudiante, String[] nemonicoAsignatura) throws ExcepcionServiciosCancelaciones {
        String impacto=null;
        Gson g = new Gson();
        Syllabus s = g.fromJson(consultarPlanDeEstudioByIdEstudiante(codigoEstudiante), Syllabus.class);
        try{
            Solicitud request=solicitud.consultRequestByStudentAndId(codigoEstudiante, nemonicoAsignatura[0]);
            if (request==null || solicitud.consultRequestByStudentAndId(codigoEstudiante, nemonicoAsignatura[0]).getImpacto()==null ){
                impacto=algo.getImpact(nemonicoAsignatura, gRec.verify(s), s)[0];
            }else{
                impacto=solicitud.consultRequestByStudentAndId(codigoEstudiante, nemonicoAsignatura[0]).getImpacto();
            }
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el impacto de la asignatura "+nemonicoAsignatura+" del estudiante "+codigoEstudiante);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el impacto de una asignatura", e);
        }
        return impacto;
    }
    
    @Transactional
    @Override
    public String consultarProyeccionByEstudianteAsignatura(int codigoEstudiante, String[] nemonicoAsignatura) throws ExcepcionServiciosCancelaciones {
        String proyeccion=null;
        Gson g = new Gson();
        Syllabus s = g.fromJson(consultarPlanDeEstudioByIdEstudiante(codigoEstudiante), Syllabus.class);
        try{
            Solicitud request=solicitud.consultRequestByStudentAndId(codigoEstudiante, nemonicoAsignatura[0]);
            if (request==null || solicitud.consultRequestByStudentAndId(codigoEstudiante, nemonicoAsignatura[0]).getProyeccion()==null){
                proyeccion=algo.getImpact(nemonicoAsignatura, gRec.verify(s), s)[1];
            }else{
                proyeccion=solicitud.consultRequestByStudentAndId(codigoEstudiante, nemonicoAsignatura[0]).getProyeccion();
            }
            
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar la proyeccion de cancelar la asignatura "+nemonicoAsignatura);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar la proyeccion de cancelar una asignatura", e);
        }
        return proyeccion;
    }

    @Transactional
    @Override
    public List<Syllabus> obtenerSyllabusEstudiante(int codigo) throws ExcepcionServiciosCancelaciones {
        List<Syllabus> planesDeEstudio= new ArrayList<>();
        String programa="";
        String syllabusEstudiante="";
        try{
            syllabusEstudiante=consultarPlanDeEstudioByIdEstudiante(codigo);
            programa=universidad.loadSyllabusByStudent(codigo).getContenido();
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el plan de estudios del estudiante "+codigo+" y del programa al que pertenece");
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el plan de estudios de un estudiante y del programa", e);
        }
        Gson plan= new Gson();
        Syllabus planEstudiante= plan.fromJson(syllabusEstudiante, Syllabus.class);
        Syllabus syllabusPrograma= plan.fromJson(programa, Syllabus.class);
        planesDeEstudio.add(planEstudiante);
        planesDeEstudio.add(syllabusPrograma);
        
        return planesDeEstudio;
    }
    
    @Transactional
    @Override
    public void actualizarJustificacionById(int id, String justificacion, String materia, String impacto, String proyeccion) throws ExcepcionServiciosCancelaciones {
        Syllabus planE=obtenerSyllabusEstudiante(id).get(0);
        Estudiante student;
        int numero;
        boolean acuse=false;
        try{
            student=estudiante.loadEstudianteById(id);
            for (CourseStudent c:planE.getCourses()){
                if(c.getNombre().equals(materia)){
                    for (int i:c.getHistorialNotas()){
                        if(i==-1){
                            acuse=true;
                        }
                    }
                }
            }
            if (student.getNumeroMatriculas()<3){
                acuse=true;
            }
            numero=solicitud.consultarSolicitudes().size()+1;
            solicitud.updateJustification(id, materia, justificacion, numero, acuse, impacto,proyeccion);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo actualizar la justificacion para cancelar la asignatura "+materia);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al actualizar la justificacion de una solicitud", e);
        }

        if (student.getNumeroMatriculas()<3){
            acuse=true;
        }        
    }   
    
    @Transactional
    @Override
    public void actualizarNumeroMaximoDeCreditos(int creditos) throws ExcepcionServiciosCancelaciones{
        try{
            universidad.updateCredits(creditos);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se actualizaron el numero maximo de creditos por semestre");
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al actualizar el numero maximo de creditos", e);
        }
    }
    
    @Transactional
    @Override
    public int consultarNumeroMaximoDeCreditos() throws ExcepcionServiciosCancelaciones{
        int credits=0;
        try{
            credits=universidad.consultUniversity().getTotalCredits();
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el numero maximo de creditos");
        }catch (Exception e){
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el numero maximo de creditos", e);
        }
        return credits;
    }
    
    @Transactional
    @Override
    public Estudiante consultarEstudianteByCorreo(String correo) throws ExcepcionServiciosCancelaciones{
        Estudiante student=null;
        try{
            student=estudiante.consultStudentByEmail(correo);
            if (student==null){
                throw new ExcepcionServiciosCancelaciones("No existe un estudiante con el correo "+correo);
            }
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el estudiante por correo "+correo);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar un estudiante por correo", e);
        }
        return student;
    }
    
    @Transactional
    @Override
    public List<CourseStudent> consultarAsignaturasSinSolicitudByIdEStudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones{
        List<CourseStudent> asignaturas=new ArrayList<>();
        List<CourseStudent> asig;
        try{
            asig=solicitud.loadCoursesById(codigoEstudiante);
            List<String> tieneSolicitud=new ArrayList<>();
            for (CourseStudent co:asig){
                tieneSolicitud.add(co.getNemonico());
            }
            if (asig.size()==0){
                asignaturas=consultarAsignaturasByIdEstudiante(codigoEstudiante);
            }else{
                asignaturas=new ArrayList<>();
                List<String> as=new ArrayList<>();
                for (CourseStudent c:consultarAsignaturasByIdEstudiante(codigoEstudiante)){
                    for(CourseStudent a:asig){
                        if(!(c.getNemonico().equals(a.getNemonico()))){
                            if (!(as.contains(c.getNemonico())) && !(tieneSolicitud.contains(c.getNemonico()))){
                                as.add(c.getNemonico());
                                asignaturas.add(c);
                            }
                        }
                    }
                }
            }
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las asignaturas sin solicitud de cancelacion del estudiante "+codigoEstudiante);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las asignaturas sin solicitud de un estudiante", e);
        }
        return asignaturas;
    }
    
    @Override
    public List<ProgramaAcademico> consultarTodosProgramasAcademicos() throws ExcepcionServiciosCancelaciones {
        List<ProgramaAcademico> programas=null;
        try{
            programas=universidad.consultarProgramasAcademicos();
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar todos los programas academicos");
        }catch(Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperarado al consultar todos los programas academicos",e);
        }
        return programas;
    }

    @Override
    public Syllabus consultarPlanDeEstudios(String programa, int planDeEstudios) throws ExcepcionServiciosCancelaciones {
        PlanDeEstudios plan=null;
        try{
            plan=universidad.consultarPlanDeEstudios(programa, planDeEstudios);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el plan de estudios "+planDeEstudios+" del programa "+programa);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar un plan de estudios de un programa");
        }
        Gson gson= new Gson();
        Syllabus syllabus= gson.fromJson(plan.getContenido(), Syllabus.class);
        return syllabus;
    }

    @Override
    public ArrayList<Integer> consultarPlanesDeEstudiosPorPrograma(String programa) throws ExcepcionServiciosCancelaciones {
        ArrayList<Integer> planes= new ArrayList<>();
        ProgramaAcademico programaAc = null;
        try{
            programaAc=universidad.consultarProgramaAcademicoPorNombre(programa);
            
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudieron consultar los planes de estudios del programa "+programa);
        }catch(Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar todos los planes de estudios de un programa");
        }
        for (PlanDeEstudios p:programaAc.getPlanDeEstudio()){
            planes.add(p.getId());
        }
        return planes;
    }
    
    @Override
    public List<Solicitud> consultarSolicitudesDeCancelaciones(String consejero) throws ExcepcionServiciosCancelaciones{
        List<Solicitud> solicitudes;
        solicitudes=estudiante.consultRequest(consejero);
        
        try{
            solicitudes=estudiante.consultRequest(consejero);
            if (solicitudes==null){
                throw new ExcepcionServiciosCancelaciones("El consejero no tiene solicitudes");
            }
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudieron consultar las solicitudes del consejero "+consejero);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las solicitudes de un consejero");
        }
        return solicitudes ;
    }
    
    @Override
    public Estudiante consultarEstudiantePorSolicitud(int numero) throws ExcepcionServiciosCancelaciones{
        Estudiante student;
        try{
            student=estudiante.consultStudentByRequest(numero);
            if (student==null){
                throw new ExcepcionServiciosCancelaciones("La solicitud "+numero+" no existe");
            }
            student=estudiante.loadEstudianteById(student.getCodigo());
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el estudiante que realizo la solicitud "+numero);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar un estudiante por numero de solicitud");
        }
        
        return student;
    }

    @Override
    public void actualizarComentariosSolicitud(int numero,String comentarios) throws ExcepcionServiciosCancelaciones {
        try{
            if (comentarios.isEmpty()){
                throw new ExcepcionServiciosCancelaciones("Los comentarios no deben ser vacios");
            }   
            solicitud.updateComentariosSolicitud(numero,comentarios);
        }catch(PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo actualizar la solicitud "+numero);
        }catch(Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al alctualizar la solicitud numero "+numero);
        }
    }
        
    
    @Override
    public void actualizarEstadoSolicitud(int numero, String estado) throws ExcepcionServiciosCancelaciones{
        try{
            solicitud.updateStateRequest(numero,estado);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo actualizar el estado de la solicitud "+numero);
        }catch (Exception e){
            throw new ExcepcionServiciosCancelaciones("Error inesperado al actualizar el estado de la solicitud");
        }
    }

    @Override
    public void enviarSolicitudes(int id, String justificacion, List<CourseStudent> materias) throws ExcepcionServiciosCancelaciones {
        String courses[]=new String [materias.size()];
        for (int i=0; i<materias.size();i++){
            courses[i]=materias.get(i).getNemonico();
        }
        String impacto=consultarImpactoByEstudianteAsignatura(id,courses);
        String proyeccion=consultarProyeccionByEstudianteAsignatura(id, courses);
        for (CourseStudent course:materias){
            
            actualizarJustificacionById(id, justificacion, course.getNemonico(),impacto,proyeccion);
        }
    }

    @Override
    public Acudiente consultarAcudientePorStudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones {
        Acudiente acu;
        try{
            acu=acudiente.consultarAcudiantePorEstudiante(codigoEstudiante);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el acudiente del estudiante con codigo "+codigoEstudiante);
        }catch (Exception e){
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el acudiente");
        }
        return acu;
    }

    @Override
    public void actualizarAcuseSolicitud(int numero) throws ExcepcionServiciosCancelaciones {
        try{
            boolean seEnvia=true;
            List<Solicitud> solicitudes = estudiante.consultRequestByStudent(estudiante.consultStudentByRequest(numero).getCodigo());
            for (Solicitud s: solicitudes){
                if(s.getComentarios()==null){
                    seEnvia=false;
                }
            }
            if (seEnvia){
                solicitud.actualizarAcuseSolicitud(numero);
            }
            solicitud.consultRequestByStudentAndId(173183, "CALD").isAcuseRecibo();
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo actualizar la solicitud "+numero);
        }catch (Exception e){
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el acudiente");
        }
    }
    
    @Override
    public List<Solicitud> consultarSolicitudesPorEstudiante(int codigo) throws ExcepcionServiciosCancelaciones{
        List<Solicitud> solicitudes=new ArrayList<>();
        try{
            solicitudes=estudiante.consultRequestByStudent(codigo);
            if (solicitudes==null){
                throw new ExcepcionServiciosCancelaciones("El estudiante con codigo "+codigo+" no tiene solicitudes de cancelaciones");
            }
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las solicitudes de cancelaciones del estudiante con codigo "+codigo);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las solicitudes de cancelaciones del estudiante");
        }
        return solicitudes;
    }
    
    @Override
    public Estudiante consultarEstudianteById(int codigo) throws ExcepcionServiciosCancelaciones {
        Estudiante estu=null;
        try{
            estu=estudiante.loadEstudianteById(codigo);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar lel estudiante con codigo"+codigo);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el estudiante");
        }
        return estu;
    }
    
    @Override
    public Solicitud consultarSolicitudPorEstudianteYNemonico(int codigo,String nemonico) throws ExcepcionServiciosCancelaciones{
        Solicitud sol=null;
        try{
            sol=solicitud.consultRequestByStudentAndId(codigo, nemonico);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return sol;
    }

    @Override
    public List<Solicitud> consultarSolicitudes() throws ExcepcionServiciosCancelaciones {
        List<Solicitud> solicitudes=new ArrayList<>();
        try{
            solicitudes=solicitud.consultarSolicitudes();
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las solicitudes de cancelaciones");
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las solicitudes de cancelaciones");
        }
        return solicitudes;
    }

    @Override
    public ConsejeroAcademico consultarConsejeroPorEstudiante(int codigo) throws ExcepcionServiciosCancelaciones {
        ConsejeroAcademico conse=null;
        try{
            conse=estudiante.consultarConsejeroPorEstudiante(codigo);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el consejero del estudiante con codigo"+codigo);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el consejero");
        }
        return conse;
    }

    @Override
    public Solicitud consultarSolicitudPorNumero(int numero) throws ExcepcionServiciosCancelaciones {
        Solicitud sol=null;
        try{
            sol=solicitud.consultarSolicitud(numero);
        }catch (PersistenceException e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar la solicitud con numero: "+numero);
        }catch (Exception e){
            Logger.getLogger(ServiciosCancelacionesImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar la solicitud");
        }
        return sol;
    }
}
