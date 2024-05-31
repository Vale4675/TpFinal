package Sistema;

import java.util.Date;

public class Asistencia {
    private Date fecha;
    private boolean presente;

    public Asistencia(Date fecha, boolean presente) {
        this.fecha = fecha;
        this.presente = presente;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean isPresente() {
        return presente;
    }

    @Override
    public String toString() {
        return "Asistencia{" +
                "fecha=" + fecha +
                ", presente=" + presente +
                '}';
    }
}

