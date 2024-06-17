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
    private static Scanner teclado = new Scanner(System.in);//solo para string
    private static Sistema sistema = new Sistema();
    private static GestionAlumno gestionAlumno = new GestionAlumno();


    public static void main(String[] args) throws PasswordIncorrecto, AlumnoNoEncontrado, UsuarioIncorrecto, UsuarioYaExiste {

        try {
            sistema = ControladoraDeArchivo.leer("Sistema.dat");
            gestionAlumno = ControladoraDeArchivo.leer("Alumno.dat");
            //Json
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
                    System.out.println(sistema.getProfesor().toString());
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

            //Json
            gestionAlumno.grabarAlumnos();

        } catch (Exception e) {
            System.out.println("Error al grabar archivo" + e.getMessage());
        }


    }

    //   Gestion para Usuario
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
            System.out.println(nombre);
        } catch (UsuarioIncorrecto e) {
            System.out.println(e.getMessage());
        }
    }

    public static Profesor iniciarSesion() throws PasswordIncorrecto, UsuarioIncorrecto, AlumnoNoEncontrado {
        System.out.println("introduce el mail");
        String mail = scanner.next();
        System.out.println("introduce la contraseña");
        String password = scanner.next();
        Profesor profesor = null;
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


///Gestion cuotas

    private static void agregarCuotaMenu() {
        try {
            System.out.println("Ingrese el ID del alumno:");
            int id = scanner.nextInt();
            Alumno alumno = gestionAlumno.buscar(id);
            System.out.println("Ingrese el mes de la cuota (1-12):");
            int mesNumero = scanner.nextInt();
            Mes mes = Mes.values()[mesNumero - 1];
            System.out.println("Ingrese el importe de la cuota:");
            double importe = scanner.nextDouble();
            Calendar fechaVencimiento = Calendar.getInstance();
            fechaVencimiento.set(Calendar.DAY_OF_MONTH, 10); // Cuota vence el día 10
            Cuota cuota = new Cuota(importe, fechaVencimiento);
            cuota.setPagado(true);
            alumno.pagarCuota(mes, cuota);
            sistema.getProfesor().cobrarCuota(id, mes, cuota);
            System.out.println("Cuota agregada con éxito.");
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * verifica la cuota si esta vencida
     */

    private static void verificarCuotasMenu() {
        sistema.getProfesor().verificarCuotasAlumnos();
        System.out.println("Cuotas vencidas verificadas y avisos generados.");

    }


    private static void generarComprobanteMenu() {
        try {
            System.out.println("Ingrese el ID del alumno:");
            int id = scanner.nextInt();
            System.out.println("Ingrese el mes de la cuota para generar el comprobante (1-12):");
            int mesNumero = scanner.nextInt();
            Mes mes = Mes.values()[mesNumero - 1];
            sistema.getProfesor().generarComprobantePago(id, mes);
            System.out.println("Comprobante generado con éxito.");
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al generar comprobante: " + e.getMessage());
        }
    }


    //Gestion recordatorio

    private static void agregarRecordatorioMenu() {
        Calendar fecha = agregarRecordatorio();
        TipoRecordatorio tp = obtenerTipoRecordatorio();
        String detalle = obtenerDetalle();
        System.out.println("El recordatorio es para un alumno en particular s/n");
        String rta = scanner.next().toUpperCase();
        Recordatorio recordatorio = new Recordatorio(fecha, tp, detalle);
        if (rta.equalsIgnoreCase("s")) {
            int id = obtenerIdAlumno();
            try {
                Alumno alumno = gestionAlumno.buscar(id);
                alumno.recibirRecordatorio(recordatorio);
                sistema.getProfesor().agregarRecordatorio(recordatorio);
                System.out.println(" El recordatorio ha sido agregado al alumno  " + alumno.getNombre());
            } catch (AlumnoNoEncontrado e) {
                System.out.println(e.getMessage());
            }
        } else {
            sistema.getProfesor().agregarRecordatorio(recordatorio);
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
        Calendar fecha = obtenerFecha(usarFecha);
        return fecha;
    }

    private static TipoRecordatorio obtenerTipoRecordatorio() {
        try {
            System.out.println(" Tipo de recordatorio ->  EXAMEN, PAGO_DE_CUOTA, REUNION_DE_PADRE, ENTREGAR_TAREA, FECHA_LIMITE_PARA_ENTREGAR ");
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


    private static Calendar obtenerFecha(Boolean usarFecha) {
        Calendar fecha = Calendar.getInstance();
        if (!usarFecha) {
            System.out.println("Ingrese la fecha  (dd,MM,yyyy):");
            String fechaStr = scanner.next();
            try {
                //divide la cadena de texto separadas por  coma
                String[] fechaC = fechaStr.split(",");
                int dia = Integer.parseInt(fechaC[0]);
                int mes = Integer.parseInt(fechaC[1]);
                int anio = Integer.parseInt(fechaC[2]);
                fecha.set(anio, mes, dia, 0, 0, 0);
            } catch (Exception e) {
                System.out.println("Formato no valido" + e.getMessage());
                return null;
            }
        } else {
            System.out.println(" Fecha y hora actuales " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecha.getTime()));
        }

        return fecha;
    }

    private static void eliminarRecordatorioMain() {
        TipoRecordatorio tp = obtenerTipoRecordatorio();
        try {
            sistema.getProfesor().eliminarRecordatorio(tp);
            System.out.println("Los recordatorios han sido eliminados de la lista del profesor");
        } catch (RecordatorioNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }

    private static void buscarRecordatorioMain() {
        TipoRecordatorio tp = obtenerTipoRecordatorio();
        try {
            ArrayList<Recordatorio> recordatorios = sistema.getProfesor().buscarRecordatorio(tp);
            System.out.println("El recordatorio es " + recordatorios);
        } catch (RecordatorioNoEncontrado | ColeccionVacia e) {
            System.out.println("Recordatorio no encontrado");
        }
    }

    //Gestion profesor
    private static void registrarAsistencia() {

        System.out.println("ingrese id del alumno");
        int id = scanner.nextInt();
        System.out.println("ingrese la fecha dd,mm,yyyy");
        String fechita = teclado.nextLine();
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

    /**
     * manda a todos los alumnos un aviso general y el
     * Hashset de alumnos lo recibe
     */
    private static void mandarAvisoGeneral() {
        System.out.println("ingrese el aviso");
        String descp = scanner.next();
        try {
            System.out.println("Ingrese la fecha");
            String date = teclado.nextLine();
            Date fecha = new SimpleDateFormat("dd,MM,yyyy").parse(date);
            sistema.getProfesor().mandarAvisoGenerales(fecha, descp);
        } catch (ParseException e) {
            System.out.println("formato no valido");
        }
    }

    private static void mandarAvisoPersonalisado() {
        int id = obtenerIdAlumno();
        System.out.println("ingrese el aviso");
        String descp = teclado.nextLine();
        try {
            System.out.println("Ingrese la fecha");
            String date = teclado.nextLine();
            Date fecha = new SimpleDateFormat("dd,MM,yyyy").parse(date);
            Alumno alumno = gestionAlumno.buscar(id);
            Aviso aviso = new Aviso(fecha, descp);
            alumno.recibirAviso(aviso);
        } catch (ParseException e) {
            System.out.println("formato no valido" + e.getMessage());
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }

    private static void mandarTarea() {
        int id = obtenerIdAlumno();
        System.out.println("ingrese la descripcion de la tarea");
        String tareita = scanner.next();
        try {
            System.out.println("Ingrese la fecha");
            String date = teclado.nextLine();
            Date fecha = new SimpleDateFormat("dd,MM,yyyy").parse(date);
            Tarea tarea = new Tarea(id, tareita, fecha, false);
            Alumno alumno = gestionAlumno.buscar(id);
            alumno.recibirTarea(tarea);//el alumno recibe la tarea
            System.out.println(" Tarea enviada al alumno " + alumno.getNombre());

        } catch (ParseException e) {
            System.out.println("formato no valido");
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
        String comentario = teclado.nextLine();
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

    private static void registrarAlumnoMenu() {
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
            Alumno alumno = gestionAlumno.buscarPormail(mail);
            sistema.getProfesor().agregar(alumno);
            System.out.println("El alumno " + nombre + " con id " + alumno.getId() + " ha sido registrado con exito\n");

        } catch (UsuarioYaExiste e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Nivel no valido");
        }
    }

    private static void buscarAlumnoMenu() {
        int id = obtenerIdAlumno();
        try {
            Alumno alumno = gestionAlumno.buscar(id);
            System.out.println(alumno.toString());
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }

    }

    private static void eliminarAlumno() {
        int id = obtenerIdAlumno();
        try {
            gestionAlumno.eliminar(id);
            System.out.println("El alumno con el id " + id + " ha sido eliminado\n");
        } catch (AlumnoNoEncontrado e) {
            System.out.println(e.getMessage());
        }

    }

    //menu
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
            System.out.println(" 10 -> Agregar cuota ");
            System.out.println(" 11 -> Verificar Cuota Vencida");
            System.out.println(" 12 -> Imprimir comprobante");
            System.out.println(" 13 -> Agenda ");
            System.out.println(" 14 -> Menu anterior");
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                    registrarAlumnoMenu();
                    break;
                case 2:
                    buscarAlumnoMenu();
                    break;
                case 3:
                    System.out.println(gestionAlumno.listar());
                    break;
                case 4:
                    eliminarAlumno();
                    break;
                case 5:
                    registrarAsistencia();
                    break;
                case 6:
                    mandarAvisoPersonalisado();
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
                    agregarCuotaMenu();
                    break;
                case 11:
                    verificarCuotasMenu();
                    break;
                case 12:
                    generarComprobanteMenu();
                    break;
                case 13:
                    menuRecordatorio();
                    break;
                case 14:
                    System.out.println("Volviendo al menu anterior");
                    break;

                default:
                    System.out.println("Opcion invalida");

            }
        } while (opcion != 14);

    }

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
                    try {
                        System.out.println(sistema.getProfesor().listarRecordatorios());
                    } catch (ColeccionVacia e) {
                        System.out.println(e.getMessage());
                    }
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


}