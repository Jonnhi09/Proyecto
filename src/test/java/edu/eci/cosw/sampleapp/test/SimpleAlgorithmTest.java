package edu.eci.cosw.sampleapp.test;

import com.projectKepler.services.algorithm.Algorithm;
import com.projectKepler.services.algorithm.impl.SimpleAlgorithm;
import org.junit.Assert;
import static org.junit.Assert.fail;

import org.junit.Test;


/**
 * 
 * @author blackphantom
 * 
 * 
 * Clases de equivalencia.
 * 
 * CE1 Cuando se cancela una materia, se agregue en el proximo semestre y se sume.
 *      Resultado: cantidad Creditos cursos pendientes.
 * 
 * CE2 Cuando se cancele una materia que es correquisito de otras materia y se estan viendo, las otras materias se toman en cuenta para el siguiente semestre.
 *      Resultado : cantidad creditos cursos pendientes + las otras materias.
 * 
 *  
 *      
 */

public class SimpleAlgorithmTest {
private Algorithm a = new SimpleAlgorithm() ;

  @Test
  public void TestCE1() {
      String [] impacto = a.getImpact("FFIS", "{\n" +
"    \"programa\": \"ing. sistemas\",\n" +
"    \"version\": 13,        \n" +
"    \"creditsSemester\" : 18,\n" +
"    \"totalCredits\" : 24, \n" +
"    \"courses\": [\n" +
"        {\n" +
"            \"nombre\": \"PREM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CALD\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"PREM\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CIED\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"CALD\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FFIS\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FIMF\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"FFIS\",\n" +
"            \"coReq\": \"CALD\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FIEM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"FIMF\",\n" +
"            \"coReq\": \"CIED\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        }\n" +
"    ]\n" +
"}"
              , 
              
              "{\n" +
"    \"programa\": \"ing. sistemas\",\n" +
"    \"version\": 13,        \n" +
"    \"creditsSemester\" : 18,\n" +
"    \"totalCredits\" : 24, \n" +
"    \"courses\": [\n" +
"        {\n" +
"            \"nombre\": \"PREM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CALD\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"PREM\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CIED\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"CALD\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FFIS\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FIMF\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"FFIS\",\n" +
"            \"coReq\": \"CALD\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FIEM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"FIMF\",\n" +
"            \"coReq\": \"CIED\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        }\n" +
"    ]\n" +
"}");
      Assert.assertEquals("No esta calculando bien los creditos si cancelo una materia sin coreq","Si cancela FFIS le quedan: 24 creditos por ver.",impacto[0]);
  }
  
  @Test
  public void TestCE2() {
	String [] impacto = a.getImpact("CALD", "{\n" +
"    \"programa\": \"ing. sistemas\",\n" +
"    \"version\": 13,        \n" +
"    \"creditsSemester\" : 18,\n" +
"    \"totalCredits\" : 24, \n" +
"    \"courses\": [\n" +
"        {\n" +
"            \"nombre\": \"PREM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CALD\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"PREM\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"V\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CIED\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"CALD\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FFIS\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FIMF\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"FFIS\",\n" +
"            \"coReq\": \"CALD\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"V\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FIEM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"FIMF\",\n" +
"            \"coReq\": \"CIED\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        }\n" +
"    ]\n" +
"}" ,"{\n" +
"    \"programa\": \"ing. sistemas\",\n" +
"    \"version\": 13,        \n" +
"    \"creditsSemester\" : 18,\n" +
"    \"totalCredits\" : 24, \n" +
"    \"courses\": [\n" +
"        {\n" +
"            \"nombre\": \"PREM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CALD\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"PREM\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"V\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"CIED\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"CALD\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FFIS\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"\",\n" +
"            \"coReq\": \"\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"A\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FIMF\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"FFIS\",\n" +
"            \"coReq\": \"CALD\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"V\"\n" +
"        },\n" +
"        {\n" +
"            \"nombre\": \"FIEM\",\n" +
"            \"creditos\": 4,\n" +
"            \"preReq\": \"FIMF\",\n" +
"            \"coReq\": \"CIED\",\n" +
"            \"historialNotas\": [],\n" +
"            \"tercios\": [],\n" +
"            \"estado\":\"P\"\n" +
"        }\n" +
"    ]\n" +
"}");
      Assert.assertEquals("No esta calculando bien los creditos si cancelo una materia con coreq","Si cancela CALD le quedan: 16 creditos por ver.",impacto[0]);
  }

} 

