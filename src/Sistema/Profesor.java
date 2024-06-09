package Sistema;

import Excepciones.AlumnoNoEncontrado;
import Interfaz.I_Convertir_JsonArray;
import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_Metodos;
import Sistema.Enum.Mes;
import Sistema.Enum.Nivel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Profesor extends Persona implements I_Metodos<Alumno>, Serializable, I_Convertir_JsonObject {

    private String password;
    private GestionAlumno alumnos;
    private ArrayList<Aviso> avisosGenerales;

    public Profesor(String nombre, String apellido, String mail, String password) {
        super( nombre, apellido, mail);
        this.password = password;
        this.alumnos = new GestionAlumno();
        avisosGenerales = new ArrayList<>();
    }

//region Getters and Setters

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
// endregion

    /**
     * Metodos de la interfaz
     * @param alumno
     */
     @Override
    public void agregar(Alumno alumno) {
        alumnos.agregar(alumno);

    }

    @Override
    public void eliminar(int id) {
        try {
            alumnos.eliminar(id);
            System.out.println(" eliminado correctamente");
        } catch (AlumnoNoEncontrado e) {
            System.out.println( e.getMessage());
        }

    }

    @Override
    public Alumno buscar(int id) {
        try {
            alumnos.buscar(id);
        }catch (AlumnoNoEncontrado A)
        {
            System.out.println(A.getMessage());
        }
        return null;
    }

    @Override
    public StringBuilder listar() {
        StringBuilder sb=alumnos.listar();
        return sb;
    }

    /**
     * Metodos para mandar informacion
     * @param tarea
     */


    /**
     *
     * @param tarea
     */
    public void mandarTarea (Tarea tarea)
    {
        for (Alumno alu: alumnos.alumnoHashSet) {
            alu.recibirTarea(tarea);
        }
    }



    /**
     *
     * @param fecha
     * @param mensaje
     */
    public void mandarAvisoGenerales (Date fecha,String mensaje)
    {
        Aviso aviso = new Aviso(fecha,mensaje);// creo el aviso
        avisosGenerales.add(aviso);             /// lo agrego al array
        for (Alumno alu:alumnos.alumnoHashSet) {// recorro el set de alumnos y envio el aviso
            alu.recibirAviso(aviso);
        }
    }


    /**
     *
     * @param id
     * @param fecha
     * @param mensaje
     * @throws AlumnoNoEncontrado
     */
    public void avisosPersonalisados(int id,Date fecha, String mensaje)throws AlumnoNoEncontrado
    {
        Alumno buscado = alumnos.buscar(id);
        if (buscado!=null)
        {
            Aviso aviso = new Aviso(fecha,mensaje);
            buscado.recibirAviso(aviso);
        }
    }


    public void cobrarCuota (int id, Mes mes, Cuota cuota)throws AlumnoNoEncontrado
    {
        Alumno buscado = alumnos.buscar(id);
        if(buscado!= null)
        {
            buscado.pagarCuota(mes,cuota);
        }
    }



    /**
     *
     * @param id
     * @param mes
     * @throws AlumnoNoEncontrado
     */
    public void generarComprobantePago(int id, Mes mes)throws AlumnoNoEncontrado
    {
        Alumno alumno = alumnos.buscar(id);

        if(alumno !=null)
        {
            alumno.Comprobante(mes);
        }else
            System.out.println(" No se encontro la cuota de ese mes");
    }


    public void tomarAsistencia (int id,Date fecha,Boolean asist) throws AlumnoNoEncontrado
    {
        Alumno alumno = alumnos.buscar(id);
        if(alumno!=null) {
                alumno.registrarAsistencia(fecha,asist);

        }else throw new AlumnoNoEncontrado("no se encontro el alumno");

    }



    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Profesor{" +
                "password='" + password + '\'' +
                ", alumnos=" + alumnos +
                ", avisosGenerales=" + avisosGenerales +
                "} " + super.toString();
    }


    /**
     *
     * @return
     * @throws JSONException
     */
    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = super.convertirJsonObject();
        jsonObject.put("Password",password);
        jsonObject.put("Lista de Alumnos",alumnos.convertirJsonArray());
        JSONArray jsonArrayAviso = new JSONArray();
        for (Aviso a:avisosGenerales) {
          jsonArrayAviso.put(a.convertirJsonObject());
        }
        jsonObject.put("Aviso Gneral",jsonArrayAviso);
        return jsonObject;
    }


}