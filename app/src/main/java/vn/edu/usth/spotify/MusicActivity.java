package vn.edu.usth.spotify;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.palette.graphics.Palette;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MusicActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "a20d64ca1933453ca9c626261564b4d1";

    private static final String REDIRECT_URI = "http://localhost:8888/callback";

    private String accessToken;
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

        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);
            this.setAccessToken(response.getAccessToken());
            Log.i("Connect", "onActivityResult: Success");
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    Log.i("Connect", "Token" + response.getAccessToken());
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



//        RequestQueue requestQueue;
//
//// Instantiate the cache
//        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//
//// Set up the network to use HttpURLConnection as the HTTP client.
//        Network network = new BasicNetwork(new HurlStack());
//
//// Instantiate the RequestQueue with the cache and network.
//        requestQueue = new RequestQueue(cache, network);
//
//// Start the queue
//        requestQueue.start();
//        String url = "https://accounts.spotify.com/api/token";
//        Uri uri = Uri.parse(url);
//        String code = uri.getQueryParameter("code");
//        StringRequest tokenRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String token = jsonObject.getString("access_token");
//                            Log.i("Connect", "onResponse:" + token);
//                            int expired_in = jsonObject.getInt("expires_in");
//                            Log.i("Connect", "onResponse: " + expired_in);
//                        } catch (Exception e) {
//                            Log.i("Connect", "onResponse: Request Failed");
//                        }
//                        Log.i("Connect", "onResponse: Request Successful");
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Log.i("Connect", "onErrorResponse: Request Failed");
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("grant_type", "authorization_code");
//                params.put("code",code);
//                params.put("redirect_uri", REDIRECT_URI);
//                params.put("client_id", CLIENT_ID);
//                params.put("client_secret", "89f6fd7934d54adfb60c7b61f83988e4");
//                return params;
//            }
//        };
//        Volley.newRequestQueue(this).add(tokenRequest);
// Formulate the request and handle the response.

//        JsonObjectRequest jasonobjectrequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i("Connect", "onResponse: Request Successful");
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("Connect", "onErrorResponse: Request Failed");
//                    }
//                });
//
//// Add the request to the RequestQueue.
//      requestQueue.add(jasonobjectrequest);
//    }

    }


    //create getter and setter for access token

    public String getAccessToken(){
        return accessToken;
    }
    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
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
