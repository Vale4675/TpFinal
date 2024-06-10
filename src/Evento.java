import Sistema.Alumno;

import java.util.Date;

public class Evento {
    private String descripcion;
    private Date fecha;
    private Alumno alumno;

    public Evento(String descripcion, Date fecha, Alumno alumno) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.alumno = alumno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }




}
