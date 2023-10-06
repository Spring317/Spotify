package vn.edu.usth.spotify;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This interface defines a callback mechanism to handle the completion of an API call.
 */
public interface Callback {
    /**
     * Called when an API call is complete and returns a JSON object as a result.
     *
     * @param jsonObject The JSON object returned by the API call.
     */
    void onAPICallComplete(JSONObject jsonObject) throws JSONException;
}

