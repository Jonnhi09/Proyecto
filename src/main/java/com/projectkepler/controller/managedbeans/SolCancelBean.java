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
import com.projectkepler.services.email.Email;
import com.projectkepler.services.email.EmailConfiguration;
import com.projectkepler.services.email.EmailSender;
import com.projectkepler.services.email.SimpleEmail;
import com.projectkepler.services.email.SimpleEmailSender;
import com.projectkepler.services.entities.ConsejeroAcademico;
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
import javax.mail.MessagingException;
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
    private ConsejeroAcademico consejero;
    
    @PostConstruct
    private void init(){
        setUsuario(getShiroLoginBean().getUsername());
        try {
            estudiante = servicios.consultarEstudianteByCorreo(usuario+"@mail.escuelaing.edu.co");
            materias = servicios.consultarAsignaturasSinSolicitudByIdEStudiante(estudiante.getCodigo());
            consejero = servicios.consultarConsejeroPorEstudiante(estudiante.getCodigo());
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
        try {
            if(materiasSelect.size()<1){
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una materia para realizar el análisis", null)); 
                makePanelInvisible();
            }else{
                String[] nemonicos = new String[materiasSelect.size()];
                for(int i=0; i < materiasSelect.size(); i++){
                    nemonicos[i]=materiasSelect.get(i).getNemonico();
                }
                impacto = servicios.consultarImpactoByEstudianteAsignatura(estudiante.getCodigo(), nemonicos);
                proyeccion = servicios.consultarProyeccionByEstudianteAsignatura(estudiante.getCodigo(), nemonicos);
                makePanelVisible();
            }
        } catch (ExcepcionServiciosCancelaciones ex) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, ex.getLocalizedMessage(), null));
        }
    }
    
    public void enviarSolicitud(){
        try{
            if(justificacion == null || justificacion.isEmpty()){
                FacesContext.getCurrentInstance().addMessage("dialogMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese una justificación", null));
            }else{
                servicios.enviarSolicitudes(estudiante.getCodigo(), justificacion,materiasSelect);
                materias = servicios.consultarAsignaturasSinSolicitudByIdEStudiante(estudiante.getCodigo());
                enviarCorreo();
                makePanelInvisible();
                RequestContext.getCurrentInstance().execute("PF('dialogJustificacion').hide();");
                RequestContext.getCurrentInstance().update("formSol");
                RequestContext.getCurrentInstance().update("formSol:matcheck");
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Su solicitud ha sido enviada", null));
                justificacion = null;
            }
        }catch (ExcepcionServiciosCancelaciones ex) {
            FacesContext.getCurrentInstance().addMessage("dialogMessages", new FacesMessage(FacesMessage.SEVERITY_FATAL, ex.getLocalizedMessage(), null));
        } catch (MessagingException ex) {
            Logger.getLogger(SolCancelBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage("dialogMessages", new FacesMessage(FacesMessage.SEVERITY_FATAL, ex.getLocalizedMessage(), null));
        }
    }
    
    public void enviarCorreo() throws MessagingException{
  
        Email email = new SimpleEmail("noreplay@escuelaing.edu.co",consejero.getCorreo(),"Cancelación " + estudiante.getNombre() + ": " + sMaterias(),"Buen día " + consejero.getNombre() + ",\n\nEste es un mensaje automático para informar sobre una nueva cancelación adicionada en el sistema" + "\n\nEstudiante: " + estudiante.getNombre() + "\nMateria/s: " + sMaterias() + "\nJustificación: " + justificacion + "\nImpacto: " + impacto + "\nProyección: " + proyeccion +  "\n\nPor favor no responda este mensaje.");
        EmailSender sender = new SimpleEmailSender(new EmailConfiguration());
        sender.send(email);
    }
    
    public String sMaterias(){
        String sMat = new String();
        for(int i=0; i<materiasSelect.size();i++){
            sMat += (i+1<materiasSelect.size())? materiasSelect.get(i).getNemonico() + ", " : materiasSelect.get(i).getNemonico();
        }
        return sMat;
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

    public ConsejeroAcademico getConsejero() {
        return consejero;
    }

    public void setConsejero(ConsejeroAcademico consejero) {
        this.consejero = consejero;
    }
    
}
