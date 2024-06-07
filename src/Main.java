import Archivo.ControladoraDeArchivo;
import Archivo.JsonUtiles;
import Excepciones.AlumnoNoEncontrado;
import Excepciones.UsuarioYaExiste;
import Sistema.*;
import Excepciones.PasswordIncorrecto;
import Excepciones.UsuarioIncorrecto;
import Sistema.Enum.Mes;
import Sistema.Enum.Nivel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {


    private static Scanner scanner = new Scanner(System.in);
    private static Sistema sistema = new Sistema();
    private static GestionAlumno gestionAlumno = new GestionAlumno();


    public static void main(String[] args) throws PasswordIncorrecto, AlumnoNoEncontrado, UsuarioIncorrecto, UsuarioYaExiste {


        System.out.println("---- Usuario -----\n" +
                "\n" +
                "¡Bienvenido al sistema de gestión de alumnos! Este manual te guiará a través de las distintas  funcionalidades disponibles en esta aplicación.\n" +
                "\n" +
                "1. Iniciar Sesión\n" +
                "\n" +
                "Al abrir la aplicación, se te pedirá que elijas una opción. Para comenzar, selecciona la opción \"1 -> Iniciar sesión\". Luego, ingresa tu correo electrónico y contraseña cuando se te solicite.\n" +
                "\n" +
                "2. Registrarse\n" +
                "\n" +
                "Si aún no tienes una cuenta, puedes registrarte seleccionando la opción \"2 -> Registrarse\". Completa los campos solicitados, incluyendo tu nombre, apellido, correo electrónico y contraseña.\n" +
                "\n" +
                "3. Recuperar Contraseña\n" +
                "\n" +
                "En caso de olvidar tu contraseña, puedes recuperarla seleccionando la opción \"3 -> Recuperar contraseña\". Ingresa tu correo electrónico y la nueva contraseña que deseas utilizar.\n" +
                "\n" +
                "4. Ver Mis Datos\n" +
                "\n" +
                "Después de iniciar sesión, podrás ver tus datos personales seleccionando la opción \"4 -> Ver mis datos\". Aquí encontrarás información como tu nombre, apellido y correo electrónico.\n" +
                "\n" +
                "5. Eliminarse del Sistema\n" +
                "\n" +
                "Si deseas eliminar tu cuenta del sistema, selecciona la opción \"5 -> Eliminarse del sistema\". Se te pedirá que ingreses tu correo electrónico y contraseña para confirmar la eliminación de tu cuenta.\n" +
                "\n" +
                "6. Salir\n" +
                "\n" +
                "Para salir de la aplicación, selecciona la opción \"6 -> Salir\".\n" +
                "\n" +
                "Gestión de Alumnos\n" +
                "\n" +
                "Después de iniciar sesión, tendrás acceso a las siguientes opciones relacionadas con la gestión de alumnos:\n" +
                "\n" +
                "1. Registrar Alumno\n" +
                "\n" +
                "Registra un nuevo alumno en el sistema proporcionando su nombre, apellido, correo electrónico y nivel (INICIAL, INTERMEDIO o AVANZADO).\n" +
                "2. Buscar Alumno\n" +
                "\n" +
                "Busca un alumno en el sistema ingresando su ID.\n" +
                "3. Listar Alumnos\n" +
                "\n" +
                "Muestra la lista de todos los alumnos registrados en el sistema.\n" +
                "4. Eliminar Alumno\n" +
                "\n" +
                "Elimina un alumno del sistema ingresando su ID.\n" +
                "5. Tomar Asistencia\n" +
                "\n" +
                "Registra la asistencia de un alumno seleccionando esta opción.\n" +
                "6. Otra Opción\n" +
                "\n" +
                "Si deseas realizar otra acción, selecciona esta opción para volver al menú anterior.\n" +
                "7. Menú Anterior\n" +
                "\n" +
                "Regresa al menú anterior si deseas seleccionar otra opción.\n" +
                "¡Ahora estás listo para utilizar todas las funciones de esta aplicación de gestión de alumnos! Si tienes alguna pregunta o necesitas ayuda adicional, no dudes en consultar este manual o ponerte en contacto con el soporte técnico. ¡Disfruta utilizando el sistema!" +
                "\n");

        sistema= ControladoraDeArchivo.leer("Sistema.dat");
        gestionAlumno.leerAlumnos();
        int opcion;
        do {
            System.out.println("-----BIENVENIDO-----");
            System.out.println("------ELIGE UNA OPCION-------");
            System.out.println(" 1 -> Registrarse");
            System.out.println(" 2 -> Iniciar sesion");
            System.out.println(" 3 -> Recuperar contraseña");
            System.out.println(" 4 -> Ver mis datos");
            System.out.println(" 5 -> Eliminarse del sistema");
            System.out.println(" 6 -> Salir");
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    Profesor p = iniciarSecion();
                    if(p!=null)
                    {
                        try {
                            menuProfe(p);
                        } catch (UsuarioYaExiste e) {
                            System.out.println(e.getMessage());;
                        }
                    }
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

        ControladoraDeArchivo.grabar(sistema,"Sistema.dat");

        gestionAlumno.grabarAlumnos();

    }

    //region   Gestion para Usuario
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
        } catch (UsuarioIncorrecto | AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }


    public static Profesor iniciarSecion() throws PasswordIncorrecto, UsuarioIncorrecto, AlumnoNoEncontrado {
        System.out.println("introduce el mail");
        String mail = scanner.next();
        System.out.println("introduce la contraseña");
        String password = scanner.next();
        Profesor profesor = null;
        try {
            profesor = sistema.iniciarSesion(mail, password);
            System.out.println("Bienvenido " + profesor.getNombre());
        }catch (PasswordIncorrecto|UsuarioIncorrecto e)
        {
            System.out.println(e.getMessage());
        }
        return profesor;
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

    public static void mostrarInfoProfesor() throws AlumnoNoEncontrado
    {
        Profesor profesor = sistema.getProfesor();
        if (profesor != null) {
            System.out.println(profesor.toString());
        } else {
            throw new AlumnoNoEncontrado("Profesor no encontrado");
        }

    }

    public static void eliminarDatos() {
        try {
            System.out.println(" Ingrese el mail");
            String mail = scanner.next();
            System.out.println(" Ingrese contraseña");
            String contrasenia = scanner.next();
            sistema.eliminarDelSistema(mail, contrasenia);

            System.out.println("El profesor ha sido eliminado con exito " + sistema.getProfesor());

        } catch (UsuarioIncorrecto usuarioIncorrecto) {
            System.out.println(usuarioIncorrecto.getMessage());
        }
    }

    public static void menuProfe(Profesor p) throws UsuarioYaExiste {


        int opcion;
        do {
            System.out.println("------ELIGE UNA OPCION-------");
            System.out.println(" 1 -> Registrar Alumno");
            System.out.println(" 2 -> Buscar Alumno");
            System.out.println(" 3 -> Listar Alumnos");
            System.out.println(" 4 -> Eliminar Alumno");
            System.out.println(" 5 -> Tomar asistencia");
            System.out.println(" 6 -> Mandar aviso");
            System.out.println(" 7 -> Mandar Tarea");
            System.out.println(" 8 -> Mandar nota" );
            System.out.println(" 9 -> Cobrar cuota ");
            System.out.println(" 10 -> Menu anterior");
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    registrandoAlumno();
                    break;
                case 2:
                    buscarAlumno();
                    break;
                case 3:
                    System.out.println(listarAlumnos());
                    break;
                case 4:
                    eliminarAlumno();
                    break;
                case 5:
                    registrarAsistencia();
                    break;
                case 6:
                    mandarAviso();
                    break;
                case 7:
                    mandarTarea();
                    break;
                case 8:
                    mandarNota();
                    break;
                case 9:
                    cobrarCuota();
                    break;
                case 10:
                    System.out.println("Volviendo al menu anterior");
                    break;

                default:
                    System.out.println("Opcion invalida");

            }
        } while (opcion != 10);

    }


//endregion


//region   Gestion para alumnos

    /**
     * funciona
     */
    private static void registrandoAlumno() {
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
            System.out.println("El alumno "+ nombre+ "ha sido registrado con exito");
        } catch (UsuarioYaExiste e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Nivel no valido");
        }
    }

    /**
     * funciona
     */

    private static void buscarAlumno() {
        System.out.println("ingrese el id del alumno");
        int id=scanner.nextInt();
        Alumno alumno = null;
        try {
            alumno = gestionAlumno.buscar(id);
            mostrarInfoAlumno(alumno);
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * funciona
     * @return
     */

    private static StringBuilder listarAlumnos() {
        StringBuilder sb= gestionAlumno.listar();
        return sb;
    }

    /**
     * funciona
     */
    private static void eliminarAlumno() {
        System.out.println("ingrese el id");
        int id= scanner.nextInt();
        try {
            gestionAlumno.eliminar(id);
            System.out.println("El alumno con el id "+ id+" ha sido eliminado");
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }

    }

    private static void registrarAsistencia()  {

        System.out.println("ingrese id del alumno");
        int id = scanner.nextInt();
        System.out.println("ingrese la fecha dd,mm,yyyy");
        String fechita = scanner.next();
        Date fecha = null;
        try {
            fecha = new SimpleDateFormat("dd,MM,yyyy").parse(fechita);
        } catch (ParseException e) {
            System.out.println("formato no valido");
        }
        boolean asistio = asistencia();
        Alumno alumno =null;
        try {
            if(asistio)
            {
                alumno= gestionAlumno.buscar(id);
                alumno.registrarAsistencia( fecha ,asistio);
                mostrarInfoAlumno(alumno);
            }
        }catch (AlumnoNoEncontrado e)
        {
            System.out.println("Alumno ausente");
        }
      

    }

    private static boolean asistencia() {
        boolean asistencia = true;
        System.out.println(" 1: Ausente \n  2: Presente ");
        int asis = scanner.nextInt();
        if (asis == 1) {
            asistencia = false;
        }
        return asistencia;

    }

    private static void mostrarInfoAlumno(Alumno a) throws AlumnoNoEncontrado
    {
        if(a!=null)
        {
            System.out.println(a.toString());
        }
        else {
            throw new AlumnoNoEncontrado("Alumno no encontrado");
        }
    }

    private static void mandarAviso()
    {
        System.out.println("ingrese el id del alumno");
        int id= scanner.nextInt();
        System.out.println("ingrese el aviso");
        String aviso = scanner.next();
        try {
            System.out.println("Ingrese la fecha");
            String date = scanner.next();
            Date fecha = null;
            fecha = new SimpleDateFormat("dd,MM,yyyy").parse(date);
            Aviso avisito = new Aviso(fecha,aviso);
            Alumno alumno = gestionAlumno.buscar(id);
            alumno.recibirAviso(avisito);
        } catch (ParseException | AlumnoNoEncontrado e) {
            System.out.println("formato no valido");
        }
    }

    private  static void mandarTarea()
    {
        System.out.println("ingrese el id del alumno");
        int id= scanner.nextInt();
        System.out.println("ingrese la descripcion de la tarea");
        String tareita = scanner.next();
        System.out.println("Fue entregada ? ");
        boolean entreg= scanner.nextBoolean();

        try {
            System.out.println("Ingrese la fecha");
            String date = scanner.next();
            Date fecha = null;
            fecha = new SimpleDateFormat("dd,MM,yyyy").parse(date);
            Tarea tarea= new Tarea(id,tareita,fecha,entreg);
              Alumno alumno = gestionAlumno.buscar(id);
              alumno.recibirTarea(tarea);
            System.out.println("Tarea enviada al alumno "+alumno.getNombre());
        } catch (ParseException e) {
            System.out.println("formato no valido");
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());;
        }

    }

    private static void cobrarCuota()
    {
        System.out.println("ingrese el id del alumno");
        int id= scanner.nextInt();
        System.out.println("Numero de comprobante");
        int c = scanner.nextInt();
        System.out.println("Ingrese el mes de la cuota");
        String mes= scanner.next();
        Mes m =Mes.valueOf(mes);
        System.out.println("ingrese el monto");
        double monto = scanner.nextDouble();
        System.out.println("Pagado t/f");
        boolean pagado = scanner.nextBoolean();
        Cuota cuota = new Cuota(c,monto,pagado);

        System.out.println("Comprobante de pago Clases de ingles ");
        System.out.println("Comprobante n°  "+c );
        System.out.println("Mes " +  mes);
        System.out.println("Monto " + monto);

        try {
            Alumno alumno = gestionAlumno.buscar(id);
            alumno.pagarCuota(m,cuota);
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }


   private static  void mandarNota()
   {
       System.out.println("escribe el id del alumno");
       int id= scanner.nextInt();
       System.out.println("Escribe la calificacion");
       double nota = scanner.nextInt();
       System.out.println("Escribe comentario");
       String comentario = scanner.next();
       try {
           Alumno alumno = gestionAlumno.buscar(id);
           Nota notita = new Nota(nota,comentario);
           alumno.recibirNota(notita);
           System.out.println("nota enviada al alumno ");
       } catch (AlumnoNoEncontrado e) {
           System.out.println(e.getMessage());;
       }


   }


    //endregion

}