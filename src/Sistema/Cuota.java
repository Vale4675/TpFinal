package Sistema;

import Interfaz.I_Convertir_JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Cuota implements Serializable, I_Convertir_JsonObject {
    private int comprobante;
    private static int contadorComprobante = 1;
    private double importe;
    private boolean pagado;

    public Cuota(int comprobante, double importe, boolean pagado) {
        this.comprobante = comprobante;
        this.importe = importe;
        this.pagado = pagado;
    }
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


    @Override
    public String toString() {
        return  "\nCuota" +
                "\n comprobante ='" + comprobante + '\'' +
                "\n importe=" + importe +
                "\n pagado=" + pagado ;
    }

    /**
     *
     * @return
     * @throws JSONException
     */

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Comprobante", comprobante);
        jsonObject.put("Importe", importe);
        jsonObject.put("Pagado", pagado);
        return jsonObject;
    }
}
