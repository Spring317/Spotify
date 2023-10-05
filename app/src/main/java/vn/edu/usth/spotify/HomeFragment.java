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

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("HomeFragment", "View Destroyed");
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
            musicActivity.makeAPICall("https://api.spotify.com/v1/albums/5jDZKqgoVRbob6A3omYTG5", new Callback() {
                @Override
                public void onAPICallComplete(JSONObject jsonObject) {
                    if (jsonObject != null) {
                        // Log the received JSON object for debugging purposes
                        Log.i("jsonObject", jsonObject.toString());
                        try {
                            // Extract relevant information from the JSON object
                            String playlistName = jsonObject.getString("name");
                            JSONArray imageArray = jsonObject.getJSONArray("images");
                            String imageURL = imageArray.getJSONObject(0).getString("url");

                            // Log the extracted playlist name and image URL
                            Log.i("playlistName", playlistName);
                            Log.i("imageUrl", imageURL);

                            // Initialize and configure RecyclerViews for different playlists
                            // (e.g., "tSE", "featured charts", "trending now") with the extracted data.

                            // tSE RecyclerView
                            RecyclerView tSERecyclerView = view.findViewById(R.id.tSERecyclerView);
                            tSERecyclerView.setHasFixedSize(true);
                            tSERecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

                            List<ItemPlaylistTitle> tSEList = new ArrayList<>();

                            // Add items to the tSEList based on the extracted data
                            tSEList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            tSEList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            tSEList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            tSEList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            tSEList.add(new ItemPlaylistTitle(imageURL, playlistName));

                            // Create and set an adapter for the tSE RecyclerView
                            ItemPlaylistTitleAdapter tSEAdapter = new ItemPlaylistTitleAdapter(requireContext(), tSEList);
                            tSERecyclerView.setAdapter(tSEAdapter);

                            // featureCharts RecyclerView
                            RecyclerView featuredChartsRecyclerView = view.findViewById(R.id.featuredChartsRecyclerView);
                            featuredChartsRecyclerView.setHasFixedSize(true);
                            featuredChartsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

                            List<ItemPlaylistTitle> featuredChartsList = new ArrayList<>();

                            // Add items to the featuredChartsList based on the extracted data
                            featuredChartsList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            featuredChartsList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            featuredChartsList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            featuredChartsList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            featuredChartsList.add(new ItemPlaylistTitle(imageURL, playlistName));

                            // Create and set an adapter for the featuredCharts RecyclerView
                            ItemPlaylistTitleAdapter featuredChartsAdapter = new ItemPlaylistTitleAdapter(requireContext(), featuredChartsList);
                            featuredChartsRecyclerView.setAdapter(featuredChartsAdapter);

                            // trendingNow RecyclerView
                            RecyclerView trendingNowRecyclerView = view.findViewById(R.id.trendingNowRecyclerView);
                            trendingNowRecyclerView.setHasFixedSize(true);
                            trendingNowRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

                            List<ItemPlaylistTitle> trendingNowList = new ArrayList<>();

                            // Add items to the featuredChartsList based on the extracted data
                            trendingNowList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            trendingNowList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            trendingNowList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            trendingNowList.add(new ItemPlaylistTitle(imageURL, playlistName));
                            trendingNowList.add(new ItemPlaylistTitle(imageURL, playlistName));

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

//        RecyclerView tSERecyclerView = view.findViewById(R.id.tSERecyclerView);
//        tSERecyclerView.setHasFixedSize(true);
//        tSERecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
//
//        List<ItemPlaylistTitle> tSEList = new ArrayList<>();
//
//        tSEList.add(new ItemPlaylistTitle(R.drawable.playlist_1, R.string.song_info_1));
//        tSEList.add(new ItemPlaylistTitle(R.drawable.playlist_2, R.string.song_info_2));
//        tSEList.add(new ItemPlaylistTitle(R.drawable.playlist_3, R.string.song_info_3));
//        tSEList.add(new ItemPlaylistTitle(R.drawable.playlist_4, R.string.song_info_4));
//        tSEList.add(new ItemPlaylistTitle(R.drawable.playlist_5, R.string.song_info_5));
//        tSEList.add(new ItemPlaylistTitle(R.drawable.playlist_6, R.string.song_info_6));
//        tSEList.add(new ItemPlaylistTitle(R.drawable.playlist_7, R.string.song_info_7));
//        tSEList.add(new ItemPlaylistTitle(R.drawable.playlist_8, R.string.song_info_8));
//        tSEList.add(new ItemPlaylistTitle(R.drawable.playlist_9, R.string.song_info_9));
//
//        ItemPlaylistTitleAdapter tSEAdapter = new ItemPlaylistTitleAdapter(requireContext(), tSEList);
//
//        tSERecyclerView.setAdapter(tSEAdapter);

        // Feature Charts RecyclerView
        RecyclerView featuredChartsRecyclerView = view.findViewById(R.id.featuredChartsRecyclerView);
        featuredChartsRecyclerView.setHasFixedSize(true);
        featuredChartsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        List<ItemPlaylistTitle> featuredChartsList = new ArrayList<>();

//        featuredChartsList.add(new ItemPlaylistTitle(R.drawable.playlist_10, R.string.song_info_10));
//        featuredChartsList.add(new ItemPlaylistTitle(R.drawable.playlist_11, R.string.song_info_10));
//        featuredChartsList.add(new ItemPlaylistTitle(R.drawable.playlist_5, R.string.song_info_5));
//        featuredChartsList.add(new ItemPlaylistTitle(R.drawable.playlist_12, R.string.song_info_5));
//        featuredChartsList.add(new ItemPlaylistTitle(R.drawable.playlist_13, R.string.song_info_5));
//        featuredChartsList.add(new ItemPlaylistTitle(R.drawable.playlist_14, R.string.song_info_5));

        // Demo
        ItemPlaylistTitleAdapter featuredChartsAdapter = new ItemPlaylistTitleAdapter(requireContext(), featuredChartsList);

        featuredChartsRecyclerView.setAdapter(featuredChartsAdapter);

        // Trending Now RecyclerView
        RecyclerView trendingNowRecyclerView = view.findViewById(R.id.trendingNowRecyclerView);
        trendingNowRecyclerView.setHasFixedSize(true);
        trendingNowRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        List<ItemPlaylistTitle> trendingNowList = new ArrayList<>();

//        trendingNowList.add(new ItemPlaylistTitle(R.drawable.playlist_15, R.string.song_info_11));
//        trendingNowList.add(new ItemPlaylistTitle(R.drawable.playlist_16, R.string.song_info_12));
//        trendingNowList.add(new ItemPlaylistTitle(R.drawable.playlist_17, R.string.song_info_13));
//        trendingNowList.add(new ItemPlaylistTitle(R.drawable.playlist_18, R.string.song_info_14));
//        trendingNowList.add(new ItemPlaylistTitle(R.drawable.playlist_19, R.string.song_info_15));
//        trendingNowList.add(new ItemPlaylistTitle(R.drawable.playlist_20, R.string.song_info_16));
//        trendingNowList.add(new ItemPlaylistTitle(R.drawable.playlist_21, R.string.song_info_17));
//        trendingNowList.add(new ItemPlaylistTitle(R.drawable.playlist_22, R.string.song_info_18));
//        trendingNowList.add(new ItemPlaylistTitle(R.drawable.playlist_23, R.string.song_info_19));
//        trendingNowList.add(new ItemPlaylistTitle(R.drawable.playlist_24, R.string.song_info_20));

        // Demo
        ItemPlaylistTitleAdapter trendingNowAdapter = new ItemPlaylistTitleAdapter(requireContext(), trendingNowList);

        trendingNowRecyclerView.setAdapter(trendingNowAdapter);

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