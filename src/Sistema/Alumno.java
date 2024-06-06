package Sistema;

import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import Sistema.Enum.Mes;
import Sistema.Enum.Nivel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Alumno extends Persona implements Comparable<Alumno>, Serializable, I_From_JsonObect,I_Convertir_JsonObject {
    private int id;
    private static int contadorComprobante = 1;
    private Nivel nivel;
    private ArrayList<Tarea> tareas;
    private HashMap<Mes, Cuota> cuotaHashMap;
    private ArrayList<Boolean> asistencias;
    private ArrayList<Nota> notas;
    private ArrayList<Aviso> avisoPersoanlizado;


    public Alumno(int id, String nombre, String apellido, String mail, Nivel nivel) {
        super(nombre, apellido, mail);
        this.id = id;
        this.nivel = nivel;

    }

    public Alumno(String nombre, String apellido, String mail, Nivel nivel) {
        super(nombre, apellido, mail);
        this.nivel = nivel;
        this.tareas = new ArrayList<>();
        this.asistencias = new ArrayList<Boolean>();
        this.notas = new ArrayList<>();
        this.avisoPersoanlizado = new ArrayList<>();
        this.cuotaHashMap = new HashMap<>();
    }

    /** metodos para recibir informacion
     * */

    /**
     * @param tarea
     */

    public void recibirTarea(Tarea tarea)
    {
        tareas.add(tarea);
    }

    /**
     *
      * @param aviso
     */
    public void recibirAviso(Aviso aviso)
    {
        avisoPersoanlizado.add(aviso);
    }

    /**
     *
     * @param mes
     * @param cuota
     */
    public void pagarCuota(Mes mes,Cuota cuota)
    {
        cuota.setComprobante(contadorComprobante++);
        cuotaHashMap.put(mes,cuota);
    }

    /**
     *
     * @param asistencia
     */
    public void registrarAsistencia(Date fecha, boolean asistencia)
    {
        asistencias.add(asistencia);
    }

    /**
     *
     * @param nota
     */
    public void recibirNota (Nota nota)
    {
        notas.add(nota);
    }

    /**
     *
     * @param mes
     */
    public void Comprobante(Mes mes)
    {
        Cuota cuota= cuotaHashMap.get(mes);
        if(cuota !=null)
        {
            System.out.println("Comprobante de pago "+ cuota.getComprobante());
        }else
            System.out.println(" No se encontro la cuota de ese mes");
    }


    //region Getters and Setters
    public int getId() {
        return id;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public void setId(int id) {
        this.id = id;
    }
//endregion
    @Override
    public int compareTo(Alumno o) {
        return Integer.compare(this.id, o.id);
    }

    @Override
    public boolean equals(Object o) {
        boolean esIgual= super.equals(o);
        if(esIgual)
        {
            Alumno alumno = (Alumno) o;
            if(this.id==alumno.id)
            {
                esIgual= true;
            }
        }
     return esIgual;
    }

    @Override
    public int hashCode() {
        return 1;
    }


    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = super.convertirJsonObject();
        jsonObject.put("Id", id);
        jsonObject.put("Nivel",nivel);

        //convierte ArrayList en JsonArray
        JSONArray tareasArray= new JSONArray();
        for (Tarea t : tareas) {
            tareasArray.put(t.convertirJsonObject());
        }
        jsonObject.put("Tareas",tareasArray);

        //convierte HashMap de cuotas en JsonArray
        JSONArray cuotasArray = new JSONArray();
        for(Map.Entry<Mes,Cuota> entry : cuotaHashMap.entrySet())
        {
            JSONObject c = new JSONObject();
            c.put("Mes",entry.getKey().toString());
            c.put("Cuota",entry.getKey().toString());
            cuotasArray.put(c);
        }
        jsonObject.put("Cuotas",cuotasArray);

        return jsonObject;
    }

    @Override
    public void fromJsonObject(JSONObject jsonObject) {

        ///JsonObject to alumnot
        try {
        String nombre = jsonObject.getString("Nombre");
        String apellido= jsonObject.getString("apellido");
        String mail = jsonObject.getString("mail");
        Nivel nivel = Nivel.valueOf(jsonObject.getString("Nivel"));
        int id = jsonObject.getInt("Id");
        Alumno alumno = new Alumno(nombre,apellido,mail,nivel);



        //JsonArray to tareas
        JSONArray tareasArray = jsonObject.getJSONArray("Tareas");

        for(int i=0; i<tareasArray.length();i++)
        {
            Tarea tarea =  new Tarea();
            tarea.fromJsonObject(tareasArray.getJSONObject(i));
            tareas.add(tarea);
        }


            //JsonArray to asistencias
         JSONArray asistArray = jsonObject.getJSONArray("asistencias");
        this.asistencias = new ArrayList<>();
        for(int i=0; i<asistArray.length();i++)
        {
            asistencias.add(asistArray.getBoolean(i));
        }


        //Jsonarray to notas
            JSONArray notasArray = jsonObject.getJSONArray("notas");
            this.notas = new ArrayList<>();
            for (int i=0; i<notasArray.length();i++)
            {
                Nota nota = new Nota();
                nota.fromJsonObject(notasArray.getJSONObject(i));
                notas.add(nota);
            }


            //jsonarray to aviso
            JSONArray jsonArrayAviso = jsonObject.getJSONArray("avisos");
            this.avisoPersoanlizado =new ArrayList<>();
            for(int i=0; i<jsonArrayAviso.length();i++)
            {
                Aviso aviso = new Aviso();
                aviso.fromJsonObject(jsonArrayAviso.getJSONObject(i));
                avisoPersoanlizado.add(aviso);
            }

            //jsonArray to cuotas
            JSONArray jsonArrayCuotas = jsonObject.getJSONArray("cuotas");
            for(int i=0; i<jsonArrayCuotas.length();i++)
            {
                JSONObject cuotaObject = jsonArrayCuotas.getJSONObject(i);
                Mes mes = Mes.valueOf("Mes");
                Cuota cuota = new Cuota();
                cuota.fromJsonObject(cuotaObject.getJSONObject("cuotas"));
                alumno.cuotaHashMap.put(mes,cuota);
            }


    } catch (JSONException e) {
        throw new RuntimeException(e);
    }
    }


    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nivel=" + nivel +
                ", tareas=" + tareas +
                ", cuotaHashMap=" + cuotaHashMap +
                ", asistencias=" + asistencias +
                ", notas=" + notas +
                ", avisoPersoanlizado=" + avisoPersoanlizado +
                "} " + super.toString();
    }


}
