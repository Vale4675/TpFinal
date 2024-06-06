package Archivo;

import java.io.*;

public class ControladoraDeArchivo {

    public static <T extends Serializable> void grabar(T objeto, String nombreaArchivo) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(nombreaArchivo);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(objeto);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (objectOutputStream != null)
                    objectOutputStream.close();
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }

    }

    public static <T extends Serializable> T leer(String nombreArchivo) {
        T objeto = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(nombreArchivo);
            objectInputStream = new ObjectInputStream(fileInputStream);
            objeto = (T) objectInputStream.readObject();
        } catch (IOException e) {
            System.out.println("Fin archivo");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (objectInputStream != null)
                    objectInputStream.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return objeto;
    }




}
