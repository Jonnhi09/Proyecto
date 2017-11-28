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
import com.projectkepler.services.entities.Syllabus;
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

/**
 *
 * @author dxmortem
 */
@ManagedBean(name = "ajusteparametros")
@SessionScoped
public class AjusteParametros implements Serializable {
    
    
    @ManagedProperty(value="#{loginBean}")
    private ShiroLoginBean shiroLoginBean; 
    private String usuario;
    private int creditosSemestre;
    private int creditosSemestreActuales;
    private String programaSeleccionado;
    private int planDeEstudiosSeleccionado;
    private List<?> planesDeEstudios;
    private List<?> programas;
    private boolean renderTable;
    private boolean planDisabled;
    private Syllabus syllabus;
    private ServiciosCancelaciones servicios = ServiciosCancelacionesFactory.getInstance().getServiciosCancelaciones();
    
    

    /**
     * Creates a new instance of AjusteParametros
     */
    
    public AjusteParametros() {
        this.planDeEstudiosSeleccionado = -1;
        this.planDisabled = true;
        this.renderTable = false;
        try {
            this.programas = servicios.consultarTodosProgramasAcademicos();
        } catch (ExcepcionServiciosCancelaciones ex) {
            Logger.getLogger(AjusteParametros.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            creditosSemestreActuales = servicios.consultarNumeroMaximoDeCreditos();
        } catch (ExcepcionServiciosCancelaciones ex) {
            Logger.getLogger(AjusteParametros.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage("creditosMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getLocalizedMessage(), null));
        }
    }
    
    @PostConstruct
    private void init(){
        if(getShiroLoginBean().getSubject().hasRole("superadmin")){
            setUsuario(getShiroLoginBean().getUsername());
            programaSeleccionado= "Programa";
            
        }else{
            setUsuario(getShiroLoginBean().getUsername().replace("admin", "").trim());
            programaSeleccionado="Ingenieria de "+ usuario; //TODO: estandarizar
            seleccionPrograma();
        }
        
    }
    
    public void seleccionPrograma(){
       if(!"Programa".equals(programaSeleccionado)){
            try {
                planesDeEstudios=servicios.consultarPlanesDeEstudiosPorPrograma(programaSeleccionado);
                
                setPlanDisabled(false);
            } catch (ExcepcionServiciosCancelaciones ex) {
                Logger.getLogger(AjusteParametros.class.getName()).log(Level.SEVERE, null, ex);
                FacesContext.getCurrentInstance().addMessage("programaMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getLocalizedMessage(),null));
            }
        }else{
            setPlanDisabled(true);
            setRenderTable(false);
            
        }
        
    }
    
    public void seleccionPlanDeEstudios(){
        if(planDeEstudiosSeleccionado!=-1){
            try {
                this.syllabus = servicios.consultarPlanDeEstudios(programaSeleccionado, planDeEstudiosSeleccionado);
                setRenderTable(true);
            } catch (ExcepcionServiciosCancelaciones ex) {
                Logger.getLogger(AjusteParametros.class.getName()).log(Level.SEVERE, null, ex);
                FacesContext.getCurrentInstance().addMessage("programaMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getLocalizedMessage(),null));
            }
        }else{
            setRenderTable(false);
        }
        
        
    }
    
    public void guardarCreditos(){
        try {
            servicios.actualizarNumeroMaximoDeCreditos(creditosSemestre);
            FacesContext.getCurrentInstance().addMessage("creditosMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se han modificado los créditos máximos", null));
        } catch (ExcepcionServiciosCancelaciones ex) {
            Logger.getLogger(AjusteParametros.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage("creditosMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getLocalizedMessage(), null));
        }
    }
    
    public int getCreditosSemestre() {
        return creditosSemestre;
    }

    public void setCreditosSemestre(int CreditosSemestre) {
        this.creditosSemestre = CreditosSemestre;
    }

    public String getProgramaSeleccionado() {
        return programaSeleccionado;
    }

    public void setProgramaSeleccionado(String programaSeleccionado) {
        this.programaSeleccionado = programaSeleccionado;
    }

    public List<?> getPlanesDeEstudios() {
        return planesDeEstudios;
    }

    public void setPlanesDeEstudios(List<?> planesDeEstudios) {
        this.planesDeEstudios = planesDeEstudios;
    }

    public List<?> getProgramas() {
        return programas;
    }

    public void setProgramas(List<?> programas) {
        this.programas = programas;
    }

    public boolean isRenderTable() {
        return renderTable;
    }

    public void setRenderTable(boolean renderTable) {
        this.renderTable = renderTable;
    }

    public boolean isPlanDisabled() {
        return planDisabled;
    }

    public void setPlanDisabled(boolean planDisabled) {
        this.planDisabled = planDisabled;
    }

    public int getCreditosSemestreActuales() {
        return creditosSemestreActuales;
    }

    public void setCreditosSemestreActuales(int CreditosSemestreActuales) {
        this.creditosSemestreActuales = CreditosSemestreActuales;
    }

    public ServiciosCancelaciones getServicios() {
        return servicios;
    }

    public void setServicios(ServiciosCancelaciones servicios) {
        this.servicios = servicios;
    }

    public Syllabus getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(Syllabus syllabus) {
        this.syllabus = syllabus;
    }

    public int getPlanDeEstudiosSeleccionado() {
        return planDeEstudiosSeleccionado;
    }

    public void setPlanDeEstudiosSeleccionado(int planDeEstudiosSeleccionado) {
        this.planDeEstudiosSeleccionado = planDeEstudiosSeleccionado;
    }
    
    public ShiroLoginBean getShiroLoginBean() {
        return shiroLoginBean;
    }

    public void setShiroLoginBean(ShiroLoginBean shiroLoginBean) {
        this.shiroLoginBean = shiroLoginBean;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
}
