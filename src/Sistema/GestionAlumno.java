package Sistema;

import Archivo.JsonUtiles;
import Excepciones.AlumnoNoEncontrado;
import Excepciones.UsuarioYaExiste;
import Interfaz.I_Convertir_JsonArray;
import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_Metodos;
import Sistema.Enum.Nivel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

public class GestionAlumno implements I_Metodos<Alumno>, Serializable, I_Convertir_JsonArray {

    private  int contadorId;
    HashSet<Alumno> alumnoHashSet;

    public GestionAlumno() {
        this.alumnoHashSet = new HashSet<>();
        this.contadorId =1;

    }


    ///metodos de la interfaz
    @Override
    public void agregar(Alumno alumno) {

        alumnoHashSet.add(alumno);
    }

    @Override
    public void eliminar(int id) throws AlumnoNoEncontrado {
        Iterator<Alumno> iterator = alumnoHashSet.iterator();
        boolean encontrado = false;
        while (iterator.hasNext() && !encontrado) {
            Alumno alumno = iterator.next();
            if (alumno.getId() == id) {
                encontrado = true;
                alumno.eliminarTodosLosRecordatorio();
                alumno.eliminarTodaLasNota();
                alumno.eliminarTodosLosAviso();
                alumno.eliminarTodosLosRecordatorio();
                iterator.remove();
            }
        }
        if (!encontrado) {
            throw new AlumnoNoEncontrado(" El alumno con el id" + id + "no se encontro");
        }

    }

    @Override
    public Alumno buscar(int id) throws AlumnoNoEncontrado {
        boolean encontrado = false;
        Alumno alumno = null;
        Iterator<Alumno> iterator = alumnoHashSet.iterator();
        while (iterator.hasNext() && !encontrado) {
            alumno = iterator.next();
            if (alumno.getId() == id) {
                encontrado = true;
                return alumno;
            }
        }
      throw new AlumnoNoEncontrado("Alumno no encontrado");

    }

    @Override
    public StringBuilder listar() {
        StringBuilder st = new StringBuilder();
        Iterator<Alumno> iterator = alumnoHashSet.iterator();
        while (iterator.hasNext()) {
            Alumno alumno = iterator.next();
            st.append(alumno.toString()).append("\n");
        }
        return st;
    }

    public Alumno buscarPormail(String mail) {
        boolean encontrado = false;
        Alumno alumno = null;
        Iterator<Alumno> iterator = alumnoHashSet.iterator();
        while (iterator.hasNext() && !encontrado) {
            alumno = iterator.next();
            if (alumno.getMail().equals(mail)) {
                encontrado = true;
            }
        }
        return alumno;
    }

    public void registrarAlumno(String nombre, String apellido, String mail, Nivel nivel) throws UsuarioYaExiste {
        Iterator<Alumno> iterator = alumnoHashSet.iterator();
        while (iterator.hasNext()) {
            Alumno alumno = iterator.next();
            if (alumno.getMail().equals(mail)) {
                throw new UsuarioYaExiste("El alumno ya existe");
            }
            if (alumno.getId()==contadorId)
            {
                throw new UsuarioYaExiste("EL ID YA EXISTE");
            }
        }
        Alumno alumno = new Alumno(nombre, apellido, mail, nivel);
        alumno.setId(contadorId++);
        alumnoHashSet.add(alumno);
    }

    public void grabarAlumnos() {
        try {
            JSONArray jsonArray = convertirJsonArray();
            JsonUtiles.grabar(jsonArray, "Alumnos");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void leerAlumnos() {
        String fuente = JsonUtiles.leer("Alumnos");
        System.out.println(fuente);
        try {
            JSONArray jsonArray = new JSONArray(fuente);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.getString("Nombre"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONArray convertirJsonArray() throws JSONException {
        JSONArray jsonArrayAlumnos = new JSONArray();
        for (Alumno a : alumnoHashSet) {
            jsonArrayAlumnos.put(a.convertirJsonObject());
        }
        return jsonArrayAlumnos;
    }

    @Override
    public String toString() {
        return "GestionAlumno{" +
                "alumnoHashSet=" + alumnoHashSet +
                '}';
    }
}
