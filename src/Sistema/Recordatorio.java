package Sistema;

import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import Sistema.Enum.TipoRecordatorio;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Recordatorio implements Serializable, I_Convertir_JsonObject, I_From_JsonObect {

    private Calendar fecha;
    private TipoRecordatorio tipo;
    private String detalle;

    public Recordatorio(Calendar fecha, TipoRecordatorio tipo, String detalle) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.detalle = detalle;

    }

    public Recordatorio() {
    }

    public Calendar getFecha() {
        return fecha;
    }

    public TipoRecordatorio getTipo() {
        return tipo;
    }

    public String getDetalle() {
        return detalle;
    }


    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaStr = sdf.format(fecha.getTime());
        return "Recordatorio{" +
                "fecha=" + fechaStr +
                ", tipo='" + tipo + '\'' +
                ", detalle='" + detalle + '\'' +
                '}';
    }

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Tipo", tipo);
        jsonObject.put("Detalle", detalle);
        jsonObject.put("fecha", sdf.format(fecha.getTime()));

        return jsonObject;
    }

    @Override
    public void fromJsonObject(JSONObject jsonObject) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.fecha = Calendar.getInstance();
            fecha.setTime(sdf.parse(jsonObject.getString("Fecha")));
            this.detalle = jsonObject.getString("Detalle");
            this.tipo = TipoRecordatorio.valueOf(jsonObject.getString("Tipo"));
        } catch (JSONException | ParseException e) {
            throw new RuntimeException(e);
        }


    }
}
