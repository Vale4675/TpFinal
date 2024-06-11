package Sistema;

import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tarea implements Serializable, I_Convertir_JsonObject, I_From_JsonObect{
    private int id;
    private String descripcion;
    private Date fechaEntrega;
    private boolean entregada;

    public Tarea(int id, String descripcion, Date fechaEntrega, boolean entregada) {
        this.id = id;
        this.fechaEntrega = fechaEntrega;
        this.entregada = entregada;
    }

    public Tarea() {

    }

    //region Getters and Setters
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
// endregion


    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                "\ndescripcion='" + descripcion + '\'' +
                "\nfechaEntrega=" + fechaEntrega +
                "\nentregada=" + entregada ;

    }

    /**
     *
     * @return
     * @throws JSONException
     */

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Id",id);
        jsonObject.put("Descripcion Tarea",descripcion);
        jsonObject.put("Fecha entrega", fechaEntrega);
        jsonObject.put(" Entregada ", entregada);
        return jsonObject;
    }


    @Override
    public void fromJsonObject(JSONObject jsonObject) {
        try {
            this.descripcion=jsonObject.getString("Descripcion Tarea");
            this.entregada=jsonObject.getBoolean("Fecha entrega");
            this.id=jsonObject.getInt("Id");
            String  fecha = jsonObject.getString("Fecha entrega");
            SimpleDateFormat Dfecha = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaDate = null;
            try {
                fechaDate = Dfecha.parse(fecha);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            this.fechaEntrega = fechaDate;
        }catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
}
