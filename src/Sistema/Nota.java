package Sistema;

import Interfaz.I_Convertir_JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Nota implements Serializable, I_Convertir_JsonObject {
    private double nota;
    private String comentario;

    public Nota(double nota, String comentario) {
        this.nota = nota;
        this.comentario = comentario;
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
        return  "\nNota \n" +
                "\nnota " + nota +
                "\ncomentario " + comentario ;
    }

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Nota", nota);
        jsonObject.put("Comentario",comentario);
        return jsonObject;
    }
}

