package vn.edu.usth.spotify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.WindowDecorActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SongList extends Fragment  {


    boolean liked = false;
    boolean shuffled = false;

    boolean played = false;


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

        RelativeLayout relativeLayout = view.findViewById(R.id.boundedRelativeLayout);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.intothenight);

        MusicActivity activity = (MusicActivity) getActivity();


        assert activity != null;
        relativeLayout.setBackground(activity.getGradientDrawable(bitmap));

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

                if (!liked)
                {
                    liked = true;
                    heart.setImageResource(R.drawable.heart_liked);
                    Toast.makeText(getContext(), added, duration).show();
                }
                else
                {

                    liked = false;
                    heart.setImageResource(R.drawable.heart);
                    Toast.makeText(getContext(),removed, duration).show();
                }

            }
        });
        ImageButton shuffleSong = (ImageButton) view.findViewById(R.id.btn_shuffle);
        shuffleSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!shuffled){
                     shuffled = true;
                     shuffleSong.setImageResource(R.drawable.player_shuffled);

               }

               else{
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
                        Toast.makeText(getContext(), item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }

                });
                popup.show();
            }

        });
        ImageButton player = (ImageButton) view.findViewById(R.id. btn_play);
        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!played){
                    played = true;
                    player.setImageResource(R.drawable.playlist_pause);
                }
                else{
                    played = false;
                    player.setImageResource(R.drawable.player_resume);
                }
            }
        });
        ImageView songimgview = (ImageView) view.findViewById(R.id.song_popupmenu);

        songimgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), songimgview);
                popup.getMenuInflater().inflate(R.menu.musicmenu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getContext(), item.getTitle(),Toast.LENGTH_SHORT).show();
                        return true;
                    }

                });
                popup.show();
            }

        });

        RelativeLayout songPack = (RelativeLayout) view.findViewById(R.id.song_pack);
        songPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView songName = (TextView) view.findViewById(R.id.song_name);
                songName.setTextColor(getResources().getColor(R.color.green_spotify));

                MusicActivity activity = (MusicActivity) getActivity();
                if (activity != null) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    activity.popupFragment(mediaPlayer);
                    activity.hideFragmentsAndTabLayout();
                }

                    Log.i("Button", "Pressed");
                }
        });



        return view;


    }

    public void getAlbumJSON(){
        String url = "https://api.spotify.com/v1/tracks/11dFghVXANMlKmJXsNCbNl";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Log.i("getAlbum", "getAlbumJSON: Started");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("getAlbum","Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("getAlbum", "onErrorResponse: Failed");
                    }
                });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);


    }


}