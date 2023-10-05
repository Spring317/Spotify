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

    String albumUrl = "https://api.spotify.com/v1/albums/68w73FF3dYC6C3RWdcV0Yl";
    boolean liked = false;
    boolean shuffled = false;

    boolean played = false;

    JSONArray imageArray;


    JSONArray artistsArray;

    public SongList() {
        // Required empty public constructor
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

        // Reference the kill playlist button
        ImageButton kill_playlist_btn = view.findViewById(R.id.kill_playlist_btn);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.intothenight);
//        ImageView album_cover = view.findViewById(R.id.images);
//        String imageUrl = "https://i.scdn.co/image/ab67616d0000b273c98af859e9b24d3a6c1c72bb?fbclid=IwAR3qvm4wNTRUEbEtkZd4zAFMvSgCdevfQnfSgUAmxrr9oIh7PyPWb4M0RlI";

        getImageURL(albumUrl);
        getAlbumName(albumUrl);
        getAlbumArtist(albumUrl);
        getAlbumListContext(albumUrl);

//        getAlbumListContext(albumUrl);


//         relativeLayout.setBackground(activity.getGradientDrawable(bitmap));

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
//        ImageView songimgview = (ImageView) view.findViewById(R.id.song_popupmenu);
//
//        songimgview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popup = new PopupMenu(getContext(), songimgview);
//                popup.getMenuInflater().inflate(R.menu.musicmenu, popup.getMenu());
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        Toast.makeText(getContext(), item.getTitle(),Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//
//                });
//                popup.show();
//            }
//
//        });

//        LinearLayout songPack = (LinearLayout) view.findViewById(R.id.song_pack);
//        songPack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TextView songName = (TextView) view.findViewById(R.id.song_name);
//                songName.setTextColor(getResources().getColor(R.color.green_spotify));
//
//                MusicActivity activity = (MusicActivity) getActivity();
//                if (activity != null) {
//                    MediaPlayer mediaPlayer = new MediaPlayer();
//                    activity.popupFragment(mediaPlayer);
//                    activity.hideFragmentsAndTabLayout();
//                }
//
//                    Log.i("Button", "Pressed");
//                }
//        });

        return view;
    }

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
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }

    public void getImageURL(String albumUrl) {
        Log.i("SongList", "getImageURL: Started");
        MusicActivity musicActivity = (MusicActivity) getActivity();
        String imageUrl = "";
        if (musicActivity != null) {
            musicActivity.makeAPICall(albumUrl, new Callback() {
                @Override
                public void onAPICallComplete(JSONObject jsonObject) {
                    try {

                        imageArray = jsonObject.getJSONArray("images");
                        Log.i("SongList", "image url: Completed " + imageArray.getJSONObject(0).getString("url"));

                        setImage(getView(), imageArray.getJSONObject(0).getString("url"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }
    }

    public void getAlbumName(String albumUrl) {
        MusicActivity musicActivity = (MusicActivity) getActivity();
        if (musicActivity != null) {
            musicActivity.makeAPICall(albumUrl, new Callback() {
                @Override
                public void onAPICallComplete(JSONObject jsonObject) {
                    try {
                        String albumName = jsonObject.getString("name");
                        TextView AlbumName = getView().findViewById(R.id.album_name);
                        AlbumName.setText(albumName);
                        Log.i("AlbumNameArray", "onAPICallComplete: " + albumName);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }
    }

    public void getAlbumArtist(String albumUrl) {
        MusicActivity musicActivity = (MusicActivity) getActivity();
        if (musicActivity != null) {
            musicActivity.makeAPICall(albumUrl, new Callback() {
                @Override
                public void onAPICallComplete(JSONObject jsonObject) {
                    try {
                        artistsArray = jsonObject.getJSONArray("artists");
                        String artistName = artistsArray.getJSONObject(0).getString("name");
                        TextView ArtistName = getView().findViewById(R.id.artist_name);
                        ArtistName.setText(artistName);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }
    }

    public void getAlbumListContext(String albumUrl) {
        MusicActivity musicActivity = (MusicActivity) getActivity();
        if (musicActivity != null) {
            musicActivity.makeAPICall(albumUrl, new Callback() {
                @Override
                public void onAPICallComplete(JSONObject jsonObject) {
                    try {

                        JSONObject tracks = jsonObject.getJSONObject("tracks");
                        JSONArray items = tracks.getJSONArray("items");


                        JSONArray artists = items.getJSONObject(0).getJSONArray("artists");
                        String artistName = artists.getJSONObject(0).getString("name");
                        String uriString = items.getJSONObject(0).getString("uri");

                        Uri uri = Uri.parse(uriString);

                        RecyclerView songListRecyclerView = getView().findViewById(R.id.SongListRecyclerView);
                        songListRecyclerView.setHasFixedSize(true);
                        songListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                        List<ItemSongList> songListList = new ArrayList<>();

                        for (int i = 0; i < items.length(); i++) {
                            String songName = items.getJSONObject(i).get("name").toString();
                            songListList.add(new ItemSongList(songName, artistName, uri));
                            Log.i("AlbumList", "onAPICallComplete: " + uri);
//
//                        songListList.add(new ItemSongList(songName, artistName));
//                        songListList.add(new ItemSongList("Light Switch", "Charlie Puth"));
//                        songListList.add(new ItemSongList("Light Switch", "Charlie Puth"));
//                        songListList.add(new ItemSongList("Light Switch", "Charlie Puth"));
//                        songListList.add(new ItemSongList("Light Switch", "Charlie Puth"));

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
}
