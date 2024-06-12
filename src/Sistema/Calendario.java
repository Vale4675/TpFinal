package Sistema;

import Excepciones.AlumnoNoEncontrado;
import Interfaz.I_Metodos;
import Sistema.Alumno;

import java.util.ArrayList;

public class Calendario implements I_Metodos<Evento> {
    private ArrayList<Evento> eventos;

    public Calendario() {
        eventos = new ArrayList<>();
    }


    @Override
    public void agregar(Evento evento) {
        eventos.add(evento);

    }

    @Override
    public void eliminar(int id) {
        for (Evento e : eventos) {
            if (e.getAlumno().getId() == id) {
                eventos.remove(e);
                System.out.println("El evento del alumno " + id + " ha sido eliminado\n");
            } else {
                System.out.println(" evento no encontrado");
            }
        }


    }

    @Override
    public Evento buscar(int id) {
        boolean encontrado = false;
        Evento evento = null;
        for (Evento e : eventos) {
            if (e.getAlumno().getId() == id) {
                encontrado = true;
            }
        }
        return evento;
    }

    public ArrayList<Evento> listarEventos()
    {
        return new ArrayList<>(eventos);
    }
    @Override
    public StringBuilder listar() {
        StringBuilder st = new StringBuilder();
        for (Evento e : eventos) {
            st.append(e.toString()).append("\n");
        }
        return st;
    }
}
