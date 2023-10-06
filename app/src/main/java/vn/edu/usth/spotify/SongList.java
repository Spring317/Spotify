package vn.edu.usth.spotify;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SongList extends Fragment {

    String url;
    String type;
    boolean liked = false;
    boolean shuffled = false;

    boolean played = false;

    public SongList(String url, String type) {
        this.url = url;
        this.type = type;
    }

    public SongList() {
        this("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n", "playlist");
    }

    @Override
    public void onDestroy() {
        MusicActivity activity = (MusicActivity) getActivity();
        if (activity != null) {
            activity.restoreViewPager();
        }

        super.onDestroy();
        Log.i("SongList", "fragment destroyed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_song_list, container, false);

        // Determine display album or playlist
        switch (type) {
            case "album":{
                getAlbumInformation(url);
                break;
            }
            case "playlist":{
                getPlaylistInformation(url);
                break;
            }
        }

        // Reference the kill playlist button
        ImageButton kill_playlist_btn = view.findViewById(R.id.kill_playlist_btn);

        kill_playlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.remove(SongList.this);

                transaction.commit();
            }
        });

        ImageButton heart = (ImageButton) view.findViewById(R.id.btn_like);

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence added = "Added to library!";
                CharSequence removed = "Removed from library!";
                int duration = Toast.LENGTH_SHORT;

                if (!liked) {
                    liked = true;
                    heart.setImageResource(R.drawable.heart_liked);
                    Toast.makeText(getContext(), added, duration).show();
                } else {

                    liked = false;
                    heart.setImageResource(R.drawable.heart);
                    Toast.makeText(getContext(), removed, duration).show();
                }

            }
        });
        ImageButton shuffleSong = (ImageButton) view.findViewById(R.id.btn_shuffle);
        shuffleSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!shuffled) {
                    shuffled = true;
                    shuffleSong.setImageResource(R.drawable.player_shuffled);

                } else {
                    shuffled = false;
                    shuffleSong.setImageResource(R.drawable.player_shuffle);
                }
            }
        });
        ImageView menu = (ImageView) view.findViewById(R.id.popupmenu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), menu);
                popup.getMenuInflater().inflate(R.menu.musicmenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }

                });
                popup.show();
            }

        });
        ImageButton player = (ImageButton) view.findViewById(R.id.btn_play);
        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!played) {
                    played = true;
                    player.setImageResource(R.drawable.playlist_pause);
                } else {
                    played = false;
                    player.setImageResource(R.drawable.player_resume);
                }
            }
        });

        return view;
    }

    // Generate bitmap from cdn link
    public void setImage(View view, String albumimageUrl) {
        MusicActivity musicActivity = (MusicActivity) getActivity();
        ImageView album_cover = view.findViewById(R.id.images);
        Picasso.get().load(albumimageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                album_cover.setImageBitmap(bitmap);
                if (musicActivity != null) {
                    view.setBackground(musicActivity.getGradientDrawable(bitmap));
                    Log.i("SongList", "bitmap loaded");
                }
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {}
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}
        });

    }

    // Function to display album
    public void getAlbumInformation(String albumUrl) {
        Log.i("SongList", "getAlbum: Started");
        MusicActivity musicActivity = (MusicActivity) getActivity();
        if (musicActivity != null) {
            musicActivity.makeAPICall(albumUrl, new Callback() {
                @Override
                public void onAPICallComplete(JSONObject jsonObject) {
                    try {
                        JSONArray imageArray;
                        JSONArray artistsArray;
                        // Create array to store images data from API
                        imageArray = jsonObject.getJSONArray("images");

                        //Set album name
                        String albumName = jsonObject.getString("name");
                        TextView AlbumName = getView().findViewById(R.id.album_name);
                        AlbumName.setText(albumName);

                        // Get artists from jsonObj
                        artistsArray = jsonObject.getJSONArray("artists");
                        JSONObject tracks = jsonObject.getJSONObject("tracks");
                        JSONArray items = tracks.getJSONArray("items");

                        // Set up recycler view
                        RecyclerView songListRecyclerView = getView().findViewById(R.id.SongListRecyclerView);
                        songListRecyclerView.setHasFixedSize(true);
                        songListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                        List<ItemSongList> songListList = new ArrayList<>();
                        StringBuilder AlbumArtists = new StringBuilder();
                        for (int i = 0; i < artistsArray.length() - 1; i ++) {

                            AlbumArtists.append(artistsArray.getJSONObject(i).getString("name")).append(", ");
                        }

                        AlbumArtists.append(artistsArray.getJSONObject(artistsArray.length() - 1).getString("name"));

                        TextView ArtistName = getView().findViewById(R.id.artist_name);
                        ArtistName.setText(AlbumArtists.toString());

                        Log.i("SongList", "image url: Completed " + imageArray.getJSONObject(0).getString("url"));

                        setImage(getView(), imageArray.getJSONObject(0).getString("url"));

                        for (int i = 0; i < items.length(); i++) {
                            //Get song names from jsonObj
                            String songName = items.getJSONObject(i).get("name").toString();

                            // Get artists from jsonObj
                            JSONArray artists = items.getJSONObject(i).getJSONArray("artists");
                            StringBuilder TrackArtists = new StringBuilder();
                            for (int j = 0; j < artists.length() - 1; j++) {
                                TrackArtists.append(artists.getJSONObject(j).getString("name")).append(", ");
                            }
                            TrackArtists.append(artists.getJSONObject(artists.length() - 1).getString("name"));

                            // Get urls from jsonObj
//                            String url = items.getJSONObject(i).getString("href");
                            String url = items.getJSONObject(i).getString("href");

                            String uri = items.getJSONObject(i).getString("uri");
                            // Add items to recycler view

                            songListList.add(new ItemSongList(songName, TrackArtists.toString(), url, uri));


                            Log.i("AlbumList", "onAPICallComplete: " + url);

                            SongListAdapter songListAdapter = new SongListAdapter(requireContext(), songListList);

                            songListRecyclerView.setAdapter(songListAdapter);

                            Log.i("AlbumListContext", "onAPICallComplete: " + tracks.toString());
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }
    }

    // Function to display playlist
    public void getPlaylistInformation(String playlistUrl) {
        Log.i("SongList", "getPlaylist: Started");
        MusicActivity musicActivity = (MusicActivity) getActivity();
        if (musicActivity != null) {
            musicActivity.makeAPICall(url, new Callback() {
                @Override
                public void onAPICallComplete(JSONObject jsonObject) {
                    try {
                        JSONArray imageArray;
                        // Create array to store images data from API
                        imageArray = jsonObject.getJSONArray("images");

                        //Set playlist name
                        String name = jsonObject.getString("name");
                        TextView playlistName = getView().findViewById(R.id.album_name);
                        playlistName.setText(name);

                        // Get description from jsonObj
                        String description = jsonObject.getString("description");

                        JSONObject tracks = jsonObject.getJSONObject("tracks");
                        JSONArray items = tracks.getJSONArray("items");

                        // Set up recycler view
                        RecyclerView songListRecyclerView = getView().findViewById(R.id.SongListRecyclerView);
                        songListRecyclerView.setHasFixedSize(true);
                        songListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                        List<ItemSongList> songListList = new ArrayList<>();

                        TextView descriptionView = getView().findViewById(R.id.artist_name);
                        descriptionView.setText(description);

                        Log.i("SongList", "image url: Completed " + imageArray.getJSONObject(0).getString("url"));

                        setImage(getView(), imageArray.getJSONObject(0).getString("url"));

                        for (int i = 0; i < items.length(); i++) {
                            JSONObject track = items.getJSONObject(i).getJSONObject("track");
                            //Get song names from jsonObj
                            String songName = track.getString("name");

                            // Get artists from jsonObj
                            JSONArray artists = track.getJSONArray("artists");
                            StringBuilder TrackArtists = new StringBuilder();
                            for (int j = 0; j < artists.length() - 1; j++) {
                                TrackArtists.append(artists.getJSONObject(j).getString("name")).append(", ");
                            }
                            TrackArtists.append(artists.getJSONObject(artists.length() - 1).getString("name"));

                            // Get urls from jsonObj
                            String url = track.getString("href");

                            String uri = track.getString("uri");

                            // Add items to recycler view
                            songListList.add(new ItemSongList(songName, TrackArtists.toString(), url, uri));

                            Log.i("Playlist", "onAPICallComplete: " + url);

                            SongListAdapter songListAdapter = new SongListAdapter(requireContext(), songListList);

                            songListRecyclerView.setAdapter(songListAdapter);

                            Log.i("PlaylistContext", "onAPICallComplete: " + tracks.toString());
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }
    }
}
