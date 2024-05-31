package Sistema;

import Excepciones.PasswordIncorrecto;
import Excepciones.UsuarioIncorrecto;

public class Sistema {

    Profesor profesor;


    public Sistema( Profesor profesor) {
        this.profesor = profesor;

    }

    public Sistema() {
        this.profesor = null;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void registrarProfesor(String nombre, String apellido, String mail, String password) throws UsuarioIncorrecto {
        if (this.profesor!=null) {
            throw new UsuarioIncorrecto("Ya hay un profesor registrado");
        }
        this.profesor = new Profesor(nombre, apellido, mail, password);
        System.out.println(" profesor registrado "+ this.profesor.getNombre());

    }


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
                "profesor=" + profesor +
                '}';
    }
}