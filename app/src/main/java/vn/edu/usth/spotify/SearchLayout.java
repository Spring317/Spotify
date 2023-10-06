package vn.edu.usth.spotify;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchLayout extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
        MusicActivity musicActivity = (MusicActivity) getActivity();
        if (musicActivity !=null){
            musicActivity.restoreViewPager();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.search_layout, container, false);

        TextView Cancelbtn = view.findViewById(R.id.Cancelbtn);
        // Find the TextView by its ID
        Cancelbtn.setOnClickListener(new View.OnClickListener() {
            // Set the Cancel Button in SearchLayout
            @Override
            public void onClick(View view) {
                Log.i("SearchExtend", "Click on Cancel button");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(SearchLayout.this);
                fragmentTransaction.commit();
            }
        });

        MusicActivity musicActivity = (MusicActivity) getActivity();
        if(musicActivity != null) {
            // Initiate an API call to retrieve information about a Spotify album using the makeAPICall method
            // and provide a callback to handle the API call result.
            String apiUrl = "https://api.spotify.com/v1/search?q=t&type=track";
            musicActivity.makeAPICall(apiUrl, new Callback() {
                @Override
                public void onAPICallComplete(JSONObject jsonObject) {
                    if (jsonObject != null) {
                        Log.i("jsonObject", jsonObject.toString());
                        try {
                            // Extract relevant information from the JSON object
                            JSONObject tracks = jsonObject.getJSONObject("tracks");
                            JSONArray items = tracks.getJSONArray("items");

                            RecyclerView recyclerView2 = view.findViewById(R.id.searchlistrecyclerview);
                            recyclerView2.setHasFixedSize(true);
                            recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext()));
                            List<SearchLayoutData> datas2 = new ArrayList<>();

                            for (int i = 0; i < items.length(); i++) {
                                // Make many tracks appear
                                JSONArray artists = items.getJSONObject(i).getJSONArray("artists");

                                StringBuilder stringBuilder = new StringBuilder();
                                for (int j = 0; j < artists.length() - 1; j++) {
                                    // Make many author names appear
                                    stringBuilder.append(artists.getJSONObject(j).getString("name")).append(", ");
                                }
                                stringBuilder.append(artists.getJSONObject(artists.length() - 1).getString("name"));
                                String artistsName = stringBuilder.toString();

                                String tracksName = items.getJSONObject(i).getString("name");
                                JSONObject album = items.getJSONObject(i).getJSONObject("album");
                                JSONArray images = album.getJSONArray("images");
                                String imageURL = images.getJSONObject(0).getString("url");

                                Log.i("tracksName", tracksName);
                                Log.i("artistsName", artistsName);
                                Log.i("imageUrl", imageURL);

                                datas2.add(new SearchLayoutData(imageURL, tracksName, artistsName));
                                // Add datas to recyclerview
                                SearchLayoutAdapter searchLayoutAdapter = new SearchLayoutAdapter(requireContext(),datas2);
                                recyclerView2.setAdapter(searchLayoutAdapter);
                            }


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
}