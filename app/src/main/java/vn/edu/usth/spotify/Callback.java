package vn.edu.usth.spotify;

import org.json.JSONObject;

public interface Callback {
    void onAPICallComplete(JSONObject jsonObject);
}