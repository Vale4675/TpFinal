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
    private ArrayList<Recordatorio> recordatorioArrayList;


    public Alumno(int id, String nombre, String apellido, String mail, Nivel nivel) {
        super(nombre, apellido, mail);
        this.id = id;
        this.nivel = nivel;

    }

    public Alumno() {
        super();
        this.nivel = nivel;
        this.tareas = new ArrayList<>();
        this.asistencias = new ArrayList<Boolean>();
        this.notas = new ArrayList<>();
        this.avisoPersoanlizado = new ArrayList<>();
        this.cuotaHashMap = new HashMap<>();
        this.recordatorioArrayList = new ArrayList<>();

    }

    public Alumno(String nombre, String apellido, String mail, Nivel nivel) {
        super(nombre, apellido, mail);
        this.nivel = nivel;
        this.tareas = new ArrayList<>();
        this.asistencias = new ArrayList<Boolean>();
        this.notas = new ArrayList<>();
        this.avisoPersoanlizado = new ArrayList<>();
        this.cuotaHashMap = new HashMap<>();
        this.recordatorioArrayList = new ArrayList<>();
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
     *se agrega la cuota pagada del alumno
     * @param mes
     * @param cuota
     */
    public void pagarCuota(Mes mes,Cuota cuota)
    {
        cuota.setComprobante(contadorComprobante++);
        cuotaHashMap.put(mes,cuota);
    }

    public void recibirRecordatorio(Recordatorio r)
    {
        recordatorioArrayList.add(r);
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

    /**
     * contiene una lista de cuotas,
     * metodo para verificar si esta vencida y si es asi, genera un nuevo aviso
     * @return
     */

    public ArrayList<Aviso> verificarCuotas() {
        ArrayList<Aviso> avisos = new ArrayList<>();
        for (Map.Entry<Mes, Cuota> entry : cuotaHashMap.entrySet()) {
            Cuota cuota = entry.getValue();
            if (cuota.estaVencida()) {
                Aviso aviso = new Aviso(cuota.getFechaVencimiento().getTime(), "La cuota del mes " + entry.getKey() + " está vencida.");
                avisos.add(aviso);
                this.avisoPersoanlizado.add(aviso);
            }
        }
        return avisos;
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

    /**
     *
     * @param o
     * @return
     */
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



    /**
     * Convierte a JsonObject y Array
     * @return
     * @throws JSONException
     */

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = super.convertirJsonObject();
        jsonObject.put("Id", id);
        jsonObject.put("Nivel",nivel.toString());

        JSONArray tareasArray= new JSONArray();
        for (Tarea t : tareas) {
            tareasArray.put(t.convertirJsonObject());
        }
        jsonObject.put("Tarea",tareasArray);

        JSONArray cuotasArray = new JSONArray();
        for(Map.Entry<Mes,Cuota> entry : cuotaHashMap.entrySet())
        {
            JSONObject c = new JSONObject();
            c.put("Mes",entry.getKey().toString());
            c.put("Cuota",entry.getKey().toString());
            cuotasArray.put(c);
        }
        jsonObject.put("Cuota",cuotasArray);

        JSONArray jsonArrayAsistencia = new JSONArray();
        for (Boolean asistencia : asistencias)
        {
         jsonArrayAsistencia.put(asistencia);
        }
        jsonObject.put("Asistencia",jsonArrayAsistencia);
        JSONArray jsonArrayAviso= new JSONArray();
        for (Aviso aviso :avisoPersoanlizado ) {
            jsonArrayAviso.put(aviso.convertirJsonObject());
        }
        jsonObject.put("Aviso",jsonArrayAviso);

        JSONArray jsonArrayNota= new JSONArray();
        for (Nota n:notas) {
            jsonArrayNota.put(n.convertirJsonObject());
        }
        jsonObject.put("Nota",jsonArrayNota);

        JSONArray jsonArrayRec = new JSONArray();
        for (Recordatorio r:recordatorioArrayList) {
            jsonArrayRec.put(r.convertirJsonObject());
        }
        jsonObject.put("Recordatorio",recordatorioArrayList);

        return jsonObject;
    }

    /**
     * de Json a java
     * @param jsonObject
     */
    @Override
    public void fromJsonObject(JSONObject jsonObject) {

        ///JsonObject to alumnot
        try {
        String nombre = jsonObject.getString("Nombre");
        String apellido= jsonObject.getString("Apellido");
        String mail = jsonObject.getString("mail");
        Nivel nivel = Nivel.valueOf(jsonObject.getString("Nivel"));
        int id = jsonObject.getInt("Id");
        Alumno alumno = new Alumno(nombre,apellido,mail,nivel);
            System.out.println(" estoy en fromJson alumnos ");

        //JsonArray to tareas
        JSONArray tareasArray = jsonObject.getJSONArray("Tarea");
        this.tareas = new ArrayList<>();
        for(int i=0; i<tareasArray.length();i++)
        {
            System.out.println(" estoy en fromJson tareas ");
            Tarea tarea =  new Tarea();
           tarea.fromJsonObject(tareasArray.getJSONObject(i));
          // tareas.add(tareasArray.getJSONObject(i));
        }


            //JsonArray to asistencias
         JSONArray asistArray = jsonObject.getJSONArray("Asistencia");
        this.asistencias = new ArrayList<>();
        for(int i=0; i<asistArray.length();i++)
        {
            System.out.println(" estoy en fromJson asistencia ");
            asistencias.add(asistArray.getBoolean(i));
            //asistencias.add();
        }


        //Jsonarray to notas
            JSONArray notasArray = jsonObject.getJSONArray("Nota");
            this.notas = new ArrayList<>();
            for (int i=0; i<notasArray.length();i++)
            {
                Nota nota = new Nota();
                System.out.println(" estoy en fromJson notas ");
                nota.fromJsonObject(notasArray.getJSONObject(i));
                notas.add(nota);
            }


            //jsonarray to aviso
            JSONArray jsonArrayAviso = jsonObject.getJSONArray("Aviso");
            this.avisoPersoanlizado =new ArrayList<>();
            for(int i=0; i<jsonArrayAviso.length();i++)
            {
                Aviso aviso = new Aviso();
                System.out.println(" estoy en fromJson avisos ");
                aviso.fromJsonObject(jsonArrayAviso.getJSONObject(i));
                avisoPersoanlizado.add(aviso);
            }

            //jsonArray to cuotas
            JSONArray jsonArrayCuotas = jsonObject.getJSONArray("Cuota");
            for(int i=0; i<jsonArrayCuotas.length();i++)
            {
                System.out.println(" estoy en fromJson cuotas ");
                JSONObject cuotaObject = jsonArrayCuotas.getJSONObject(i);
                Mes mes = Mes.valueOf("Mes");
                Cuota cuota = new Cuota();
                cuota.fromJsonObject(cuotaObject.getJSONObject("Cuota"));
                alumno.cuotaHashMap.put(mes,cuota);
            }
            //JsonArray to Recordatorio
            JSONArray jsonArray = jsonObject.getJSONArray("Recordatorio");
            for(int i=0; i<jsonArray.length();i++)
            {
                JSONObject recorJson = jsonArray.getJSONObject(i);
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.fromJsonObject(recorJson.getJSONObject("Recordatorio"));
                alumno.recordatorioArrayList.add(recordatorio);
            }

    } catch (JSONException e) {
        throw new RuntimeException(e);
    }
    }


    /**
     *
     * @return
     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Nombre: ").append(getNombre()).append("\n");
        sb.append("Apellido: ").append(getApellido()).append("\n");
        sb.append("Mail: ").append(getMail()).append("\n");
        sb.append("Nivel: ").append(nivel).append("\n");
        sb.append("Tareas:\n");
        for (Tarea tarea : tareas) {
            sb.append("\t").append(tarea.toString()).append("\n");
        }
        sb.append("Cuotas:\n");
        for (Map.Entry<Mes, Cuota> entry : cuotaHashMap.entrySet()) {
            sb.append("\t").append(entry.getKey()).append(": ").append(entry.getValue().toString()).append("\n");
        }
        sb.append("Asistencias:\n");
        for (Boolean asistencia : asistencias) {
            sb.append("\t").append(asistencia ? "Presente" : "Ausente").append("\n");
        }
        sb.append("Notas:\n");
        for (Nota nota : notas) {
            sb.append("\t").append(nota.toString()).append("\n");
        }
        sb.append("Avisos Personalizados:\n");
        for (Aviso aviso : avisoPersoanlizado) {
            sb.append("\t").append(aviso.toString()).append("\n");
        }
        sb.append("Recordatorios:");
        for (Recordatorio recordatorio : recordatorioArrayList) {
            sb.append(recordatorio.toString()).append("\n");
        }
        return sb.toString();
    }
}
