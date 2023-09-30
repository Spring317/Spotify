package vn.edu.usth.spotify;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import vn.edu.usth.spotify.MusicActivity;

public class Authenticate extends AppCompatActivity {

    private SharedPreferences.Editor editor;
    private SharedPreferences msharedPreferences;
    private RequestQueue Queue;
    Fragment fragment = null;
    private static final int REQUEST_CODE = 1337;

    private static final String CLIENT_ID = "a20d64ca1933453ca9c626261564b4d1";
    private static final String REDIRECT_URI = "https://localhost:3107/callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginActivityAuthentication();
        msharedPreferences = getSharedPreferences("SPOTIFY", 0);
        Queue = Volley.newRequestQueue(this);
    }

    public void LoginActivityAuthentication() {
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"streaming user-read-private user-read-email user-read-playback-state user-modify-playback-state"});
        AuthorizationRequest request = builder.build();
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                case TOKEN:
                    editor = getSharedPreferences("SPOTIFY", 0).edit();
                    editor.putString("token", response.getAccessToken());
                    Log.d("START", "This the auth token");
                    editor.apply();
                    break;
                case ERROR:
                    Log.d("ERROR", "Auth error: " + response.getError());
                    break;
                default:
            }
        }
    }

    public void waitForUserInfo() {
        UserService userService = new UserService(Queue, msharedPreferences);
        userService.get(() -> {
            User user = userService.getUser();
            editor = getSharedPreferences("SPOTIFY", 0).edit();

            editor.putString("userid", user.id);
            editor.putString("name", user.display_name);

            editor.commit();
            startMusicActivity();
        });
    }

    private void startMusicActivity() {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

}
