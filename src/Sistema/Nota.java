package Sistema;

import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Nota implements Serializable, I_Convertir_JsonObject, I_From_JsonObect {
    private double nota;
    private String comentario;

    public Nota(double nota, String comentario) {
        this.nota = nota;
        this.comentario = comentario;
    }

    public Nota() {
    }


    //region Getters and Setters
    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
//endregion

    @Override
    public String toString() {
        return "Nota{" +
                "nota=" + nota +
                ", comentario='" + comentario + '\'' +
                '}';
    }

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Nota", nota);
        jsonObject.put("Comentario",comentario);
        return jsonObject;
    }


    @Override
    public void fromJsonObject(JSONObject jsonObject) {
        try {
            this.nota = jsonObject.getDouble("nota");
            this.comentario = jsonObject.getString("comentario");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

