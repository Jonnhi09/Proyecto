/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.controller.managedbeans;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author dxmortem
 */
@ManagedBean(name = "detalleSolicitud")
@SessionScoped
public class DetalleSolicitudBean{

    public DetalleSolicitudBean() {
        listaSol=new ArrayList<>();
        List<String> listainfo;
        for(int j=0;j<5;j++){
            listainfo = new ArrayList<>();
            listainfo.add((new Date()).toString());
            listainfo.add("1"+String.valueOf(j));
            listainfo.add("nombre"+String.valueOf(j));
            if(j%2==0){
                listainfo.add("Pendiente");
            }else{
                listainfo.add("Tramitada");
            }
            this.listaSol.add(listainfo);
        }
        this.fecha = new Date();
        this.profesor = "Hector cadavid";
        this.codigo = 2103110;
        this.estudiante = "Diego Borrero";
    }
    
    public Date fecha;
    public String profesor;
    public int codigo;
    public String estudiante;
    public List<List<String>> listaSol;

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
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

    public List<List<String>> getListaSol() {
        return listaSol;
    }

    public void setListaSol(List<List<String>> listaSol) {
        this.listaSol = listaSol;
    }

    public String accionVista(){
        return "DetalleSolicitud.xhtml";
    }
    
}
