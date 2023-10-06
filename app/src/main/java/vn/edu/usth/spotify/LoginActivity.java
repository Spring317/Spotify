package vn.edu.usth.spotify;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.spotify.android.appremote.api.error.SpotifyDisconnectedException;
import com.spotify.android.appremote.api.error.AuthenticationFailedException;
import com.spotify.android.appremote.api.error.SpotifyDisconnectedException;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.types.Album;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.Artist;
import com.spotify.protocol.types.Repeat;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.error.*;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class LoginActivity extends AppCompatActivity {
    
    private final static String TAG = "LoginActivity";
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "a20d64ca1933453ca9c626261564b4d1";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private String accessToken;
    private SpotifyAppRemote mSpotifyAppRemote;
    private Button mButton;
    private TextView mStatusText;

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
        mButton.setOnClickListener(v -> appremote());
    }

    // Spotify app remote goes here
    private void appremote() {
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();
        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {
                @Override
                public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                    mSpotifyAppRemote = spotifyAppRemote;
                    Log.d("MainActivity", "Connected! Yay!");
                    // Now you can start interacting with App Remote
                    // connected();
                }
                @Override
                public void onFailure(Throwable throwable) {
                    Log.e("Failed to connect", throwable.getMessage(), throwable);
                }
          }
            );
    }

    // Play song
    public void playSong(String uri) {
        uri = "spotify:track:6R6ihJhRbgu7JxJKIbW57w";
        if (mSpotifyAppRemote != null) {
            try {
            mSpotifyAppRemote.getPlayerApi().play(uri);
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        }

        mSpotifyAppRemote.getPlayerApi().play(uri);
    }

    // Pause song
    public void pauseSong() {
        mSpotifyAppRemote.getPlayerApi().pause();
    }

    // Resume song
    public void resumeSong() {
        mSpotifyAppRemote.getPlayerApi().resume();
    }

    // Skip to next song
    public void skipNext() {
        mSpotifyAppRemote.getPlayerApi().skipNext();
    }

    // Skip to previous song
    public void skipPrevious() {
        mSpotifyAppRemote.getPlayerApi().skipPrevious();
    }

    // Repeat song
    public void repeat() {
        mSpotifyAppRemote.getPlayerApi().toggleRepeat();
    }

    // Shuffle song
    public void shuffle(boolean shuffle) {
        mSpotifyAppRemote.getPlayerApi().setShuffle(shuffle);
    }

    // Seek to position
    public void seekTo(int position) {
        mSpotifyAppRemote.getPlayerApi().seekTo(position);
    }

    // Subscribe to Player State(get current track)
    public void subscribeToPlayerState() {
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                    }
                });
    }

    // Subscribe to Player Context(get current context)
    public void subscribeToPlayerContext() {
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerContext()
                .setEventCallback(playerContext -> {
                    final String title = playerContext.title;
                    if (title != null) {
                        Log.d("MainActivity", title);
                    }
                });
    }

    // Subscribe to Player State(get current playback speed)
    public void getPlaybackSpeed() {
        mSpotifyAppRemote.getPlayerApi()
                .getPlayerState()
                .setResultCallback(playerState -> {
                    final float playbackSpeed = playerState.playbackSpeed;
                    Log.d("MainActivity", String.valueOf(playbackSpeed));
                });
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
        }
    }

    // Getter and Setter for access token
    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
        Log.i(TAG, "onDestroy: destroyed");
    }


}