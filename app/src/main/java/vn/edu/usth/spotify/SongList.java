package vn.edu.usth.spotify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

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

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.intothenight);
//        ImageView album_cover = view.findViewById(R.id.images);
//        String imageUrl = "https://i.scdn.co/image/ab67616d0000b273c98af859e9b24d3a6c1c72bb?fbclid=IwAR3qvm4wNTRUEbEtkZd4zAFMvSgCdevfQnfSgUAmxrr9oIh7PyPWb4M0RlI";
        setImage(view, "https://i.scdn.co/image/ab67616d0000b273c98af859e9b24d3a6c1c72bb?fbclid=IwAR3qvm4wNTRUEbEtkZd4zAFMvSgCdevfQnfSgUAmxrr9oIh7PyPWb4M0RlI");
        MusicActivity activity = (MusicActivity) getActivity();

        RecyclerView songListRecyclerView = view.findViewById(R.id.SongListRecyclerView);
        songListRecyclerView.setHasFixedSize(true);
        songListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<ItemSongList> songListList = new ArrayList<>();
        songListList.add(new ItemSongList("Light Switch", "Charlie Puth"));
        songListList.add(new ItemSongList("Light Switch", "Charlie Puth"));
        songListList.add(new ItemSongList("Light Switch", "Charlie Puth"));
        songListList.add(new ItemSongList("Light Switch", "Charlie Puth"));
        songListList.add(new ItemSongList("Light Switch", "Charlie Puth"));

        SongListAdapter songListAdapter = new SongListAdapter(requireContext(), songListList);

        songListRecyclerView.setAdapter(songListAdapter);

        assert activity != null;
        // relativeLayout.setBackground(activity.getGradientDrawable(bitmap));

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
                    MusicActivity musicActivity = (MusicActivity) getActivity();
                    if (musicActivity != null) {
                        musicActivity.APICall("https://api.spotify.com/v1/albums/6KUcTXmg2VkUuHDtWcjSrd");
                    }
                }
                else{
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

    private void setImage(View view, String imageURl) {
        MusicActivity musicActivity = (MusicActivity) getActivity();
        if (musicActivity != null) {
            ImageView album_cover = view.findViewById(R.id.images);
            Picasso.get().load(imageURl).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    album_cover.setImageBitmap(bitmap);
                    MusicActivity activity = (MusicActivity) getActivity();
                    if (activity != null) {
                        view.setBackground(activity.getGradientDrawable(bitmap));
                    }
                    Log.i("SongList", "bitmap loaded");
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

        }
    }


}


