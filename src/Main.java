import Archivo.ControladoraDeArchivo;
import Archivo.JsonUtiles;
import Excepciones.*;
import Sistema.*;
import Sistema.Enum.Mes;
import Sistema.Enum.Nivel;
import Sistema.Enum.TipoRecordatorio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {


    private static Scanner scanner = new Scanner(System.in);
    private static Sistema sistema = new Sistema();
    private static GestionAlumno gestionAlumno = new GestionAlumno();


    public static void main(String[] args) throws PasswordIncorrecto, AlumnoNoEncontrado, UsuarioIncorrecto, UsuarioYaExiste {

        try {
            sistema = ControladoraDeArchivo.leer("Sistema.dat");
            System.out.println("Soy sistema" + sistema);
            gestionAlumno = ControladoraDeArchivo.leer("Alumno.dat");
            System.out.println("Soy Gestion"+ gestionAlumno);
            gestionAlumno.leerAlumnos();
        } catch (Exception e) {
            System.out.println("Error al leer archivos " + e.getMessage());
        }

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
                    Profesor p = iniciarSesion();
                    if (p != null) {
                        try {
                            menuProfe(p);
                        } catch (UsuarioYaExiste e) {
                            System.out.println(e.getMessage());
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

        try {
            ControladoraDeArchivo.grabar(sistema, "Sistema.dat");
            ControladoraDeArchivo.grabar(gestionAlumno, "Alumno.dat");
             gestionAlumno.grabarAlumnos();

        } catch (Exception e) {
            System.out.println("Error al grabar archivo" + e.getMessage());
        }


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


    public static Profesor iniciarSesion() throws PasswordIncorrecto, UsuarioIncorrecto, AlumnoNoEncontrado {
        System.out.println("introduce el mail");
        String mail = scanner.next();
        System.out.println("introduce la contraseña");
        String password = scanner.next();
        Profesor profesor= null;
        try {
            profesor = sistema.iniciarSesion(mail, password);
            System.out.println("Bienvenido " + profesor.getNombre());

        } catch (PasswordIncorrecto | UsuarioIncorrecto e) {
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

    public static void mostrarInfoProfesor() throws AlumnoNoEncontrado {
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

            System.out.println("El profesor " + sistema.getProfesor() + "  ha sido eliminado con exito");

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
            System.out.println(" 6 -> Mandar aviso personalisado");
            System.out.println(" 7 -> Mandar aviso general");
            System.out.println(" 8 -> Mandar Tarea");
            System.out.println(" 9 -> Mandar nota");
            System.out.println(" 10 -> Cobrar cuota ");
            System.out.println(" 11 -> Agenda ");
            System.out.println(" 12 -> Menu anterior");
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
                    listarAlumnos();
                    break;
                case 4:
                    eliminarAlumno();
                    break;
                case 5:
                    registrarAsistencia();
                    break;
                case 6:
                    mandarAviso();
                case 7:
                    mandarAvisoGeneral();
                    break;
                case 8:
                    mandarTarea();
                    break;
                case 9:
                    mandarNota();
                    break;
                case 10:
                    cobrarCuota();
                    break;
                case 11:
                    menuRecordatorio();
                    break;
                case 12:
                    System.out.println("Volviendo al menu anterior");
                    break;

                default:
                    System.out.println("Opcion invalida");

            }
        } while (opcion != 12);

    }


//endregion
///Gestion recordatorio

    private static void menuRecordatorio() {
        int opcion;
        do {
            System.out.println("Gestionar recordatorio");
            System.out.println(" 1 -> Agregar recordatorio");
            System.out.println(" 2 -> Listar recordatorio");
            System.out.println(" 3 -> Buscar recordatorio");
            System.out.println(" 4 -> Eliminar recordatorio");
            System.out.println(" 5 -> Volver al Menu anterior");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    agregarRecordatorioMenu();
                    break;
                case 2:
                    listarRecordatorioMenu();
                    break;
                case 3:
                    buscarRecordatorioMain();
                    break;
                case 4:
                    eliminarRecordatorioMain();
                    break;
                case 5:
                    System.out.println("Volviendo al menu anterior");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while (opcion != 5);
    }

    private static void listarRecordatorioMenu() {
        try {
            Profesor profesor = sistema.getProfesor();
            if (profesor != null) {
                profesor.listarRecordatorios();
            }
        } catch (RecordatorioNoEncontrado e) {
            System.out.println("Recordatorio no encontrado");
        }


    }


    private static void agregarRecordatorioMenu() {
        Calendar fecha = agregarRecordatorio();
        TipoRecordatorio tp = obtenerTipoRecordatorio();
        String detalle = obtenerDetalle();
        System.out.println("El recordatorio es para un alumno en particular s/n");
        String rta = scanner.next().toUpperCase();
        if (rta.equalsIgnoreCase("s")) {
            int id = obtenerIdAlumno();
            try {
                Alumno alumno = gestionAlumno.buscar(id);
                Recordatorio recordatorio = new Recordatorio(fecha, tp, detalle);
                alumno.recibirRecordatorio(recordatorio);
                sistema.getProfesor().agregarRecordatorio(fecha, tp, detalle);
                System.out.println(" El recordatorio ha sido agregado al alumno  " + alumno.getNombre());
            } catch (AlumnoNoEncontrado e) {
                System.out.println(e.getMessage());
            }
        } else {
            sistema.getProfesor().agregarRecordatorio(fecha, tp, detalle);
            System.out.println("  El recordatorio General ah sido realizado");
        }

    }


    private static Calendar agregarRecordatorio() {
        System.out.println("Quiere usar fecha automatica s/n");
        String rta = scanner.next().toUpperCase();
        boolean usarFecha = false;
        if (rta.equalsIgnoreCase("s")) {
            usarFecha = true;
        }
        Calendar fecha = obtenerFechaYHora(usarFecha);
        System.out.println(" usar fecha " + usarFecha);
        return fecha;
    }

    private static TipoRecordatorio obtenerTipoRecordatorio() {
        try {
            System.out.println(" Tipo de recordatorio ->  EXAMEN, PAGO_CUOTA, REUNION_PADRE, TAREA, FECHA_LIMITE ");
            String tipo = scanner.next().toUpperCase();
            return TipoRecordatorio.valueOf(tipo);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static int obtenerIdAlumno() {
        System.out.println("Ingrese el id del alumno");
        return scanner.nextInt();
    }

    private static String obtenerDetalle() {
        System.out.println("Ingrese la descripcion");
        return scanner.next();
    }


    private static Calendar obtenerFechaYHora(Boolean usarFecha) {
        Calendar fecha = null;
        if (!usarFecha) {
            System.out.println("Ingrese la fecha  (dd,MM,yyyy):");
            String fechaStr = scanner.next();
            System.out.println("Ingrese la hora");
            String horaStr = scanner.next();
            try {
                //divide la cadena de texto separadas po coma
                String[] fechaC = fechaStr.split(",");
                int dia = Integer.parseInt(fechaC[0]);
                int mes = Integer.parseInt(fechaC[1]);
                int anio = Integer.parseInt(fechaC[2]);
                String[] horaC = horaStr.split(",");
                int hora = Integer.parseInt(horaC[0]);
                int minuto = Integer.parseInt(fechaC[1]);
                fecha.set(anio, mes, dia, hora, minuto);
            } catch (Exception e) {
                System.out.println("Formato no valido" + e.getMessage());
            }
        } else {
            fecha = Calendar.getInstance();
            System.out.println(" Fecha y hora actuales " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecha.getTime()));
        }

        return fecha;
    }

    private static void eliminarRecordatorioMain() {
        System.out.println("Tipo de recordatorio ->  EXAMEN , PAGO_CUOTA, REUNION_PADRE, TAREA, FECHA_LIMITE ");
        String tipo = scanner.next().toUpperCase();
        TipoRecordatorio tp = TipoRecordatorio.valueOf(tipo);
        try {
            Recordatorio recordatorio = sistema.getProfesor().buscarRecordatorio(tp);
            sistema.getProfesor().eliminarRecordatorio(tp);
            System.out.println("El recordatorio " + recordatorio + " ha sido eliminado");
        } catch (RecordatorioNoEncontrado e) {
            System.out.println("Recordatorio no encontrado");
        }


    }

    private static void buscarRecordatorioMain() {
        System.out.println("Tipo de recordatorio ->  EXAMEN, PAGO_CUOTA, REUNION_PADRE, TAREA, FECHA_LIMITE ");
        String tipo = scanner.next().toUpperCase();
        TipoRecordatorio tp = TipoRecordatorio.valueOf(tipo);
        try {
            Recordatorio recordatorio = sistema.getProfesor().buscarRecordatorio(tp);
            System.out.println("El recordatorio es " + recordatorio);
        } catch (RecordatorioNoEncontrado e) {
            System.out.println("Recordatorio no encontrado");
        }
    }


//region   Gestion para alumnos


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
            System.out.println("El alumno " + nombre + " ha sido registrado con exito\n");
            sistema.getProfesor().agregar(new Alumno(nombre, apellido, mail, nivel));
        } catch (UsuarioYaExiste e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Nivel no valido");
        }
    }


    private static void buscarAlumno() {
        System.out.println("ingrese el id del alumno");
        int id = scanner.nextInt();

        try {
            Alumno alumno = gestionAlumno.buscar(id);
            mostrarInfoAlumno(alumno);
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }

    }


    private static void listarAlumnos() {
        System.out.println("estoy en listar alumno main");
        StringBuilder sb = gestionAlumno.listar();
        System.out.println(sb);
    }


    private static void eliminarAlumno() {
        System.out.println("ingrese el id");
        int id = scanner.nextInt();
        try {
            gestionAlumno.eliminar(id);
            System.out.println("El alumno con el id " + id + " ha sido eliminado\n");
            sistema.getProfesor().eliminar(id);
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }

    }

    private static void registrarAsistencia() {

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
        Alumno alumno = null;
        try {
            if (asistio) {
                alumno = gestionAlumno.buscar(id);
                alumno.registrarAsistencia(fecha, asistio);
                sistema.getProfesor().tomarAsistencia(id, fecha, asistio);
                System.out.println("Alumno " + alumno.getNombre() + "presente");
            }
        } catch (AlumnoNoEncontrado e) {
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

    private static void mostrarInfoAlumno(Alumno a) throws AlumnoNoEncontrado {
        if (a != null) {
            System.out.println(a.toString());
        } else {
            throw new AlumnoNoEncontrado("Alumno no encontrado");
        }
    }

    private static void mandarAvisoGeneral() {
        System.out.println("ingrese el aviso");
        String aviso = scanner.next();
        try {
            System.out.println("Ingrese la fecha");
            String date = scanner.next();
            Date fecha = null;
            fecha = new SimpleDateFormat("dd,MM,yyyy").parse(date);
            Aviso avisito = new Aviso(fecha, aviso);
            for (Alumno a : gestionAlumno.getAlumnoHashSet()) {
                a.recibirAviso(avisito);
            }
            sistema.getProfesor().mandarAvisoGenerales(fecha, aviso);

        } catch (ParseException e) {
            System.out.println("formato no valido");
        }
    }

    private static void mandarAviso() {
        System.out.println("ingrese el id del alumno");
        int id = scanner.nextInt();
        System.out.println("ingrese el aviso");
        String aviso = scanner.next();
        try {
            System.out.println("Ingrese la fecha");
            String date = scanner.next();
            Date fecha = null;
            fecha = new SimpleDateFormat("dd,MM,yyyy").parse(date);
            Aviso avisito = new Aviso(fecha, aviso);
            Alumno alumno = gestionAlumno.buscar(id);
            alumno.recibirAviso(avisito);
            sistema.getProfesor().avisosPersonalisados(id, fecha, aviso);
        } catch (ParseException | AlumnoNoEncontrado e) {
            System.out.println("formato no valido");
        }
    }

    private static void mandarTarea() {
        System.out.println("ingrese el id del alumno");
        int id = scanner.nextInt();
        System.out.println("ingrese la descripcion de la tarea");
        String tareita = scanner.next();
        /* System.out.println("1 = Fue entregada  ");
        System.out.println("2 = No fue entregada  ");
        int n = scanner.nextInt();
        boolean entrega = false;
        if(n == 1)
        {
            System.out.println("Tarea entregada");
            entrega = true;
        }
        else
        {
            System.out.println("La tarea no se entrego");
        }
         */
        try {
            System.out.println("Ingrese la fecha");
            String date = scanner.next();
            Date fecha = new SimpleDateFormat("dd,MM,yyyy").parse(date);
            Tarea tarea = new Tarea(id, tareita, fecha, false);
            Alumno alumno = gestionAlumno.buscar(id);
            alumno.recibirTarea(tarea);//el alumno recibe la tarea
            sistema.getProfesor().mandarTarea(tarea); // se guarda la tarea en el profesor
            System.out.println(" Tarea enviada al alumno " + alumno.getNombre());
        } catch (ParseException e) {
            System.out.println("formato no valido");
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
            ;
        }

    }

    private static void cobrarCuota() {
        System.out.println("ingrese el id del alumno");
        int id = scanner.nextInt();
        System.out.println("Numero de comprobante");
        int c = scanner.nextInt();
        System.out.println("Ingrese el mes de la cuota Ej ENERO,FEBRERO...");
        String mes = scanner.next();
        Mes m = Mes.valueOf(mes);
        System.out.println("ingrese el monto");
        double monto = scanner.nextDouble();
        System.out.println("1= Pagado ");
        System.out.println("2= No Pagado ");
        int n = scanner.nextInt();
        boolean pagado = false;
        try {
            if (n == 1) {
                pagado = true;
                Cuota cuota = new Cuota(c, monto, pagado);
                System.out.println("Comprobante de pago Clases de ingles ");
                System.out.println("Comprobante n°  " + c);
                System.out.println("Mes " + mes);
                System.out.println("Monto " + monto);
                Alumno alumno = gestionAlumno.buscar(id);
                sistema.getProfesor().cobrarCuota(id, m, cuota);
                sistema.getProfesor().generarComprobantePago(id, m);
                alumno.pagarCuota(m, cuota);
            }
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }


    private static void mandarNota() {
        System.out.println("escribe el id del alumno");
        int id = scanner.nextInt();
        System.out.println("Escribe la calificacion");
        double nota = scanner.nextInt();
        System.out.println("Escribe comentario");
        String comentario = scanner.next();
        try {
            Alumno alumno = gestionAlumno.buscar(id);
            Nota notita = new Nota(nota, comentario);
            alumno.recibirNota(notita);
            System.out.println("nota enviada al alumno ");
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
            ;
        }


    }


    //endregion


}