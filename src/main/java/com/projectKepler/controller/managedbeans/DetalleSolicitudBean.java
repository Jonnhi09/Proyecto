/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.controller.managedbeans;


import com.projectKepler.controller.managedbeans.security.ShiroLoginBean;
import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelacionesFactory;
import com.projectKepler.services.entities.Estudiante;
import com.projectKepler.services.entities.Solicitud;
import java.util.Date;
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
@ManagedBean(name = "detalleSolicitud")
@SessionScoped
public class DetalleSolicitudBean{
    
    

    public DetalleSolicitudBean() {
        this.fecha = new Date();
        this.codigo = 2103110;
        this.estudiante = "Diego Borrero";
    }
    
    @ManagedProperty(value="#{loginBean}")
    private ShiroLoginBean shiroLoginBean;
    public Date fecha;
    public String usuario;
    public int codigo;
    public String estudiante;
    Estudiante student;
    String consejero;
    List<Solicitud> cancelaciones;
    String impacto;
    String proyeccion;


    ServiciosCancelaciones servicios = ServiciosCancelacionesFactory.getInstance().getServiciosCancelaciones();
    
    @PostConstruct
    private void initDate(){
        setUsuario(getShiroLoginBean().getUsername());
        try{
            cancelaciones = servicios.consultarSolicitudesDeCancelaciones(usuario+"@escuelaing.edu.co");
            consejero = usuario.replace("."," ");
        }catch (ExcepcionServiciosCancelaciones ex) {
            Logger.getLogger(DetalleSolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void analisis(int estudiante,String materia){
        try {
            proyeccion = servicios.obtenerProyeccionByEstudiante(estudiante,materia);
        }catch(ExcepcionServiciosCancelaciones ex){
            Logger.getLogger(DetalleSolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public String getUsuario(){
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

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = new Date();
    }
    
    public String getConsejero() {
        return consejero;
    }

    public void setConsejero(String consejero) {
        this.consejero = consejero;
    }

    public String accionVista(){
        return "DetalleSolicitud.xhtml";
    }
    
    public String listadoCancelaciones(){
        return "ListadoSolCancel.xhtml";
    }
    
    public List<Solicitud> getCancelaciones() {
        return cancelaciones;
    }

    public void setCancelaciones(List<Solicitud> cancelaciones) {
        this.cancelaciones = cancelaciones;
    }
    
    public Estudiante estudianteIdSolicitud(int numero){
        try{
            student = servicios.consultarEstudiantePorSolicitud(numero);
        }catch (ExcepcionServiciosCancelaciones ex) {
            Logger.getLogger(DetalleSolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return student;
    }
    
}
