package Sistema;

import java.util.Date;

public class Tarea {
    private int id;
    private String descripcion;
    private Date fechaEntrega;
    private boolean entregada;

    public Tarea(int id, String descripcion, Date fechaEntrega, boolean entregada) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.entregada = entregada;
    }

    public boolean isEntregada() {
        return entregada;
    }

    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                '}';
    }
}
