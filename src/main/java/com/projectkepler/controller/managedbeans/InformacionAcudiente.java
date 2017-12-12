/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.controller.managedbeans;

import com.projectkepler.services.ExcepcionServiciosCancelaciones;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.projectkepler.services.entities.Estudiante;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import com.projectkepler.services.ServiciosCancelaciones;
import com.projectkepler.services.ServiciosCancelacionesFactory;
import com.projectkepler.services.entities.Solicitud;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guzman
 */
@ManagedBean(name = "infoCancel")
@SessionScoped
public class InformacionAcudiente {
    Estudiante alumno;
    int codigo;
    List<Solicitud> solicitudesCancel;
    int sol;
    Solicitud solInfo;
    String impacto;
    ServiciosCancelaciones servicios = ServiciosCancelacionesFactory.getInstance().getServiciosCancelaciones();
    

    public int getCodigo(){
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        codigo=Integer.parseInt((req.getParameter("id")));
        sol=Integer.parseInt(req.getParameter("sol"));
        return codigo;
    }
    
    public Estudiante getAlumno(){
        if(alumno==null){
            cargarInformacion();
        }
        return alumno;
    }

    public void setAlumno(Estudiante alumno) {
        this.alumno = alumno;
    }

    public void cargarInformacion(){
        try{
            alumno=servicios.consultarEstudianteById(getCodigo());
            solicitudesCancel=servicios.consultarSolicitudesPorEstudiante(codigo);
            solInfo=servicios.consultarSolicitudPorNumero(sol);
            String[] asignatura={solInfo.getAsignatura().getNemonico()};
            impacto=servicios.consultarImpactoByEstudianteAsignatura(codigo,asignatura);
            
        }catch(ExcepcionServiciosCancelaciones ex){
            Logger.getLogger(DetalleSolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Solicitud> getSolicitudesCancel() {
        return solicitudesCancel;
    }

    public void setSolicitudesCancel(List<Solicitud> solicitudesCancel) {
        this.solicitudesCancel = solicitudesCancel;
    }

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public Solicitud getSolInfo() {
        return solInfo;
    }

    public void setSolInfo(Solicitud solInfo) {
        this.solInfo = solInfo;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }
    
    
    public void actualizarAcuse(){
        try{
            servicios.actualizarAcuseSolicitud(sol,solInfo.isAcuseRecibo());
            alumno=null;
        }catch(ExcepcionServiciosCancelaciones ex){
            Logger.getLogger(DetalleSolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

}

