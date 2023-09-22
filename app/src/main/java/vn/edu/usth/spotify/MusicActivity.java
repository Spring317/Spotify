package vn.edu.usth.spotify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicActivity extends AppCompatActivity {

    ViewPager viewPager;

    private final List<Fragment> fragments = new ArrayList<Fragment>();

    private static final String TAG = "Spotify";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Log.i(TAG, "onCreate: Success");

        RelativeLayout container = findViewById(R.id.container);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        viewPager = container.findViewById(R.id.view_pager);
        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                killAllFragments();
                restoreViewPager();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.selector_home);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.selector_search);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.selector_library);
    }

    public void popupFragment(Fragment fragment) {
        viewPager.setVisibility(View.GONE);
        Log.i(TAG, "ViewPager hidden");

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.container, fragment, null)
                .commit();

        // Add all popup fragments into list for easy kill
        fragments.add(fragment);
    }

    public void killAllFragments() {
        for (int i = 0; i < fragments.size(); i++){
            getSupportFragmentManager().beginTransaction().remove(fragments.get(i)).commit();
        }

        Log.i(TAG, "All fragments has been killed");
    }

    public void restoreViewPager() {
        viewPager.setVisibility(View.VISIBLE);
        Log.i(TAG, "ViewPager restored");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: Started");

    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "onPause: Paused");
    }
    @Override
    protected void  onResume(){
        super.onResume();
        Log.i(TAG, "onResume: Resumed");
    }
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "onStop: Stopped");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy: Destroyed");
    }

}