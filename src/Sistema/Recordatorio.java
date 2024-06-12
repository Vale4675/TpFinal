package Sistema;

import java.util.Calendar;

public class Recordatorio {

    private Calendar fecha;
    private String tipo;
    private String detalle;
    private int id;

    public Recordatorio(Calendar fecha, String tipo, String detalle, int id) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.detalle = detalle;
        this.id = id;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Recordatorio{" +
                "fecha=" + fecha +
                ", tipo='" + tipo + '\'' +
                ", detalle='" + detalle + '\'' +
                ", id=" + id +
                '}';
    }
}
