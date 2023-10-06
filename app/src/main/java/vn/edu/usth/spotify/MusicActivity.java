package vn.edu.usth.spotify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.palette.graphics.Palette;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import com.google.android.material.tabs.TabLayout;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MusicActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "484acfe42c7e47a7af199d2af5953628";

    private static final String REDIRECT_URI = "http://localhost:8888/callback";

    private String accessToken;
    private JSONObject jsonObject;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    ViewPager viewPager;
   
    private final List<Fragment> frags_2b_kill = new ArrayList<Fragment>();

    private final List<Fragment> frags_2b_hide = new ArrayList<Fragment>();

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

        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this,REQUEST_CODE ,request);

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
    public String getAccessToken(){
        return accessToken;
    }
    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    // Func to request data
    private class APICallTask extends AsyncTask<String, Void, JSONObject> {

        private vn.edu.usth.spotify.Callback callback;

        public APICallTask(vn.edu.usth.spotify.Callback callback) {
            this.callback = callback;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String url = params[0];
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();

            Log.i("APICall", "APICall: Started");

            try {
                Response response = mOkHttpClient.newCall(request).execute();

                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseBody);
                    Log.i("APICall", "onResponse: Success having jsonObject " + jsonObject.toString());
                    return jsonObject;

                } else {
                    Log.e("APICall", "API call failed");
                }
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                // getJSONObject(jsonObject);
                try {
                    callback.onAPICallComplete(jsonObject);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Log.i("APICall", "Delivered jsonObject");
            } else {
                Log.i("APICall", "API call failed or JSON parsing error");
            }
        }
    }

    public void makeAPICall(String url, vn.edu.usth.spotify.Callback callback) {
        APICallTask task = new APICallTask(callback);
        task.execute(url);
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
            // Add parents of popup frags_2b_kill into list for easy hiding
            frags_2b_hide.addAll(frags_2b_kill);
            Log.i(TAG, "popupFragment: parent fragment added to hide list");
        } catch (Exception e) {
            Log.i(TAG, "popupFragment: No parent fragment found");
        }


        // Add all popup frags_2b_kill into list for easy killing
        frags_2b_kill.add(fragment);

    }

    public void killAllFragments() {
        for (int i = 0; i < frags_2b_kill.size(); i++){
            getSupportFragmentManager().beginTransaction().remove(frags_2b_kill.get(i)).commit();
        }

        Log.i(TAG, "All frags_2b_kill has been killed");
    }

    public void hideFragmentsAndTabLayout() {
        for (int i = 0; i < frags_2b_hide.size(); i++){
            getSupportFragmentManager().beginTransaction().hide(frags_2b_hide.get(i)).commit();
        }
        findViewById(R.id.tab_layout).setVisibility(View.GONE);

        Log.i(TAG, "All frags_2b_hide and TabLayout has been hidden");
    }

    public void restoreFragmentsAndTabLayout() {
        for (int i = 0; i < frags_2b_hide.size(); i++){
            getSupportFragmentManager().beginTransaction().show(frags_2b_hide.get(i)).commit();
        }
        findViewById(R.id.tab_layout).setVisibility(View.VISIBLE);

        Log.i(TAG, "All frags_2b_kill and TabLayout has been restored");
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
