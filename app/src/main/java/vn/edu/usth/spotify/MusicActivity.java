package vn.edu.usth.spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MusicActivity extends AppCompatActivity {
    private static final String TAG = "Spotify says: ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Log.i(TAG, "onCreate: Success");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.container, Current_Song.class, null)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        Log.i(TAG, "onStop: Stop");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy: Destroy");
    }

}