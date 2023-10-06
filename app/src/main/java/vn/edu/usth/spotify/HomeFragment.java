package vn.edu.usth.spotify;

import android.content.ClipData;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Demo
public class HomeFragment extends Fragment {

    private final String TAG = "HomeFragment";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "View Destroyed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the TextView by its ID
        TextView greetingTextView = view.findViewById(R.id.greetingTextView);

        // Set the greeting in the TextView
        String greeting = getGreeting();
        greetingTextView.setText(greeting);

        // Obtain a reference to the parent MusicActivity by calling getActivity() and check if it's not null
        MusicActivity musicActivity = (MusicActivity) getActivity();
        if (musicActivity != null) {
            // Initiate an API call to retrieve information about a Spotify album using the makeAPICall method
            // and provide a callback to handle the API call result.
            musicActivity.makeAPICall("https://api.spotify.com/v1/browse/featured-playlists?limit=15", new Callback() {
                @Override
                public void onAPICallComplete(JSONObject jsonObject) {
                    if (jsonObject != null) {
                        // Log the received JSON object for debugging purposes
                        Log.i(TAG, "New Releases: " + jsonObject);
                        try {

                            // Initialize and configure RecyclerViews for different playlists
                            // (e.g., "tSE", "featured charts", "trending now") with the extracted data.

                            // tSE RecyclerView
                            RecyclerView tSERecyclerView = view.findViewById(R.id.tSERecyclerView);
                            tSERecyclerView.setHasFixedSize(true);
                            tSERecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

                            List<ItemPlaylistTitle> tSEList = new ArrayList<>();

                            JSONObject albumsArray = jsonObject.getJSONObject("playlists");
                            JSONArray itemArray = albumsArray.getJSONArray("items");

                            int numOfPlaylist = itemArray.length() / 3;

                            for (int i = 0; i < numOfPlaylist; i++) {
                                JSONObject itemObj = itemArray.getJSONObject(i);

                                JSONArray imageArray = itemObj.getJSONArray("images");
                                String imageURL = imageArray.getJSONObject(0).getString("url");
                                Log.i(TAG, "imageURL: " + imageURL);

                                String playlistName = itemObj.getString("name");
                                Log.i(TAG, "playlistName: "+ playlistName);

                                String playlistURL = itemObj.getString("href");
                                Log.i(TAG, "playlistURL: "+ playlistURL);

                                String type = itemObj.getString("type");
                                Log.i(TAG, "type: "+ type);

                                // Add items to the tSEList based on the extracted data
                                tSEList.add(new ItemPlaylistTitle(imageURL, playlistName, playlistURL, type));
                            }

                            // featureCharts RecyclerView
                            RecyclerView featuredChartsRecyclerView = view.findViewById(R.id.featuredChartsRecyclerView);
                            featuredChartsRecyclerView.setHasFixedSize(true);
                            featuredChartsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

                            List<ItemPlaylistTitle> featuredChartsList = new ArrayList<>();

                            for (int i = numOfPlaylist; i < numOfPlaylist * 2; i++) {
                                JSONObject itemObj = itemArray.getJSONObject(i);

                                JSONArray imageArray = itemObj.getJSONArray("images");
                                String imageURL = imageArray.getJSONObject(0).getString("url");
                                Log.i(TAG, "imageURL: " + imageURL);

                                String playlistName = itemObj.getString("name");
                                Log.i(TAG, "playlistName: "+ playlistName);

                                String playlistURL = itemObj.getString("href");
                                Log.i(TAG, "playlistURL: "+ playlistURL);

                                String type = itemObj.getString("type");
                                Log.i(TAG, "type: "+ type);

                                // Add items to the tSEList based on the extracted data
                                featuredChartsList.add(new ItemPlaylistTitle(imageURL, playlistName, playlistURL, type));
                            }

                            // trendingNow RecyclerView
                            RecyclerView trendingNowRecyclerView = view.findViewById(R.id.trendingNowRecyclerView);
                            trendingNowRecyclerView.setHasFixedSize(true);
                            trendingNowRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

                            List<ItemPlaylistTitle> trendingNowList = new ArrayList<>();

                            for (int i = numOfPlaylist * 2; i < numOfPlaylist * 3; i++) {
                                JSONObject itemObj = itemArray.getJSONObject(i);

                                JSONArray imageArray = itemObj.getJSONArray("images");
                                String imageURL = imageArray.getJSONObject(0).getString("url");
                                Log.i(TAG, "imageURL: " + imageURL);

                                String playlistName = itemObj.getString("name");
                                Log.i(TAG, "playlistName: "+ playlistName);

                                String playlistURL = itemObj.getString("href");
                                Log.i(TAG, "playlistURL: "+ playlistURL);

                                String type = itemObj.getString("type");
                                Log.i(TAG, "type: "+ type);

                                // Add items to the tSEList based on the extracted data
                                trendingNowList.add(new ItemPlaylistTitle(imageURL, playlistName, playlistURL, type));
                            }

                            Log.i(TAG, "itemArray: " + itemArray);
                            Log.i(TAG, "albumsArray: " + albumsArray);

                            // Create and set an adapter for the tSE RecyclerView
                            ItemPlaylistTitleAdapter tSEAdapter = new ItemPlaylistTitleAdapter(requireContext(), tSEList);
                            tSERecyclerView.setAdapter(tSEAdapter);

                            // Create and set an adapter for the featuredCharts RecyclerView
                            ItemPlaylistTitleAdapter featuredChartsAdapter = new ItemPlaylistTitleAdapter(requireContext(), featuredChartsList);
                            featuredChartsRecyclerView.setAdapter(featuredChartsAdapter);

                            // Create and set an adapter for the trendingNow RecyclerView
                            ItemPlaylistTitleAdapter trendingNowAdapter = new ItemPlaylistTitleAdapter(requireContext(), trendingNowList);
                            trendingNowRecyclerView.setAdapter(trendingNowAdapter);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        // Handle the case where the received JSON object is null
                        Log.e("jsonObject", "JSONObject is null");
                    }
                }
            });
        }

        return view;
    }

    private String getGreeting() {
        // Get the current time
       Calendar calendar = Calendar.getInstance();
       int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        // Determine the appropriate greeting based on the time
       String greeting;
        if (hourOfDay >= 0 && hourOfDay < 12) {
            greeting = "Good Morning";
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }

        return greeting;
    }


}