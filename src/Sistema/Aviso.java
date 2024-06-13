package Sistema;

import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            String  fecha = jsonObject.getString("Fecha");
            SimpleDateFormat Dfecha = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaDate = null;
            fechaDate = Dfecha.parse(fecha);
            this.fecha = fechaDate;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }


    }
}
