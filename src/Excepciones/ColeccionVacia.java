package Excepciones;

public class ColeccionVacia extends NoSuchFieldException {
    public ColeccionVacia(String mensaje) {
        super(mensaje);
    }
}
