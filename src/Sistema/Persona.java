package Sistema;

import Interfaz.I_Convertir_JsonObject;
import Interfaz.I_From_JsonObect;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public abstract class Persona implements Serializable, I_Convertir_JsonObject,I_From_JsonObect {
    private String nombre;
    private String apellido;
    private String mail;

    public Persona(String nombre, String apellido, String mail) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
    }


//region Getters and Setters
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
// endregion


    public Persona() {
    }

    @Override
    public boolean equals(Object o) {
        boolean esIgual = false;
        if (o != null) {
            if (o instanceof Persona persona) {
                if (this.apellido.equals(persona.apellido)) {
                    esIgual = true;
                }
            }
        }
        return esIgual;
    }

    @Override
    public JSONObject convertirJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Nombre", nombre);
        jsonObject.put("Apellido",apellido);
        jsonObject.put("mail", mail);
        return jsonObject;
    }

    @Override
    public void fromJsonObject(JSONObject jsonObject) {
        try {
            this.nombre = jsonObject.getString("Nombre");
            this.apellido = jsonObject.getString("Apellido");
            this.mail = jsonObject.getString("mail");
            this.setNombre(nombre);
            this.setApellido(apellido);
            this.setMail(mail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Apellido: ").append(apellido).append("\n");
        sb.append("Mail: ").append(mail).append("\n");
        return sb.toString();
    }
}
