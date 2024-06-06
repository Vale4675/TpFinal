package Sistema;

import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class Aviso implements Serializable, I_Convertir_JsonObject, I_From_JsonObect {
    private Date fecha;
    private String mensaje;

    public Aviso(Date fecha, String mensaje) {
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

    public Aviso() {
    }

    public Aviso(String mensaje) {
        this.mensaje = mensaje;
        this.fecha = new Date();
    }
    //region Getters and Setters
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
//endregion

    @Override
    public String toString() {
        return "Aviso{" +
                "fecha=" + fecha +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Fecha",fecha);
        jsonObject.put("Mensaje",mensaje);
        return jsonObject;
    }

    @Override
    public void fromJsonObject(JSONObject jsonObject) {

        try {
            //this.fecha=jsonObject.getJSONObject("fecha");
            this.mensaje=jsonObject.getString("mensaje");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
