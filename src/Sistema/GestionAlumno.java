package Sistema;

import Excepciones.AlumnoNoEncontrado;
import Excepciones.UsuarioYaExiste;
import Interfaz.I_Metodos;
import Sistema.Enum.Nivel;

import java.util.HashSet;
import java.util.Iterator;

public class GestionAlumno implements I_Metodos<Alumno> {

    private static int contadorId = 1;
    HashSet<Alumno> alumnoHashSet;

    public GestionAlumno() {
        this.alumnoHashSet = new HashSet<>();

    }

    @Override
    public void agregar(Alumno alumno)  {
        alumno.setId(contadorId++);
       alumnoHashSet.add(alumno);
        System.out.println(alumno);
    }

    @Override
    public void eliminar(int id) throws AlumnoNoEncontrado{
        Iterator<Alumno> iterator = alumnoHashSet.iterator();
        boolean encontrado = false;
        while (iterator.hasNext() && !encontrado)
        {
            Alumno alumno = iterator.next();
            if(alumno.getId()== id)
            {
                encontrado= true;
                iterator.remove();
            }
        }
        if(!encontrado)
        {
            throw  new AlumnoNoEncontrado(" El alumno con el id" + id + "no se encontro");
        }

    }

    @Override
    public Alumno buscar(int id) throws AlumnoNoEncontrado{
        boolean encontrado = false;
        Iterator <Alumno> iterator = alumnoHashSet.iterator();
        while (iterator.hasNext() && !encontrado)
        {
            Alumno alumno = iterator.next();
            if(alumno.getId()==id) {
                encontrado = true;
                return alumno;
            }
        }
        if (encontrado) {
            throw new AlumnoNoEncontrado("Alumno no encontrado");
        }


        return null;
    }

    @Override
    public StringBuilder listar() {
        StringBuilder st = new StringBuilder();
        Iterator<Alumno> iterator = alumnoHashSet.iterator();
        while (iterator.hasNext())
        {
            Alumno alumno = iterator.next();
            st.append(alumno.toString()).append("\n");
        }
        return st;
    }

    public void registrarAlumno(String nombre, String apellido, String mail, Nivel nivel )throws UsuarioYaExiste {
        Iterator<Alumno> iterator= alumnoHashSet.iterator();
        while (iterator.hasNext())
        {
            Alumno alumno = iterator.next();
            if(alumno.getMail().equals(mail))
            {
                throw new UsuarioYaExiste("El alumno ya existe");
            }
        }
        //si no existe crea y agrega al nuevo alumno
        Alumno alumno = new Alumno(nombre,apellido,mail,nivel);
        agregar(alumno);
    }

    @Override
    public String toString() {
        return "GestionAlumno{" +
                "alumnoHashSet=" + alumnoHashSet +
                '}';
    }
}
