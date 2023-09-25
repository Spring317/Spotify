package vn.edu.usth.spotify;

import androidx.appcompat.app.AppCompatActivity;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.spotify.android.appremote.api.UserApi;
import com.spotify.android.appremote.api.ImagesApi;
import com.spotify.protocol.types.ImageUri;
import com.spotify.android.appremote.api.ConnectApi;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.PlayerApi.StreamType;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.spotify.sdk.android.auth.LoginActivity;
import android.os.Bundle;
import android.util.Log;

public class MusicActivity extends AppCompatActivity {

    private static final String TAG = "Spotify says: ";
    private static final String CLIENT_ID = "95a1bf024ed54b6792c3b60e96d93c30";
    private static final String REDIRECT_URI = "http://localhost:6942/callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Log.i(TAG, "onCreate: Success");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.container, MediaPlayer.class, null)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

                    }
                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
        Log.i(TAG, "onStart: Start successful");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "onPause: Pausing");
    }
    @Override
    protected void  onResume(){
        super.onResume();
        Log.i(TAG, "onResume: Resuming");
    }
    protected void onStop(){
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
        Log.i(TAG, "onStop: Stop");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy: Destroy");
    }

    private void connected() {
        // Play a playlist
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MusicActivity", track.name + " by " + track.artist.name);
                    }
                });
    }


}