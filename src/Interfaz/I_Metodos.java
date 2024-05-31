package Interfaz;

import Excepciones.AlumnoNoEncontrado;
import Excepciones.UsuarioIncorrecto;

public interface I_Metodos<T> {
    void agregar (T elemnto);
     void eliminar (int id) throws AlumnoNoEncontrado;
    T buscar (int id) throws AlumnoNoEncontrado;
    StringBuilder listar ();

}
