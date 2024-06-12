package Sistema;

import Archivo.ControladoraDeArchivo;
import Excepciones.PasswordIncorrecto;
import Excepciones.UsuarioIncorrecto;
import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Sistema implements Serializable, I_Convertir_JsonObject, I_From_JsonObect {

    Profesor profesor;


    public Sistema( Profesor profesor) {
        this.profesor = profesor;

    }

    public Sistema() {
        
    }

    public Profesor getProfesor() {
        return profesor;
    }



    /**
     * registra un profesor
     * @param nombre
     * @param apellido
     * @param mail
     * @param password
     * @throws UsuarioIncorrecto
     */
    public void registrarProfesor(String nombre, String apellido, String mail, String password) throws UsuarioIncorrecto {
        if (this.profesor!=null) {
            throw new UsuarioIncorrecto("Ya hay un profesor registrado");
        }
        this.profesor = new Profesor(nombre, apellido, mail, password);
        System.out.println(" profesor registrado ");

    }



    /**
     * inicia sesion con mail y password
     * @param email
     * @param password
     * @return
     * @throws PasswordIncorrecto
     * @throws UsuarioIncorrecto
     */
    public Profesor iniciarSesion (String email, String password) throws PasswordIncorrecto, UsuarioIncorrecto {
        if (this.profesor == null) {
            throw new UsuarioIncorrecto("No hay ningún profesor registrado.");
        }
        if (this.profesor.getMail().equals(email)) {
            if (this.profesor.getPassword().equals(password)) {
                return profesor;
            } else {
                throw new PasswordIncorrecto("Contraseña incorrecta.");
            }
        } else {
            throw new UsuarioIncorrecto("Persona incorrecto.");
        }
    }



    /**
     *recupera contraseña ingresando el mail y la nueva contraseña
     * @param mail
     * @param password
     * @throws UsuarioIncorrecto
     */
    public void recuperarContrasenia (String mail, String password) throws UsuarioIncorrecto {
        if (this.profesor==null)
        {
            throw new UsuarioIncorrecto("No hay ningún profesor registrado");
        }
        if(this.profesor.getMail().equals(mail))
        {
            this.profesor.setPassword(password);
        }
        else {
            throw new UsuarioIncorrecto("Persona Incorrecto");
        }

    }



    /**
     * si quiere borrar sus datos
     * @param mail
     * @param password
     * @throws UsuarioIncorrecto
     */
    public void eliminarDelSistema (String mail, String password) throws UsuarioIncorrecto {
        if(this.profesor.getMail().equals(mail))
        {
            if(this.profesor.getPassword().equals(password))
            {
                this.profesor=null;
            }
        }
        else throw new UsuarioIncorrecto("No existe el usuario");

    }



    @Override
    public String toString() {
        return "Sistema{" +
                "\nprofesor=" + profesor +
                '}';
    }

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Profesor", profesor);
        return jsonObject;
    }


    @Override
    public void fromJsonObject(JSONObject jsonObject) {
        try {
            JSONObject jsonObjectProfe = jsonObject.getJSONObject("Profesor");
            if(!jsonObjectProfe.equals(JSONObject.NULL))
            {
                Profesor profesor = new Profesor();
                profesor.fromJsonObject(jsonObjectProfe);
                this.profesor=profesor;
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }





}