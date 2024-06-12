import Excepciones.AlumnoNoEncontrado;
import Interfaz.I_Metodos;
import Sistema.Alumno;

import java.util.ArrayList;

public class Calendario implements I_Metodos<Evento> {
    private ArrayList<Evento> eventos;

    public Calendario() {
       eventos= new ArrayList<>();
    }


    @Override
    public void agregar(Evento evento) {
        eventos.add(evento);

    }

    @Override
    public void eliminar(int id) {
        for (Evento e:eventos) {
            if(e.getAlumno().getId() == id)
            {
                eventos.remove(e);
                System.out.println("El evento del alumno "+ id+ "ha sido eliminado\n");
            }
            else {
                System.out.println("evento no encontrado");
            }
        }


    }

    @Override
    public Evento buscar(int id) {
        boolean esta=false;
        for (Evento e:eventos) {
            if(e.getAlumno().getId()==id)
            {

            }

        }
        return null;
    }

    @Override
    public StringBuilder listar() {
        return null;
    }
}
