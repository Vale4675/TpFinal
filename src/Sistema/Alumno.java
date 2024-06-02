package Sistema;

import Interfaz.I_Convertir_JsonArray;
import Sistema.Enum.Mes;
import Sistema.Enum.Nivel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Alumno extends Persona implements Comparable<Alumno>, Serializable {
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

//metodos para recibir informacion
    public void recibirTarea(Tarea tarea)
    {
        tareas.add(tarea);
    }
    public void recibirAviso(Aviso aviso)
    {
        avisoPersoanlizado.add(aviso);
    }
    public void pagarCuota(Mes mes,Cuota cuota)
    {
        cuota.setComprobante(contadorComprobante++);
        cuotaHashMap.put(mes,cuota);
    }
    public void registrarAsistencia(boolean asistencia)
    {
        asistencias.add(asistencia);
    }

    public void recibirNota (Nota nota)
    {
        notas.add(nota);
    }
    public void Comprobante(Mes mes)
    {
        Cuota cuota= cuotaHashMap.get(mes);
        if(cuota !=null)
        {
            System.out.println("Comprobante de pago "+ cuota.getComprobante());
        }else
            System.out.println(" No se encontro la cuota de ese mes");
    }


    //getters y setters
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

    @Override
    public int compareTo(Alumno o) {
        return Integer.compare(this.id, o.id);
    }

    @Override
    public boolean equals(Object o) {
        boolean esIgual= false;
        if(o!=null)
        {
            Alumno alumno = (Alumno) o;
            if(alumno.getApellido().equals(((Alumno) o).getApellido()))
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

    /**
     *
     * @return JsonObject
     * @throws JSONException
     */
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
            c.put("mes",entry.getKey().toString());
            c.put("Cuota",entry.getKey().toString());
            cuotasArray.put(c);
        }
        jsonObject.put("Cuotas",cuotasArray);

        return jsonObject;
    }


}
