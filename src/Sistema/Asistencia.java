package Sistema;

import Interfaz.I_Convertir_JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class Asistencia implements Serializable, I_Convertir_JsonObject {
    private Date fecha;
    private boolean presente;

    public Asistencia(Date fecha, boolean presente) {
        this.fecha = fecha;
        this.presente = presente;
    }
    //region Getters and Setters
    public Date getFecha() {
        return fecha;
    }

    public boolean isPresente() {
        return presente;
    }
//endregion
    @Override
    public String toString() {
        return "Asistencia{" +
                "fecha=" + fecha +
                ", presente=" + presente +
                '}';
    }

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Fecha", fecha);
        jsonObject.put("Presente",presente);
        return jsonObject;
    }
}

