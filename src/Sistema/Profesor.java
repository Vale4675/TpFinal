package Sistema;

import Excepciones.AlumnoNoEncontrado;
import Interfaz.I_Convertir_JsonArray;
import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import Interfaz.I_Metodos;
import Sistema.Enum.Mes;
import Sistema.Enum.Nivel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Profesor extends Persona implements I_Metodos<Alumno>, Serializable, I_Convertir_JsonObject, I_From_JsonObect {

    private String password;
    private GestionAlumno alumnos;
    private ArrayList<Aviso> avisosGenerales;
    private List<Recordatorio> recordatorioList;


    public Profesor(String nombre, String apellido, String mail, String password) {
        super( nombre, apellido, mail);
        this.password = password;
        this.alumnos = new GestionAlumno();
        this.avisosGenerales = new ArrayList<>();
        this.recordatorioList = new ArrayList<>();

    }
    public Profesor() {
    }

//region Getters and Setters


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // endregion



    public void agregarRecordatorio(Calendar fecha,String tipo,String detalle, int id)
    {
        recordatorioList.add(new Recordatorio(fecha,tipo,detalle,id));
    }

    public void eliminarRecordatorio(Calendar fecha,String tipo)
    {
        for (Recordatorio r:recordatorioList) {
            if(r.getFecha().equals(fecha)&& r.getTipo().equals(tipo))
            {
                recordatorioList.remove(r);
                System.out.println("Recordadorio eliminado " + r);
            }
            else {
                System.out.println("recordatorio no encontrado");
            }
        }
    }

    public Recordatorio buscarRecordatorio(Calendar fecha,String tipo )
    {
        for (Recordatorio r:recordatorioList) {
            if(r.getFecha().equals(fecha)&& r.getTipo().equals(tipo)) {
                return r;
            }

        }
       return null;
    }
public void listarRecordatorios()
{
    for (Recordatorio r:recordatorioList) {
        System.out.println(r);
    }

}


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
    public Alumno buscar(int id) throws AlumnoNoEncontrado {
      return alumnos.buscar(id);
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
        return "Profesor{" + super.toString()+
                "\npassword='" + password + '\'' +
                "\nalumnos=" + alumnos +
                "\navisosGenerales=" + avisosGenerales +
                "} ";
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
        jsonObject.put("ListaDeAlumnos",alumnos.convertirJsonArray());
        JSONArray jsonArrayAviso = new JSONArray();
        for (Aviso a:avisosGenerales) {
          jsonArrayAviso.put(a.convertirJsonObject());
        }
        jsonObject.put("AvisosGenerales",jsonArrayAviso);
        return jsonObject;
    }

    @Override
    public void fromJsonObject(JSONObject jsonObject) {
        super.fromJsonObject(jsonObject);
        try {
            this.password = jsonObject.getString("Password");
            JSONArray jsonArrayAlu = jsonObject.getJSONArray("ListaDeAlumnos");
            for (int i=0; i<jsonArrayAlu.length();i++)
                {
                    JSONObject jsonAlumno =jsonArrayAlu.getJSONObject(i);
                    Alumno alumno = new Alumno();
                    alumno.fromJsonObject(jsonAlumno);
                    this.alumnos.agregar(alumno);
                }
            JSONArray jsonArrayAvisos = jsonObject.getJSONArray("AvisosGenerales");
            for (int i=0; i<jsonArrayAvisos.length();i++)
            {
                JSONObject jsonObjectAvisos = jsonArrayAvisos.getJSONObject(i);
                Aviso aviso = new Aviso();
                aviso.fromJsonObject(jsonObjectAvisos);
                this.avisosGenerales.add(aviso);
            }


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}