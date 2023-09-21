package vn.edu.usth.spotify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

public class MusicActivity extends AppCompatActivity {
    private static final String TAG = "Spotify says: ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Log.i(TAG, "onCreate: Success");

        LinearLayout container = findViewById(R.id.container);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = container.findViewById(R.id.view_pager);

        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.setOnTouchListener((v, event) -> true);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.selector_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.selector_search);
        tabLayout.getTabAt(2).setIcon(R.drawable.selector_library);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.container, MediaPlayer.class, null)
//                    .commit();
//        }
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