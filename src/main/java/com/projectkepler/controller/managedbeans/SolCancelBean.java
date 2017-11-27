/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.controller.managedbeans;

import com.projectkepler.controller.managedbeans.security.ShiroLoginBean;
import com.projectkepler.services.ExcepcionServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelacionesFactory;
import com.projectkepler.services.entities.CourseStudent;
import com.projectkepler.services.entities.Estudiante;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author dxmortem
 */
@ManagedBean(name = "solCancelBean")
@SessionScoped



public class SolCancelBean implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value="#{loginBean}")
    private ShiroLoginBean shiroLoginBean;

    private ServiciosCancelaciones servicios = ServiciosCancelacionesFactory.getInstance().getServiciosCancelaciones();
    private List<CourseStudent> materias;
    private Estudiante estudiante;
    private List<CourseStudent> materiasSelect;
    private boolean panelVisibility;
    private String justificacion;
    private String impacto;
    private String proyeccion;
    private String usuario;
    
    @PostConstruct
    private void init(){
        setUsuario(getShiroLoginBean().getUsername());
        try {
            estudiante = servicios.consultarEstudianteByCorreo(usuario+"@mail.escuelaing.edu.co");
            materias = servicios.consultarAsignaturasSinSolicitudByIdEStudiante(estudiante.getCodigo());
        } catch (ExcepcionServiciosCancelaciones ex) {
            Logger.getLogger(SolCancelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a new instance of SolCancelBean
     */
    public SolCancelBean() {
        this.justificacion = null;
        this.panelVisibility = false;
    }
    
    public void makePanelVisible(){
        setPanelVisibility(true);
    }
    
    public void makePanelInvisible(){
        setPanelVisibility(false);
    }
    
    public void analisis(){
        //try {
            if(materiasSelect.size()<1){
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una materia para realizar el análisis", null)); 
                makePanelInvisible();
            }else{
                //impacto = servicios.consultarImpactoByEstudianteAsignatura(estudiante.getCodigo(), materiasSelect);
                //proyeccion = servicios.consultarProyeccionByEstudianteAsignatura(estudiante.getCodigo(), materiasSelect);
                makePanelVisible();
            }
//        } catch (ExcepcionServiciosCancelaciones ex) {
//            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, ex.getLocalizedMessage(), null));
//        }
    }
    
    public void enviarSolicitud(){
        try{
            if(justificacion == null || justificacion.isEmpty()){
                FacesContext.getCurrentInstance().addMessage("dialogMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese una justificación", null));
            }else{
                makePanelInvisible();
                RequestContext.getCurrentInstance().execute("PF('dialogJustificacion').hide();");
                servicios.enviarSolicitudes(estudiante.getCodigo(), justificacion,materiasSelect);
                materias = servicios.consultarAsignaturasSinSolicitudByIdEStudiante(estudiante.getCodigo());
                RequestContext.getCurrentInstance().update("formSol");
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su solicitud ha sido enviada", null));
                justificacion = null;
            }
        }catch (ExcepcionServiciosCancelaciones ex) {
            Logger.getLogger(SolCancelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
    
    public List<CourseStudent> getMateriasSelect() {
        return materiasSelect;
    }

    public void setMateriasSelect(List<CourseStudent> materiasSelect) {
        this.materiasSelect = materiasSelect;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public boolean isPanelVisibility() {
        return panelVisibility;
    }

    public void setPanelVisibility(boolean panelVisibility) {
        this.panelVisibility = panelVisibility;
    }

    public List<CourseStudent> getMaterias() {
        return materias;
    }

    public void setMaterias(List<CourseStudent> materias) {
        this.materias = materias;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public String getProyeccion() {
        return proyeccion;
    }

    public void setProyeccion(String proyeccion) {
        this.proyeccion = proyeccion;
    }

    public ServiciosCancelaciones getServicios() {
        return servicios;
    }

    public void setServicios(ServiciosCancelaciones servicios) {
        this.servicios = servicios;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public ShiroLoginBean getShiroLoginBean() {
        return shiroLoginBean;
    }

    public void setShiroLoginBean(ShiroLoginBean shiroLoginBean) {
        this.shiroLoginBean = shiroLoginBean;
    }
    
    
}
