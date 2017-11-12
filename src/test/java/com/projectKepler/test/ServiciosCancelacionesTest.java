package com.projectKepler.test;

import com.projectKepler.services.ExcepcionServiciosCancelaciones;
import com.projectKepler.services.ServiciosCancelacionesFactory;
import com.projectKepler.services.ServiciosCancelaciones;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

import org.junit.Test;

public class ServiciosCancelacionesTest {
    private ServiciosCancelaciones servicios;
    
    @Before
    public void setUp(){
        servicios=ServiciosCancelacionesFactory.getInstance().getTestingServiciosCancelaciones();
    }
    
    @Test
    public void consultarProgramaTest (){
        String programa="";
        try{
            programa=servicios.consultarPlanDeEstudioByIdEstudiante(173183);
            servicios.actualizarJustificacionById(2121465, "mis notas en primer y segundo tercio son muy bajas y no he aprendido lo suficiente", "CALD");
            
        }catch (ExcepcionServiciosCancelaciones e){
            e.getMessage();
        }
        assertEquals(programa,"{ \"programa\": \"ing. sistemas\", \"version\": 13, \"courses\": [ { \"nombre\": \"PREM\", \"creditos\": 4, \"PreRec\": \"\", \"coReq\": \"\", \"historialNotas\": [35], \"tercios\": [21, 46, 40], \"estado\":\"A\" }, { \"nombre\": \"CALD\", \"creditos\": 4, \"preReq\": \"PREM\", \"coReq\": \"\", \"historialNotas\": [], \"tercios\": [34], \"estado\":\"V\" }, { \"nombre\": \"CIED\", \"creditos\": 4, \"preReq\": \"CALD\", \"coReq\": \"\", \"historialNotas\": [], \"tercios\": [], \"estado\":\"P\" }, { \"nombre\": \"FFIS\", \"creditos\": 4, \"preReq\": \"\", \"coReq\": \"\", \"historialNotas\": [50], \"tercios\": [50,50,50], \"estado\":\"A\" }, { \"nombre\": \"FIMF\", \"creditos\": 4, \"preReq\": \"FFIS\", \"coReq\": \"CALD\", \"historialNotas\": [], \"tercios\": [20], \"estado\":\"V\" }, { \"nombre\": \"FIEM\", \"creditos\": 4, \"preReq\": \"FIMF\", \"coReq\": \"CIED\", \"historialNotas\": [], \"tercios\": [], \"estado\":\"P\" } ] }");
    }
} 

