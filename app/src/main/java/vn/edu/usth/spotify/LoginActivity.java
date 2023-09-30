package vn.edu.usth.spotify;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onRequestTokenClicked(View view) {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }
}