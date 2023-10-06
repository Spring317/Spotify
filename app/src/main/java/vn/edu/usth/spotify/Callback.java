package vn.edu.usth.spotify;

import org.json.JSONException;
import org.json.JSONObject;

public interface Callback {
    void onAPICallComplete(JSONObject jsonObject) throws JSONException;
}