package edu.eci.cosw.sampleapp.test;

import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelacionesFactory;
import com.projectKepler.services.ServiciosCancelaciones;
import com.projectKepler.services.entities.Acudiente;
import com.projectKepler.services.entities.ConsejeroAcademico;
import com.projectKepler.services.entities.Estudiante;
import java.util.List;
import static org.junit.Assert.assertEquals;
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
    public void consultarProgramaTest (){
        String programa="";
        try{
            programa=servicios.consultarProgramaById(173183);
        }catch (ExcepcionServiciosCancelaciones e){
            
        }
        assertEquals(programa,"{ \"programa\": \"ing. sistemas\", \"vertion\": 13, \"courses\": [ { \"nombre\": \"PREM\", \"creditos\": 4, \"PreRec\": \"\", \"coReq\": \"\", \"historialNotas\": [], \"tercios\": [], \"estado\":\"A\" }, { \"nombre\": \"CALD\", \"creditos\": 4, \"preReq\": \"PREM\", \"coReq\": \"\", \"historialNotas\": [], \"tercios\": [], \"estado\":\"A\" }, { \"nombre\": \"CIED\", \"creditos\": 4, \"preReq\": \"CALD\", \"coReq\": \"\", \"historialNotas\": [], \"tercios\": [], \"estado\":\"V\" }, { \"nombre\": \"FFIS\", \"creditos\": 4, \"preReq\": \"\", \"coReq\": \"\", \"historialNotas\": [], \"tercios\": [], \"estado\":\"A\" }, { \"nombre\": \"FIMF\", \"creditos\": 4, \"preReq\": \"FFIS\", \"coReq\": \"CALD\", \"historialNotas\": [], \"tercios\": [], \"estado\":\"A\" }, { \"nombre\": \"FIEM\", \"creditos\": 4, \"preReq\": \"FIMF\", \"coReq\": \"CIED\", \"historialNotas\": [], \"tercios\": [], \"estado\":\"C\" } ] }");        
        
    }
} 

