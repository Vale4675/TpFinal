package Sistema;

import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Cuota implements Serializable, I_Convertir_JsonObject, I_From_JsonObect {
    private int comprobante;
    private static int contadorComprobante = 1;
    private double importe;
    private boolean pagado;
    private Calendar fechaVencimiento;

    //region Getters and Setters

    public int getComprobante() {
        return comprobante;
    }

    public double getImporte() {
        return importe;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public void setComprobante(int comprobante) {
        this.comprobante = comprobante;
    }

    public static void setContadorComprobante(int contadorComprobante) {
        Cuota.contadorComprobante = contadorComprobante;
    }
//endregion


    public Cuota(double importe, Calendar fechaVencimiento) {
        this.comprobante = contadorComprobante++;
        this.importe = importe;
        this.fechaVencimiento = fechaVencimiento;
        this.pagado=false;
    }

    public Cuota() {
    }

    /**
     *Verifica si esta vencida
     * @return
     */

    public boolean estaVencida()
    {
        Calendar hoy= Calendar.getInstance();
        return !pagado && hoy.after(fechaVencimiento);
    }
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Cuota{" +
                "comprobante='" + comprobante + '\'' +
                ", importe=" + importe +
                ", pagado=" + pagado +
               " Fecha de Vecimiento: " + sdf.format(fechaVencimiento.getTime())+
                '}';
    }

    public static int getContadorComprobante() {
        return contadorComprobante;
    }

    public Calendar getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     *
     * @return
     * @throws JSONException
     */

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Comprobante", comprobante);
        jsonObject.put("Importe", importe);
        jsonObject.put("Pagado", pagado);
        jsonObject.put("FechadeVecimiento",sdf.format(fechaVencimiento.getTime()));
        return jsonObject;
    }


    @Override
    public void fromJsonObject(JSONObject jsonObject) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.comprobante= jsonObject.getInt("Comprobante");
            this.importe= jsonObject.getDouble("Importe");
            this.pagado= jsonObject.getBoolean("Pagado");
            this.fechaVencimiento = Calendar.getInstance();
            this.fechaVencimiento.setTime(sdf.parse(jsonObject.getString("FechadeVecimiento")));
        } catch (JSONException | ParseException e) {
           e.printStackTrace();
        }


    }
}
