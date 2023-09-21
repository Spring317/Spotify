package vn.edu.usth.spotify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongList!newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongList extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean liked = false;
    boolean shuffled = false;

    boolean played = false;

    boolean song_clicked = false;


    public SongList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment SongList.
     */
    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list, container, false);
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
        ImageView imgview = (ImageView) view.findViewById(R.id.popupmenu);

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), imgview);
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
                    player.setImageResource(R.drawable.player_resume);
                }
                else{
                    played = false;
                    player.setImageResource(R.drawable.music_player_pause);
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

        RelativeLayout songpack = (RelativeLayout) view.findViewById(R.id.song_pack);
        songpack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!song_clicked){
                    song_clicked = true;
                    TextView songname = (TextView) view.findViewById(R.id.song_name);
                    songname.setTextColor(getResources().getColor(R.color.green_spotify));
                }


            }
        });

        return view;
    }

    


}