package Sistema;

import Excepciones.AlumnoNoEncontrado;
import Excepciones.ColeccionVacia;
import Excepciones.RecordatorioNoEncontrado;
import Excepciones.UsuarioYaExiste;
import Interfaz.I_Convertir_JsonArray;
import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import Interfaz.I_Metodos;
import Sistema.Enum.Mes;
import Sistema.Enum.Nivel;
import Sistema.Enum.TipoRecordatorio;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.*;

public class Profesor extends Persona implements I_Metodos<Alumno> ,Serializable, I_Convertir_JsonObject, I_From_JsonObect {

    private String password;
    private GestionAlumno alumnos;
    private ArrayList<Aviso> avisosGenerales;
    private ArrayList<Recordatorio> recordatorioList;


    public Profesor(String nombre, String apellido, String mail, String password) {
        super(nombre, apellido, mail);
        this.password = password;
        this.alumnos = new GestionAlumno();
        this.avisosGenerales = new ArrayList<>();
        this.recordatorioList = new ArrayList<>();

    }

    public Profesor() {
    }

//region Getters and Setters

    public ArrayList<Recordatorio> getRecordatorioList() {
        return recordatorioList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // endregion


    public void agregarRecordatorio(Recordatorio recordatorio) {
        recordatorioList.add(recordatorio);
    }


    public void eliminarRecordatorio(TipoRecordatorio tipo) throws RecordatorioNoEncontrado {
        Iterator<Recordatorio> iterator = recordatorioList.iterator();
        boolean encontrado = false;
        while (iterator.hasNext()) {
            Recordatorio r = iterator.next();
            if (r.getTipo().equals(tipo)) {
                iterator.remove();
                encontrado = true;
            }
        }

        if (!encontrado) {
            throw new RecordatorioNoEncontrado("No se encontraron recordatorios del tipo especificado");
        }
    }

    public ArrayList<Recordatorio> buscarRecordatorio(TipoRecordatorio tipo) throws RecordatorioNoEncontrado, ColeccionVacia {
        ArrayList<Recordatorio> list = new ArrayList<>();
        for (Recordatorio r : recordatorioList) {
            if (r.getTipo().equals(tipo)) {
                list.add(r);
            }
        }
        if (list.isEmpty()) {
            throw new ColeccionVacia("No hay recordatorios registrados");
        }
        return list;
    }

    public String listarRecordatorios() throws ColeccionVacia {
        if (recordatorioList.isEmpty()) {
            throw new ColeccionVacia("no hay recordatorios ingresados");
        }
        Collections.sort(recordatorioList);
        StringBuilder sb = new StringBuilder();
        for (Recordatorio r : recordatorioList) {
            sb.append(r).append("\n");
        }
        return sb.toString();
    }


    /**
     * recorre el arreglo de alumnos para verificar las cuotas vencidas
     * de todos los alumnos
     */
    public void verificarCuotasAlumnos() {
        for (Alumno alumno : alumnos.alumnoHashSet) {
            List<Aviso> avisosAlumno = alumno.verificarCuotas();
            avisosGenerales.addAll(avisosAlumno);
        }
    }

    /**
     * metodos para mandar informacion
     */

    public void mandarAvisoGenerales(Date fecha, String mensaje)  {
        Aviso aviso = new Aviso(fecha, mensaje);// creo el aviso
        for (Alumno alu : alumnos.alumnoHashSet) {// recorro el set de alumnos y envio el aviso
            alu.recibirAviso(aviso);
        }
    }

    public void cobrarCuota(int id, Mes mes, Cuota cuota) throws AlumnoNoEncontrado {
        Alumno buscado = alumnos.buscar(id);
        if (buscado != null) {
            buscado.pagarCuota(mes, cuota);
        }
    }

    public void generarComprobantePago(int id, Mes mes) throws AlumnoNoEncontrado {
        Alumno alumno = alumnos.buscar(id);

        if (alumno != null) {
            alumno.Comprobante(mes);
        }
    }

    public void tomarAsistencia(int id, Date fecha, Boolean asist) throws AlumnoNoEncontrado {
        Alumno alumno = alumnos.buscar(id);
        if (alumno != null) {
            alumno.registrarAsistencia(fecha, asist);
        }
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return "Profesor{" + super.toString() +
                "password='" + password + '\'' +
                ", alumnos=" + alumnos +
                ", avisosGenerales=" + avisosGenerales +
                ", recordatorioList=" + recordatorioList +
                "} ";
    }

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
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Alumno buscar(int id) throws AlumnoNoEncontrado {
        return alumnos.buscar(id);
    }

    @Override
    public StringBuilder listar() {
        StringBuilder sb = alumnos.listar();
        return sb;
    }
    /**
     * @return
     * @throws JSONException
     */
    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = super.convertirJsonObject();
        jsonObject.put("Password", password);
        jsonObject.put("ListaDeAlumnos", alumnos.convertirJsonArray());
        JSONArray jsonArrayAviso = new JSONArray();
        for (Aviso a : avisosGenerales) {
            jsonArrayAviso.put(a.convertirJsonObject());
        }
        jsonObject.put("AvisosGenerales", jsonArrayAviso);

        JSONArray jsonArrayRecord = new JSONArray();
        for (Recordatorio r : recordatorioList) {
            jsonArrayRecord.put(r.convertirJsonObject());
        }
        jsonObject.put("Recordatorio", jsonArrayRecord);


        return jsonObject;
    }

    @Override
    public void fromJsonObject(JSONObject jsonObject) {
        super.fromJsonObject(jsonObject);
        try {
            this.password = jsonObject.getString("Password");
            JSONArray jsonArrayAlu = jsonObject.getJSONArray("ListaDeAlumnos");
            for (int i = 0; i < jsonArrayAlu.length(); i++) {
                JSONObject jsonAlumno = jsonArrayAlu.getJSONObject(i);
                Alumno alumno = new Alumno();
                alumno.fromJsonObject(jsonAlumno);
                this.alumnos.agregar(alumno);
            }
            JSONArray jsonArrayAvisos = jsonObject.getJSONArray("AvisosGenerales");
            for (int i = 0; i < jsonArrayAvisos.length(); i++) {
                JSONObject jsonObjectAvisos = jsonArrayAvisos.getJSONObject(i);
                Aviso aviso = new Aviso();
                aviso.fromJsonObject(jsonObjectAvisos);
                this.avisosGenerales.add(aviso);
            }
            JSONArray jsonArrayRecord = jsonObject.getJSONArray("Recordatorio");
            for (int i = 0; i < jsonArrayRecord.length(); i++) {
                JSONObject jsonObjectRec = jsonArrayRecord.getJSONObject(i);
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.fromJsonObject(jsonObjectRec);
                this.recordatorioList.add(recordatorio);
            }


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}