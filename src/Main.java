import Excepciones.AlumnoNoEncontrado;
import Excepciones.UsuarioYaExiste;
import Sistema.*;
import Excepciones.PasswordIncorrecto;
import Excepciones.UsuarioIncorrecto;
import Sistema.Enum.Nivel;

import java.util.Scanner;

public class Main {


    private static Scanner scanner = new Scanner(System.in);
    private static Sistema sistema = new Sistema();
    private static GestionAlumno gestionAlumno = new GestionAlumno();

    public static void main(String[] args) {

        int opcion;
        do {
            System.out.println("-----BIENVENIDO-----");
            System.out.println("------ELIGE UNA OPCION-------");
            System.out.println(" 1 -> Iniciar sesion");
            System.out.println(" 2 -> Registrarse");
            System.out.println(" 3 -> Recuperar contraseña");
            System.out.println(" 4 -> Ver mis datos");
            System.out.println(" 5 -> Eliminarse del sistema");
            System.out.println(" 6 -> Salir");
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    iniciarSecion();
                    do {
                        System.out.println("------ELIGE UNA OPCION-------");
                        System.out.println(" 1 -> Registrar Alumno");
                        System.out.println(" 2 -> Buscar Alumno");
                        System.out.println(" 3 -> Listar Alumnos");
                        System.out.println(" 4 -> Eliminar Alumno");
                        System.out.println(" 5 -> Tomar asistencia");
                        System.out.println(" 6 -> otra opcion");
                        System.out.println(" 7 -> Menu anterior");
                        opcion = scanner.nextInt();
                        scanner.nextLine();
                        switch (opcion) {
                            case 1:
                                registrarAlumno();
                                break;
                            case 2:
                                System.out.println(" ingrese el id del alumno");
                                int id =scanner.nextInt();
                                Alumno alumno = buscarAlumno(id);
                                break;
                            case 3:
                                System.out.println(listarAlumno().toString());
                                break;
                            case 4:
                                System.out.println("ingrese el id del alumno a eliminar");
                                int id1 = scanner.nextInt();
                                eliminarAlumno(id1);
                                break;
                            case 5:
                                System.out.println(" saliendo del sistema");
                                break;
                            case 7:
                                System.out.println("Volviendo al menu anterior");
                                break;

                            default:
                                System.out.println("Opcion invalida");

                        }
                    } while (opcion != 7);

                    break;
                case 2:
                    registrarUsuario();

                    break;
                case 3:
                    recuperarContrasenia();
                    break;
                case 4:
                    mostrarInfoProfesor();
                    break;
                case 5:
                    eliminarDatos();
                    break;
                case 6:
                    System.out.println("saliendo del sistema");
                    break;
                default:
                    System.out.println(" Opcion invalida ");
            }

        } while (opcion != 6);


    }


    //Gestion para Usuario
    private static void registrarUsuario() {
        System.out.println("Ingrese su Nombre ");
        String nombre = scanner.next();
        System.out.println("Ingrese su Apellido ");
        String apellido = scanner.next();
        System.out.println("Ingrese su Mail ");
        String mail = scanner.next();
        System.out.println("Ingrese su contraseña ");
        String password = scanner.next();
        try {
            sistema.registrarProfesor(nombre, apellido, mail, password);
            mostrarInfoProfesor();
        } catch (UsuarioIncorrecto e) {
            System.out.println(e.getMessage());
        }
    }

    public static void iniciarSecion() {
        System.out.println("introduce el mail");
        String mail = scanner.next();
        System.out.println("introduce la contraseña");
        String password = scanner.next();
        try {
            Profesor profesor = sistema.iniciarSesion(mail, password);
            mostrarInfoProfesor();
        } catch (PasswordIncorrecto | UsuarioIncorrecto e) {
            System.out.println(e.getMessage());
        }
    }

    public static void recuperarContrasenia() {
        System.out.println("introduce el mail");
        String mail = scanner.next();
        System.out.println("Introduce la nueva contraseña");
        String password = scanner.next();
        try {
            sistema.recuperarContrasenia(mail, password);
            System.out.println("contrasenia actualizada con exito");
        } catch (UsuarioIncorrecto e) {
            System.out.println(e.getMessage());
        }

    }

    public static void mostrarInfoProfesor() {
        Profesor profesor = sistema.getProfesor();
        System.out.println("-----------------BIENVENIDO-----------------");
        System.out.println("Profesor");
        System.out.println("Nombre " + profesor.getNombre());
        System.out.println("Apellidos " + profesor.getApellido());
        System.out.println("Mail " + profesor.getMail());

    }

    public static void eliminarDatos() {
        try {
            System.out.println(" Ingrese el mail");
            String mail = scanner.next();
            System.out.println(" Ingrese contraseña");
            String contrasenia = scanner.next();
            sistema.eliminarDelSistema(mail, contrasenia);


        } catch (UsuarioIncorrecto usuarioIncorrecto) {
            System.out.println(usuarioIncorrecto.getMessage());
        }
    }



// Gestion para alumnos

    private static void registrarAlumno() {
        try {
            System.out.println("Nombre del alumno");
            String nombre = scanner.next();
            System.out.println("Apellido alumno");
            String apellido = scanner.next();
            System.out.println("Mail alumno");
            String mail = scanner.next();
            System.out.println("Nivel alumno: INICIAL, INTERMEDIO, AVANZADO");
            String nivelString = scanner.next().toUpperCase();
            Nivel nivel = Nivel.valueOf(nivelString);
            gestionAlumno.registrarAlumno(nombre, apellido, mail, nivel);
            System.out.println("alumno registrado con exito");
        } catch (UsuarioYaExiste e) {
            System.out.println(e.getMessage());
        }
    }
    private static Alumno buscarAlumno(int id)
    {

        Alumno alumno= null;
        try {
            alumno= gestionAlumno.buscar(id);
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }
        return alumno;
    }

    private static StringBuilder listarAlumno()
    {
        StringBuilder sb= gestionAlumno.listar();
        return sb;
    }

    private static void eliminarAlumno(int id)
    {
        try {
            gestionAlumno.eliminar(id);
        }catch (AlumnoNoEncontrado e)
        {
            System.out.println(e.getMessage());
        }

    }





}