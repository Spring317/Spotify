package vn.edu.usth.spotify;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class LoginActivity extends AppCompatActivity {
    
    private final static String TAG = "LoginActivity";

    private static final int REQUEST_CODE = 1337;

    private static final String CLIENT_ID = "484acfe42c7e47a7af199d2af5953628";

    private static final String REDIRECT_URI = "http://localhost:8888/callback";

    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate: created");

        setContentView(R.layout.activity_login);

        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this,REQUEST_CODE ,request);
    }

    // Func for login (currently by real Spotify)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            Log.i("Connect", "onActivityResult: Success");
            Log.i("Connect", "onActivityResult: ResultCode" + resultCode);
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    this.setAccessToken(response.getAccessToken());
                    Log.i("Connect", "AccessToken: " + response.getAccessToken());

                    Intent intent1 = new Intent(this, MusicActivity.class);
                    intent1.putExtra("accessToken", accessToken);
                    startActivity(intent1);
                    finish();
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Log.i("Connect", "onActivityResult: Error");
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        };
    }

    // Getter and Setter for access token
    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy: destroyed");
    }
}