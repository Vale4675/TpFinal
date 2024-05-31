package Sistema;

import java.util.Date;

public class Aviso {
    private Date fecha;
    private String mensaje;

    public Aviso(Date fecha, String mensaje) {
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

    public Aviso(String mensaje) {
        this.mensaje = mensaje;
        this.fecha = new Date();
    }

    public Date getFecha() {
        return fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Aviso{" +
                "fecha=" + fecha +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
