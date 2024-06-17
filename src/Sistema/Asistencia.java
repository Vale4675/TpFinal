package Sistema;

import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Asistencia implements Serializable, I_Convertir_JsonObject, I_From_JsonObect {
    private Date fecha;
    private boolean presente;

    public Asistencia(Date fecha, boolean presente) {
        this.fecha = fecha;
        this.presente = presente;
    }

    public Asistencia() {
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


    @Override
    public void fromJsonObject(JSONObject jsonObject) {

        try {
            String fecha = jsonObject.getString("Fecha") ;
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
            Date fechaDate =sdf.parse(fecha);
            this.fecha = fechaDate;

        } catch (JSONException | ParseException e) {
            throw new RuntimeException(e);
        }

    }
}

