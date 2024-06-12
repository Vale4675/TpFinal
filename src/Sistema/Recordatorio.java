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
    private int idAlumno;

    public Recordatorio(Calendar fecha, TipoRecordatorio tipo, String detalle, int id) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.detalle = detalle;
        this.idAlumno = id;
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

    public int getId() {
        return idAlumno;
    }


    @Override
    public String toString() {
        return "Recordatorio{" +
                "fecha=" + fecha +
                ", tipo='" + tipo + '\'' +
                ", detalle='" + detalle + '\'' +
                ", id=" + idAlumno +
                '}';
    }

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("Tipo", tipo);
        jsonObject.put("Detalle",detalle);
        jsonObject.put("fecha",sdf.format(fecha.getTime()));
        jsonObject.put("idAlumno",idAlumno);

        return jsonObject;
    }

    @Override
    public void fromJsonObject(JSONObject jsonObject) {
        try { SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar fecha = Calendar.getInstance();
            fecha.setTime(sdf.parse(jsonObject.getString("Fecha")));
            this.idAlumno = jsonObject.getInt("idAlumno");
            this.detalle = jsonObject.getString("Detalle");
            TipoRecordatorio tipoRecordatorio = TipoRecordatorio.valueOf(jsonObject.getString("Tipo"));
        } catch (JSONException | ParseException e) {
            throw new RuntimeException(e);
        }


    }
}
