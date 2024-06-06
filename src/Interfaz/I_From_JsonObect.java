package Interfaz;

import org.json.JSONArray;
import org.json.JSONObject;

public interface I_To_JsonObect<T> {

    T  toJsonObject (JSONObject jsonObject);
}
