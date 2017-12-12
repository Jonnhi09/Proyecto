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
import com.projectkepler.services.entities.Estudiante;
import com.projectkepler.services.entities.Solicitud;
import com.projectkepler.services.entities.Acudiente;
import com.projectkepler.services.email.*;
import java.util.ArrayList;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author MathewsGuz
 */
@ManagedBean(name = "detalleSolicitud")
@SessionScoped
public class DetalleSolicitudBean{
    

    public DetalleSolicitudBean() {
    }
    
    @ManagedProperty(value="#{loginBean}")
    private ShiroLoginBean shiroLoginBean;
    public Date fecha;
    public String usuario;
    public int codigo;
    public String estudiante;
    private Estudiante student;
    private String consejero;
    private List<Solicitud> cancelaciones;
    private String impacto;
    private ArrayList<ArrayList<String>> proyeccion;
    private String justificacion;
    private String materia;
    private Acudiente acudiente;
    private Solicitud solSelect;


    ServiciosCancelaciones servicios = ServiciosCancelacionesFactory.getInstance().getServiciosCancelaciones();
    
    @PostConstruct
    private void initDate(){
        setUsuario(getShiroLoginBean().getUsername());
        try{
            if(getShiroLoginBean().getSubject().hasRole("consejero")){
                cancelaciones = servicios.consultarSolicitudesDeCancelaciones(usuario+"@escuelaing.edu.co");  
            }
            consejero = usuario.replace("."," ");
        }catch (ExcepcionServiciosCancelaciones ex) {
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
        RequestContext.getCurrentInstance().update("Solicitudes");
        return "ListadoSolCancel.xhtml";
    }
    
    public List<Solicitud> getCancelaciones() {
        return cancelaciones;
    }
    
    public List<Solicitud> getAllCancelaciones() throws ExcepcionServiciosCancelaciones{
        return servicios.consultarSolicitudesPorCoordinador(usuario+"@escuelaing.edu.co");
    }
    public String getEstadoSolicitud(Solicitud s){
        
        String a = "Falta Aval de: ";
        if (!s.isAvalConsejero())
            a+="Consejero ";
        if(s.isNecesitaAcuseRecibo() && !s.isAcuseRecibo())
            a+="Acuendinte";
        if(a.equals("Falta Aval de: "))
            a="Tramitada";
        if(s.getEstado().equals("Aceptada"))
            a="Aceptada";
        else if (s.getEstado().equals("Rechazada"))
            a="Rechazada";
        return a;
    }

    public void setCancelaciones(List<Solicitud> cancelaciones) {
        this.cancelaciones = cancelaciones;
    }
    
    public void cambiarestadoSolicitudAceptada(Solicitud s){
        try{
            servicios.actualizarEstadoSolicitud(s.getNumero(), "Aceptada");
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha realizado la tramitacion correspondiente", null));
        }catch (ExcepcionServiciosCancelaciones ex) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, ex.getLocalizedMessage(), null));
        }
        
        
    }
    public void cambiarestadoSolicitudRechazada(Solicitud s){
        try{
            servicios.actualizarEstadoSolicitud(s.getNumero(), "Rechazada");
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha realizado la tramitacion correspondiente", null));
        }catch (ExcepcionServiciosCancelaciones ex) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, ex.getLocalizedMessage(), null));
        }
        
    }
    
    public Estudiante estudianteIdSolicitud(int numero){
        try{
            student = servicios.consultarEstudiantePorSolicitud(numero);
        }catch (ExcepcionServiciosCancelaciones ex) {
            Logger.getLogger(DetalleSolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return student;
    }

    public Solicitud getSolSelect() {
        return solSelect;
    }

    public void setSolSelect(Solicitud solSelect,boolean bool) {
        this.solSelect = solSelect;
        if(bool){
            cargarDatos();
        }
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public ArrayList<ArrayList<String>> getProyeccion() {
        return proyeccion;
    }

    public void setProyeccion(ArrayList<ArrayList<String>> proyeccion) {
        this.proyeccion = proyeccion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
    
    public Estudiante getStudent(){
        return student;
    }
    
    public void setStudent(Estudiante student){
        this.student=student;
    } 
    
    public void cargarDatos(){
        try{
            student=servicios.consultarEstudiantePorSolicitud(solSelect.getNumero());
            codigo=student.getCodigo();
            materia=solSelect.getAsignatura().getNemonico()+"-"+solSelect.getAsignatura().getNombre();
            String[] asignatura={solSelect.getAsignatura().getNemonico()};
            impacto=servicios.consultarImpactoByEstudianteAsignatura(codigo,asignatura);
            justificacion=solSelect.getJustificacion();
            proyeccion=servicios.consultarProyeccionByEstudianteAsignatura(codigo, asignatura);
            estudiante=student.getNombre();
        }catch (ExcepcionServiciosCancelaciones ex) {
            Logger.getLogger(DetalleSolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public void atualizarEstadoJustificacion(){
        try{
            if(solSelect.getComentarios()== null || solSelect.getComentarios().isEmpty()){
                solSelect.setEstado("No tramitada");
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese un comentario para continuar", null));
            }else{
                solSelect.setEstado("Tramitada");
                servicios.actualizarComentariosSolicitud(solSelect.getNumero(),solSelect.getComentarios());
                servicios.actualizarEstadoSolicitud(solSelect.getNumero(), solSelect.getEstado());
                enviarCorreo();
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "El comentario a sido agregado corretamente", null));
            }
        }catch (ExcepcionServiciosCancelaciones ex) {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, ex.getLocalizedMessage(), null));
        }
    }
    
    public void enviarCorreo(){
        if(solSelect.isNecesitaAcuseRecibo()||true){
            try{
                acudiente=servicios.consultarAcudientePorStudiante(student.getCodigo());
                Email email = new SimpleEmail("noreplay@escuelaing.edu.co",acudiente.getCorreo(),"Informacion de cancelaciones","A continuacion se presenta el link donde podra consultar la solicitud realizada por "+ student.getNombre() +" "+ "https://proyecto-pdsw.herokuapp.com/InfoAcudiente.xhtml?id="+Integer.toString(student.getCodigo())+"&sol="+solSelect.getNumero());
                EmailSender sender = new SimpleEmailSender(new EmailConfiguration());
                sender.send(email);
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "El correo se ha enviado", null));
            }catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, ex.getLocalizedMessage(), null));
            }
        }
    }
    
}
