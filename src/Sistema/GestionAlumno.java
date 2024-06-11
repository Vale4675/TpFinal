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

    private static int contadorId = 1;
    HashSet<Alumno> alumnoHashSet;

    public GestionAlumno() {
        this.alumnoHashSet = new HashSet<>();

    }

    public HashSet<Alumno> getAlumnoHashSet() {
        return alumnoHashSet;
    }

    /**
     * Metodos de la interfaz
     * @param alumno
     */
    @Override
    public void agregar(Alumno alumno) {
        alumno.setId(contadorId++);
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
        Alumno alumno =null;
        Iterator<Alumno> iterator = alumnoHashSet.iterator();
        while (iterator.hasNext() && !encontrado) {
                 alumno = iterator.next();
            if (alumno.getId() == id) {
                encontrado = true;
            }
        }
        return alumno;
    }

    @Override
    public StringBuilder listar() {
        StringBuilder st = new StringBuilder();
        Iterator<Alumno> iterator = alumnoHashSet.iterator();
        while (iterator.hasNext()) {
            Alumno alumno = iterator.next();
            st.append(alumno.toString()).append("\n");
            System.out.println( "estoy en listar gestion alumnos");
        }
        return st;
    }

    /**
     * Registra un alumno  lo agrega a la lista y lo guarda en un Json
     * @param nombre
     * @param apellido
     * @param mail
     * @param nivel
     * @throws UsuarioYaExiste
     */
    public void registrarAlumno(String nombre, String apellido, String mail, Nivel nivel) throws UsuarioYaExiste {
        Iterator<Alumno> iterator = alumnoHashSet.iterator();
        while (iterator.hasNext()) {
            Alumno alumno = iterator.next();
            if (alumno.getMail().equals(mail)) {
                throw new UsuarioYaExiste("El alumno ya existe");
            }
        }
        //si no existe crea y agrega al nuevo alumno
        Alumno alumno = new Alumno(nombre, apellido, mail, nivel);
        agregar(alumno);
        }
        public void grabarAlumnos()
        {
            try {
                JSONArray jsonArray=convertirJsonArray();
                JsonUtiles.grabar(jsonArray,"Alumnos");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    public void leerAlumnos()
    {
        String fuente = JsonUtiles.leer("Alumnos");
        System.out.println(fuente);
        try {
            JSONArray jsonArray = new JSONArray(fuente);
            for(int i=0; i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.getString("Nombre"));


            }

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    /**
     *
     * @return
     * @throws JSONException
     */
    @Override
    public JSONArray convertirJsonArray() throws JSONException {
        JSONArray jsonArrayAlumnos= new JSONArray();
        for (Alumno a: alumnoHashSet) {
            jsonArrayAlumnos.put(a.convertirJsonObject());
        }
        return jsonArrayAlumnos ;
    }
    @Override
    public String toString() {
        return "GestionAlumno{" +
                "alumnoHashSet=" + alumnoHashSet +
                '}';
    }
}
