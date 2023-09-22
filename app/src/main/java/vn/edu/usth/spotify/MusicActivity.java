package vn.edu.usth.spotify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.palette.graphics.Palette;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicActivity extends AppCompatActivity {

    ViewPager viewPager;

    private final List<Fragment> fragments = new ArrayList<Fragment>();

    private final List<Fragment> hide_fragments = new ArrayList<Fragment>();

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

    public GradientDrawable getGradientDrawable(Bitmap picBit) {
        // Use Palette to extract dominant colors
        Palette palette = Palette.from(picBit).generate();

        // Get dominant color
        int defaultColor = Color.BLACK;
        int dominantColor = palette.getDominantColor(defaultColor);

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {dominantColor, defaultColor}
        );

        gradientDrawable.setCornerRadius(0f);

        return gradientDrawable;
    }

    public int getDominantColor(Bitmap picBit) {
        // Use Palette to extract dominant colors
        Palette palette = Palette.from(picBit).generate();

        // Get dominant color
        int defaultColor = Color.BLACK;

        return palette.getDominantColor(defaultColor);
    }

    public void popupFragment(Fragment fragment) {
        viewPager.setVisibility(View.GONE);
        Log.i(TAG, "ViewPager hidden");

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.container, fragment, null)
                .commit();

        try {
            // Add parents of popup fragments into list for easy hiding
            hide_fragments.addAll(fragments);
            Log.i(TAG, "popupFragment: parent fragment added to hide list");
        } catch (Exception e) {
            Log.i(TAG, "popupFragment: No parent fragment found");
        }


        // Add all popup fragments into list for easy killing
        fragments.add(fragment);

    }

    public void killAllFragments() {
        for (int i = 0; i < fragments.size(); i++){
            getSupportFragmentManager().beginTransaction().remove(fragments.get(i)).commit();
        }

        Log.i(TAG, "All fragments has been killed");
    }

    public void hideFragmentsAndTabLayout() {
        for (int i = 0; i < hide_fragments.size(); i++){
            getSupportFragmentManager().beginTransaction().hide(hide_fragments.get(i)).commit();
        }
        findViewById(R.id.tab_layout).setVisibility(View.GONE);

        Log.i(TAG, "All fragments and TabLayout has been hidden");
    }

    public void restoreFragmentsAndTabLayout() {
        for (int i = 0; i < hide_fragments.size(); i++){
            getSupportFragmentManager().beginTransaction().show(hide_fragments.get(i)).commit();
        }
        findViewById(R.id.tab_layout).setVisibility(View.VISIBLE);

        Log.i(TAG, "All fragments and TabLayout has been restored");
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