package vn.edu.usth.spotify;

import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import android.content.SharedPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import vn.edu.usth.spotify.User;
public class UserService {
    private static final String ENDPOINT = "https://api.spotify.com/v1/me";
    private RequestQueue q;
    private SharedPreferences msharedPreferences;
    private User user;

    public UserService(RequestQueue queue, SharedPreferences sharedPreferences) {
        q = queue;
        msharedPreferences = sharedPreferences;
    }

    public User getUser() {
        return user;
    }

    public void get(final AsyncHandler callBack) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ENDPOINT, null, response -> {
            Gson gson = new Gson();
            user = gson.fromJson(response.toString(), User.class);
            callBack.finished();
        }, error -> get(() -> {

        })) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = msharedPreferences.getString("token", "");
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        q.add(jsonObjectRequest);
    }
}
