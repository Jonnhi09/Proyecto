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
    private String materiaSeleccionada;
    private String materiaSeleccionadaNemo;
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
        /**
        try {
            if("Asignatura".equals(materiaSeleccionada)){
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una materia para realizar el análisis", null)); 
                makePanelInvisible();
            }else{
                impacto = servicios.consultarImpactoByEstudianteAsignatura(estudiante.getCodigo(), materiaSeleccionada);
                proyeccion = servicios.consultarProyeccionByEstudianteAsignatura(estudiante.getCodigo(), materiaSeleccionada);
                makePanelVisible();
            }
        } catch (ExcepcionServiciosCancelaciones ex) {
            Logger.getLogger(SolCancelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        * **/
    }
    
    public void enviarSolicitud(){
        try{
            if(justificacion == null || justificacion.isEmpty()){
                FacesContext.getCurrentInstance().addMessage("dialogMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese una justificación", null));
            }else{
                makePanelInvisible();
                RequestContext.getCurrentInstance().execute("PF('dialogJustificacion').hide();");
                //servicios.actualizarJustificacionById(estudiante.getCodigo(), justificacion, materiaSeleccionada);
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
    
    public String getMateriaSeleccionada() {
        return materiaSeleccionada;
    }

    public void setMateriaSeleccionada(String materiaSeleccionada) {
        this.materiaSeleccionada = materiaSeleccionada;
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

    public String getMateriaSeleccionadaNemo() {
        return materiaSeleccionadaNemo;
    }

    public void setMateriaSeleccionadaNemo(String materiaSeleccionadaNemo) {
        this.materiaSeleccionadaNemo = materiaSeleccionadaNemo;
    }
    
    
    
}
