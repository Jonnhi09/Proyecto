package edu.eci.cosw.sampleapp.test;

import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelacionesFactory;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.entities.Acudiente;
import com.projectKepler.services.entities.ConsejeroAcademico;
import com.projectKepler.services.entities.Estudiante;
import java.util.List;
import static org.junit.Assert.fail;
import org.junit.Before;

import org.junit.Test;

public class AppTest {
    private ServiciosCancelaciones servicios;
    
    @Before
    public void setUp(){
        servicios=ServiciosCancelacionesFactory.getInstance().getTestingServiciosCancelaciones();
    }
    
    @Test
    public void sampleTest() {
        String programa="";
        try{
            programa=servicios.consultarProgramaById(173183);
        }catch (ExcepcionServiciosCancelaciones e){
            
        }
        System.out.println(programa);
        
        
    }

} 

