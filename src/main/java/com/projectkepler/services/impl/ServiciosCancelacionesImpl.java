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
import org.apache.log4j.Logger;
import javax.transaction.Transactional;
import org.apache.ibatis.exceptions.PersistenceException;
import com.projectkepler.services.algorithm.ImpactAnalizer;

/**
 *
 * @author danielagonzalez
 */
public class ServiciosCancelacionesImpl implements ServiciosCancelaciones{

    
    @Inject 
    private ImpactAnalizer algo ;
    
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
    
    private static final Logger LOG = Logger.getLogger(ServiciosCancelacionesImpl.class);

    @Transactional
    @Override
    public String consultarPlanDeEstudioByIdEstudiante(int codigo) throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta el plan de estudio del estudiante con codigo "+codigo);
        String programa="";
        List<CourseStudent> asignaturas=null;
        try{
            LOG.debug("Se realiza la consulta del plan de estudio a EstudianteDAO");
            programa=estudiante.loadEstudianteById(codigo).getAsignaturas();
            Gson g = new Gson();
            Type materias = new TypeToken<List<CourseStudent>>(){}.getType(); 
            asignaturas=g.fromJson(programa, materias);
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar el plan de estudios del estudiante "+codigo,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el plan de estudios del estudiante "+codigo);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar el plan de estudios del estudiante "+codigo,e);
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
        LOG.info("Consultan las asignaturas del estudiante con codigo "+codigoEstudiante);
        List<CourseStudent> asignaturas=new ArrayList<>();
        List<Syllabus> planes=null;
        try{
            LOG.debug("Se realiza la consulta del plan de estudios del estudiante");
            planes=obtenerSyllabusEstudiante(codigoEstudiante);
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar las asignaturas del estudiante "+codigoEstudiante,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las asignaturas del estudiante "+codigoEstudiante);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar las asignaturas del estudiante "+codigoEstudiante,e);
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
        LOG.info("Consulta el impacto de la(s) asignatura(s) "+nemonicoAsignatura+" del estudiante "+codigoEstudiante);
        String impacto=null;
        Gson g = new Gson();
        Syllabus s = g.fromJson(consultarPlanDeEstudioByIdEstudiante(codigoEstudiante), Syllabus.class);
        try{
            LOG.debug("Se realiza la consulta del impacto a ImpactAnalizer (Algoritmo)");
            impacto=algo.getImpact(nemonicoAsignatura, gRec.verify(s), s,consultarNumeroMaximoDeCreditos())[0];
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar el impacto de la asignatura "+nemonicoAsignatura+" del estudiante "+codigoEstudiante,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el impacto de la asignatura "+nemonicoAsignatura+" del estudiante "+codigoEstudiante);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar el impacto de una asignatura",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el impacto de una asignatura", e);
        }
        return impacto;
    }
    
    @Transactional
    @Override
    public ArrayList<ArrayList<String>> consultarProyeccionByEstudianteAsignatura(int codigoEstudiante, String[] nemonicoAsignatura) throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta la proyeccion para la(s) asignatura(s) "+nemonicoAsignatura+" del estudiante "+codigoEstudiante);
        ArrayList<ArrayList<String>> proyeccion;
        Gson g = new Gson();
        Syllabus s = g.fromJson(consultarPlanDeEstudioByIdEstudiante(codigoEstudiante), Syllabus.class);
        try{
            LOG.debug("Se realiza la consulta de la proyeccion a ImpactAnalizar (Algoritmo)");
            String proyeccionString=algo.getImpact(nemonicoAsignatura, gRec.verify(s), s,consultarNumeroMaximoDeCreditos())[1];
            proyeccion=algo.getProyeccion();   
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar la proyeccion de cancelar la asignatura "+nemonicoAsignatura,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar la proyeccion de cancelar la asignatura "+nemonicoAsignatura);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar la proyeccion de cancelar una asignatura", e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar la proyeccion de cancelar una asignatura", e);
        }
        return proyeccion;
    }

    @Transactional
    @Override
    public List<Syllabus> obtenerSyllabusEstudiante(int codigo) throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta el syllabus del estudiante con codigo "+codigo);
        List<Syllabus> planesDeEstudio= new ArrayList<>();
        String programa="";
        String syllabusEstudiante="";
        try{
            LOG.debug("Se realiza la consulta del plan de estudio al metodo consultarPlanDeEstudioByIdEstudiante");
            syllabusEstudiante=consultarPlanDeEstudioByIdEstudiante(codigo);
            LOG.debug("Se realiza la consulta del plan de estudio del programa al que pertenece el estudiante en UniversidadDAO");
            programa=universidad.loadSyllabusByStudent(codigo).getContenido();
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar el plan de estudios del estudiante "+codigo+" y del programa al que pertenece",e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el plan de estudios del estudiante "+codigo+" y del programa al que pertenece");
        }catch (Exception e){
            LOG.error("Error inesperado al consultar el plan de estudios de un estudiante y del programa", e);
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
    public void actualizarJustificacionById(int id, String justificacion, String materia) throws ExcepcionServiciosCancelaciones {
        LOG.info("Agrega una solicitud de cancelacion para la asignatura "+materia+" del estudiante "+id);
        Syllabus planE=obtenerSyllabusEstudiante(id).get(0);
        Estudiante student;
        int numero;
        boolean acuse=false;
        try{
            LOG.debug("Se consultan todos los datos del estudiante a partir de su identificador");
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
            LOG.debug("Se agrega la nueva solicitud en SolicitudDAO");
            solicitud.updateJustification(id, materia, justificacion, numero, acuse);
            actualizarEstadoAsignaturasPorEstudiante(id, materia, 'P');
        }catch (PersistenceException e){
            LOG.error("No se pudo actualizar la justificacion para cancelar la asignatura "+materia,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo actualizar la justificacion para cancelar la asignatura "+materia);
        }catch (Exception e){
            LOG.error("Error inesperado al actualizar la justificacion de una solicitud", e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al actualizar la justificacion de una solicitud", e);
        }

        if (student.getNumeroMatriculas()<3){
            acuse=true;
        }        
    }   
    
    @Transactional
    @Override
    public void actualizarNumeroMaximoDeCreditos(int creditos) throws ExcepcionServiciosCancelaciones{
        LOG.info("Actualiza el numero de creditos que se pueden ver por semestre");
        try{
            LOG.debug("Se realiza la actualizacion del numero de creditos en UniversidadDAO");
            universidad.updateCredits(creditos);
        }catch (PersistenceException e){
            LOG.error("No se actualizaron el numero maximo de creditos por semestre",e);
            throw new ExcepcionServiciosCancelaciones("No se actualizaron el numero maximo de creditos por semestre");
        }catch (Exception e){
            LOG.error("Error inesperado al actualizar el numero maximo de creditos", e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al actualizar el numero maximo de creditos", e);
        }
    }
    
    @Transactional
    @Override
    public int consultarNumeroMaximoDeCreditos() throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta el numero de creditos que se pueden ver por semestre");
        int credits=0;
        try{
            LOG.debug("Se realiza la consulta de los creditos maximos en UniversidadDAO");
            credits=universidad.consultUniversity().getTotalCredits();
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar el numero maximo de creditos",e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el numero maximo de creditos");
        }catch (Exception e){
            LOG.error("Error inesperado al consultar el numero maximo de creditos", e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el numero maximo de creditos", e);
        }
        return credits;
    }
    
    @Transactional
    @Override
    public Estudiante consultarEstudianteByCorreo(String correo) throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta un estudiante a partir de su correo electronico");
        Estudiante student=null;
        try{
            LOG.debug("Se realiza la consulta del estudiante en EstudianteDAO");
            student=estudiante.consultStudentByEmail(correo);
            if (student==null){
                throw new ExcepcionServiciosCancelaciones("No existe un estudiante con el correo "+correo);
            }
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar el estudiante por correo "+correo,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el estudiante por correo "+correo);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar un estudiante por correo", e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar un estudiante por correo", e);
        }
        return student;
    }
    
    @Transactional
    @Override
    public List<CourseStudent> consultarAsignaturasSinSolicitudByIdEStudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta las asignaturas que no tiene solicitud de cancelacion del estudiante "+codigoEstudiante);
        List<CourseStudent> asignaturas=new ArrayList<>();
        List<CourseStudent> asig;
        try{
            LOG.debug("Se realiza la consulta de las asignaturas que ya tienen solicitud");
            asig=consultarAsignaturasConSolicitudPorEstudiante(codigoEstudiante);
            List<String> tieneSolicitud=new ArrayList<>();
            for (CourseStudent co:asig){
                tieneSolicitud.add(co.getNemonico());
            }
            if (asig.size()==0){
                LOG.debug("Se realiza la consulta de todas las asignaturas que esta viendo el estudiante");
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
            LOG.error("No se pudo consultar las asignaturas sin solicitud de cancelacion del estudiante "+codigoEstudiante,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las asignaturas sin solicitud de cancelacion del estudiante "+codigoEstudiante);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar las asignaturas sin solicitud de un estudiante", e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las asignaturas sin solicitud de un estudiante", e);
        }
        return asignaturas;
    }
    
    @Override
    public List<CourseStudent> consultarAsignaturasConSolicitudPorEstudiante(int codigo) throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta las asignaturas que ya tienen solicitud del estudiante "+codigo);
        List<CourseStudent> materias=new ArrayList<>();
        try{
            LOG.debug("Se realiza la consulta de las asignaturas con solicitud en SolicitudDAO");
            materias=solicitud.loadCoursesById(codigo);
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar las asignaturas con solicitud de cancelacion del estudiante "+codigo,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las asignaturas con solicitud de cancelacion del estudiante "+codigo);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar las asignaturas con solicitud de un estudiante", e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las asignaturas con solicitud de un estudiante", e);
        }
        return materias;
    }
    
    @Override
    public List<ProgramaAcademico> consultarTodosProgramasAcademicos() throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta todos los programas academicos");
        List<ProgramaAcademico> programas=null;
        try{
            LOG.debug("Se realiza la consulta de todos los programas academicos en UniversidadDAO");
            programas=universidad.consultarProgramasAcademicos();
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar todos los programas academicos",e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar todos los programas academicos");
        }catch(Exception e){
            LOG.error("Error inesperarado al consultar todos los programas academicos",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperarado al consultar todos los programas academicos",e);
        }
        return programas;
    }

    @Override
    public Syllabus consultarPlanDeEstudios(String programa, int planDeEstudios) throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta el plan de estudio numero "+planDeEstudios+" del programa "+programa);
        PlanDeEstudios plan=null;
        try{
            LOG.debug("Se realiza la consulta del plan de estudio del programa en UniversidadDAO");
            plan=universidad.consultarPlanDeEstudios(programa, planDeEstudios);
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar el plan de estudios "+planDeEstudios+" del programa "+programa,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el plan de estudios "+planDeEstudios+" del programa "+programa);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar un plan de estudios de un programa",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar un plan de estudios de un programa");
        }
        Gson gson= new Gson();
        Syllabus syllabus= gson.fromJson(plan.getContenido(), Syllabus.class);
        return syllabus;
    }

    @Override
    public ArrayList<Integer> consultarPlanesDeEstudiosPorPrograma(String programa) throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta todos los planes de estudios del programa "+programa);
        ArrayList<Integer> planes= new ArrayList<>();
        ProgramaAcademico programaAc = null;
        try{
            LOG.debug("Se realiza la consulta de los planes de estudios del programa en UniversidadDAO");
            programaAc=universidad.consultarProgramaAcademicoPorNombre(programa);
        }catch (PersistenceException e){
            LOG.error("No se pudieron consultar los planes de estudios del programa "+programa,e);
            throw new ExcepcionServiciosCancelaciones("No se pudieron consultar los planes de estudios del programa "+programa);
        }catch(Exception e){
            LOG.error("Error inesperado al consultar todos los planes de estudios de un programa",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar todos los planes de estudios de un programa");
        }
        for (PlanDeEstudios p:programaAc.getPlanDeEstudio()){
            planes.add(p.getId());
        }
        return planes;
    }
    
    @Override
    public List<Solicitud> consultarSolicitudesDeCancelaciones(String consejero) throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta las solicitudes de cancelaciones a partir del correo del consejero "+consejero);
        List<Solicitud> solicitudes=new ArrayList<>();
        try{
            LOG.debug("Se realiza la consulta de las solicitudes de cancelacion a partir del consejero en EstudianteDAO");
            solicitudes=estudiante.consultRequest(consejero);
            if (solicitudes==null){
                throw new ExcepcionServiciosCancelaciones("El consejero no tiene solicitudes");
            }
        }catch (PersistenceException e){
            LOG.error("No se pudieron consultar las solicitudes del consejero "+consejero,e);
            throw new ExcepcionServiciosCancelaciones("No se pudieron consultar las solicitudes del consejero "+consejero);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar las solicitudes de un consejero",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las solicitudes de un consejero");
        }
        return solicitudes ;
    }
    
    @Override
    public Estudiante consultarEstudiantePorSolicitud(int numero) throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta un estudiante a partir del numero de solicitud "+numero);
        Estudiante student=null;
        try{
            LOG.debug("Se realiza la consulta del estudiante por numero de solicitud en EstudianteDAO");
            student=estudiante.consultStudentByRequest(numero);
            if (student==null){
                throw new ExcepcionServiciosCancelaciones("La solicitud "+numero+" no existe");
            }
            student=estudiante.loadEstudianteById(student.getCodigo());
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar el estudiante que realizo la solicitud "+numero,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el estudiante que realizo la solicitud "+numero);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar un estudiante por numero de solicitud",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar un estudiante por numero de solicitud");
        }
        
        return student;
    }

    @Override
    public void actualizarComentariosSolicitud(int numero,String comentarios) throws ExcepcionServiciosCancelaciones {
        LOG.info("Actualiza los comentarios del consejero en la solicitud numero "+numero);
        try{
            if (comentarios.isEmpty()){
                throw new ExcepcionServiciosCancelaciones("Los comentarios no deben ser vacios");
            }   
            LOG.debug("Se realiza la actualizacion de los comentarios en SolicitudDAO");
            solicitud.updateComentariosSolicitud(numero,comentarios);
        }catch(PersistenceException e){
            LOG.error("No se pudo actualizar la solicitud "+numero,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo actualizar la solicitud "+numero);
        }catch(Exception e){
            LOG.error("Error inesperado al alctualizar la solicitud numero "+numero,e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al alctualizar la solicitud numero "+numero);
        }
    }
        
    
    @Override
    public void actualizarEstadoSolicitud(int numero, String estado) throws ExcepcionServiciosCancelaciones{
        LOG.info("Actualiza el estado de la solicitud con numero "+numero);
        try{
            LOG.debug("Se realiza la actualizacion del estado de la solicitud en SolicitudDAO");
            solicitud.updateStateRequest(numero,estado);
        }catch (PersistenceException e){
            LOG.error("No se pudo actualizar el estado de la solicitud "+numero,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo actualizar el estado de la solicitud "+numero);
        }catch (Exception e){
            LOG.error("Error inesperado al actualizar el estado de la solicitud",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al actualizar el estado de la solicitud");
        }
    }

    @Override
    public void enviarSolicitudes(int id, List<String> justificacion, List<CourseStudent> materias) throws ExcepcionServiciosCancelaciones {
        LOG.info("Agrega una solicitud por cada materia en la lista "+materias);
        for (int i=0;i<materias.size();i++){
            actualizarJustificacionById(id, justificacion.get(i), materias.get(i).getNemonico());
        }
    }

    @Override
    public Acudiente consultarAcudientePorStudiante(int codigoEstudiante) throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta el acudiente del estudiante con codigo "+codigoEstudiante);
        Acudiente acu;
        try{
            LOG.debug("Se realiza la consulta del acudiente en AcudienteDAO");
            acu=acudiente.consultarAcudiantePorEstudiante(codigoEstudiante);
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar el acudiente del estudiante con codigo "+codigoEstudiante,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el acudiente del estudiante con codigo "+codigoEstudiante);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar el acudiente",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el acudiente");
        }
        return acu;
    }

    @Override
    public void actualizarAcuseSolicitud(int numero,boolean acuse) throws ExcepcionServiciosCancelaciones {
        LOG.info("Actualiza el acuse de recibo de la solicitud "+numero);
        try{
            LOG.debug("Se realiza la actualizacion del acuse de recibo en SolicitudDAO");
            solicitud.actualizarAcuseSolicitud(numero,acuse);
        }catch (PersistenceException e){
            LOG.error("No se pudo actualizar la solicitud "+numero,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo actualizar la solicitud "+numero);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar el acudiente",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el acudiente");
        }
    }
    
    @Override
    public List<Solicitud> consultarSolicitudesPorEstudiante(int codigo) throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta las solicitudes de cancelacion del estudiante con codigo "+codigo);
        List<Solicitud> solicitudes=new ArrayList<>();
        try{
            LOG.debug("Se realiza la consulta de las solicitudes en EstudianteDAO");
            solicitudes=estudiante.consultRequestByStudent(codigo);
            if (solicitudes==null){
                throw new ExcepcionServiciosCancelaciones("El estudiante con codigo "+codigo+" no tiene solicitudes de cancelaciones");
            }
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar las solicitudes de cancelaciones del estudiante con codigo "+codigo,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las solicitudes de cancelaciones del estudiante con codigo "+codigo);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar las solicitudes de cancelaciones del estudiante",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las solicitudes de cancelaciones del estudiante");
        }
        return solicitudes;
    }
    
    @Override
    public Estudiante consultarEstudianteById(int codigo) throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta toda la informacion del estudiante con codigo "+codigo);
        Estudiante estu=null;
        try{
            LOG.debug("Se realiza la consulta del estudiante en EstudianteDAO");
            estu=estudiante.loadEstudianteById(codigo);
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar lel estudiante con codigo"+codigo,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar lel estudiante con codigo"+codigo);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar el estudiante",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el estudiante");
        }
        return estu;
    }
    
    @Override
    public Solicitud consultarSolicitudPorEstudianteYNemonico(int codigo,String nemonico) throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta la solicitud de la asignatura "+nemonico+" del estudiante "+codigo);
        Solicitud sol=null;
        try{
            LOG.debug("Se realiza la consulta de la solicitud en SolicitudDAO");
            sol=solicitud.consultRequestByStudentAndId(codigo, nemonico);
            if (sol==null){
                throw new ExcepcionServiciosCancelaciones("No existe una solicitud para la materia "+nemonico+" del estudiante con codigo "+codigo);
            }
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar la solicitud del estudiante "+codigo+" para la materia "+nemonico,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar la solicitud del estudiante "+codigo+" para la materia "+nemonico);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar la solicitud de la materia "+nemonico,e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar la solicitud de la materia "+nemonico);
        }
        return sol;
    }

    @Override
    public List<Solicitud> consultarSolicitudes() throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta todas las solicitudes de cancelacion");
        List<Solicitud> solicitudes=new ArrayList<>();
        try{
            LOG.debug("Se realiza la consulta de todas las solicitudes en SolicitudDAO");
            solicitudes=solicitud.consultarSolicitudes();
            if (solicitudes.isEmpty()){
                throw new ExcepcionServiciosCancelaciones("No existe ninguna solicitud");
            }
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar las solicitudes de cancelaciones",e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las solicitudes de cancelaciones");
        }catch (Exception e){
            LOG.error("Error inesperado al consultar las solicitudes de cancelaciones",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las solicitudes de cancelaciones");
        }
        return solicitudes;
    }

    @Override
    public ConsejeroAcademico consultarConsejeroPorEstudiante(int codigo) throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta el consejero del estudiante con codigo "+codigo);
        ConsejeroAcademico conse=null;
        try{
            LOG.debug("Se realiza la consulta del consejero en EstudianteDAO");
            conse=estudiante.consultarConsejeroPorEstudiante(codigo);
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar el consejero del estudiante con codigo"+codigo,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar el consejero del estudiante con codigo"+codigo);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar el consejero",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar el consejero");
        }
        return conse;
    }

    @Override
    public Solicitud consultarSolicitudPorNumero(int numero) throws ExcepcionServiciosCancelaciones {
        LOG.info("Consulta la solicitud a partir de su numero "+numero);
        Solicitud sol=null;
        try{
            LOG.debug("Se realiza la consulta de la solicitud en SolicitudDAO");
            sol=solicitud.consultarSolicitud(numero);
            if (sol==null){
                throw new ExcepcionServiciosCancelaciones("No existe la solicitud con numero "+numero);
            }
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar la solicitud con numero: "+numero,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar la solicitud con numero: "+numero);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar la solicitud",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar la solicitud");
        }
        return sol;
    }
    
    @Override
    public List<CourseStudent> consultarAsignaturasCanceladasPorEstudiante(int codigo) throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta las asignaturas canceladas del estudiante con codigo "+codigo);
        List<CourseStudent> materias=new ArrayList<>();
        try{
            LOG.debug("Se realiza la consulta de las asignaturas canceladas en SolicitudDAO");
            materias=solicitud.consultCanceledSubjectsByStudent(codigo);
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar las asignaturas canceladas del estudiante con codigo "+codigo,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las asignaturas canceladas del estudiante con codigo "+codigo);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar las asignaturas canceladas del estudiante",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las asignaturas canceladas del estudiante");
        }
        return materias;
    }
    
    @Override
    public void actualizarEstadoAsignaturasPorEstudiante(int codigo,String nemonico,char estado) throws ExcepcionServiciosCancelaciones{
        LOG.info("Actualiza el estado de la asignatura "+nemonico+" del estudiante "+codigo);
        try{
            Gson g = new Gson();
            List<CourseStudent> asignaturas = new ArrayList<>();
            Type materias = new TypeToken<List<CourseStudent>>(){}.getType(); 
            asignaturas=g.fromJson(consultarEstudianteById(codigo).getAsignaturas(), materias);
            for (CourseStudent c:asignaturas){
                if (c.getNemonico().equals(nemonico)){
                    c.setEstado(estado);
                }
            }
            Gson p = new GsonBuilder().setPrettyPrinting().create();
            String materiasActualizadas= p.toJson(asignaturas);
            LOG.debug("Se realiza la actualizacion del estado de la asignatura en EstudianteDAO");
            estudiante.updateCourseStudent(codigo, materiasActualizadas); 
        }catch (PersistenceException e){
            LOG.error("No se pudo actualizar el estado de la materia "+nemonico,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo actualizar el estado de la materia "+nemonico);
        }catch (Exception e){
            LOG.error("Error inesperado al actualizar el estado de una materia",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al actualizar el estado de una materia");
        }
    }
    
    @Override
    public List<CourseStudent> consultarCorequisitosPorMateria(int codigo,String nemonico) throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta las asignaturas que tienen como corequisito "+nemonico);
        List<CourseStudent> materiasCorequisitos=new ArrayList<>();
        try{
            Gson g = new Gson();
            List<CourseStudent> asignaturas = new ArrayList<>();
            Type materias = new TypeToken<List<CourseStudent>>(){}.getType(); 
            asignaturas=g.fromJson(consultarEstudianteById(codigo).getAsignaturas(), materias);
            for (CourseStudent c:asignaturas){
                for (String co: c.getCoReq()){
                    if (co.equals(nemonico)){
                        materiasCorequisitos.add(c);
                    }

                }
            }
        }catch (Exception e){
            LOG.error("Error inesperado al consultar las materias que tienen como corequisito a "+nemonico,e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las materias que tienen como corequisito a "+nemonico);
        }
        return materiasCorequisitos;
    }
    
    
    
    @Override
    public List<Solicitud> consultarSolicitudesPorCoordinador(int codigo) throws ExcepcionServiciosCancelaciones{
        LOG.info("Consulta las solicitudes del coordinador con codigo "+codigo);
        List<Solicitud> solicitudes=new ArrayList<>();
        try{
            LOG.debug("Se realiza la consulta de las solicitudes del coordinador en SolicitudDAO");
            solicitudes=solicitud.consultRequestsByCoordinator(codigo);
            if (solicitudes.isEmpty()){
                throw new ExcepcionServiciosCancelaciones("El coordinador no tiene ninguna solicitud para revisar");
            }
        }catch (PersistenceException e){
            LOG.error("No se pudo consultar las solicitudes para el coordinador con codigo "+codigo,e);
            throw new ExcepcionServiciosCancelaciones("No se pudo consultar las solicitudes para el coordinador con codigo "+codigo);
        }catch (Exception e){
            LOG.error("Error inesperado al consultar las solicitudes de un coordinador",e);
            throw new ExcepcionServiciosCancelaciones("Error inesperado al consultar las solicitudes de un coordinador");
        }
        return solicitudes;
    }

    @Override
    public List<CourseStudent> consultarCorequisitosPorMaterias(int codigo, List<CourseStudent> materias) throws ExcepcionServiciosCancelaciones {
        List<CourseStudent> materiasCorre=new ArrayList<>();
        List<String> nemonicoCorre=new ArrayList<>();
        List<String> nemonicos=new ArrayList<>();
        List<String> nemonicosConSolicitud=new ArrayList<>();
        for (CourseStudent co:consultarAsignaturasConSolicitudPorEstudiante(codigo)){
            nemonicosConSolicitud.add(co.getNemonico());
        }
        for (CourseStudent c:materias){
            nemonicos.add(c.getNemonico());
            List<CourseStudent> tieneCorrequisitos=new ArrayList<>();
            tieneCorrequisitos=consultarCorequisitosPorMateria(codigo, c.getNemonico());
            for (CourseStudent t: tieneCorrequisitos){
                materiasCorre.add(t);
                nemonicoCorre.add(t.getNemonico());
            }
        }
        for (int i=0;i<materiasCorre.size();i++){
            if ((nemonicos.contains(materiasCorre.get(i).getNemonico()))){
                materiasCorre.remove(materiasCorre.get(i));
            }
            if (nemonicosConSolicitud.size()>0 && nemonicosConSolicitud.contains(materiasCorre.get(i).getNemonico())){
                materiasCorre.remove(materiasCorre.get(i));
            }
        }
        return materiasCorre;
    }
}
