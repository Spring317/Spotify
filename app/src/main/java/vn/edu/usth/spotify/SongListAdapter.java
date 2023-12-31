package vn.edu.usth.spotify;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongListViewHolder> {

    private String TAG = "SongList Button";
    private Context context;
    private List<ItemSongList> itemSongListList;

    List<String> tracksUrl;
    List<String> tracksUri;

    private TextView currentClickedTextView = null;

    public SongListAdapter(Context context, List<ItemSongList> itemSongListList, List<String> tracksUrl, List<String> tracksUri) {
        this.itemSongListList = itemSongListList;
        this.context = context;
        this.tracksUrl = tracksUrl;
        this.tracksUri = tracksUri;
    }

    @NonNull
    @Override
    public SongListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song_list, parent, false);
        return new SongListViewHolder(view);
    }

    public class SongListViewHolder extends RecyclerView.ViewHolder {
        private TextView mText_1;
        private TextView mText_2;
        private String url;

        private String uri;

        Integer positionInList;

        // Demo
        public SongListViewHolder(@NonNull View itemView) {
            super(itemView);

            mText_1 = itemView.findViewById(R.id.song_name);
            mText_2 = itemView.findViewById(R.id.author_name);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.SongListViewHolder holder, int position) {
        holder.mText_1.setText(itemSongListList.get(position).getSongName());
        holder.mText_2.setText(itemSongListList.get(position).getAuthorName());
        holder.url = itemSongListList.get(position).getUrl();
        holder.uri = itemSongListList.get(position).getUri();
        holder.positionInList = itemSongListList.get(position).getPosition();
        holder.mText_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MusicActivity) {
                    MusicActivity activity = (MusicActivity) context;
                    MediaPlayer mediaPlayer = new MediaPlayer(holder.url, tracksUrl, tracksUri, holder.positionInList);


                    Log.i(TAG, "onClick: "+ holder.uri);

                    activity.popupFragment(mediaPlayer);
                    activity.hideFragmentsAndTabLayout();
                    activity.playSong(holder.uri);


                    if (currentClickedTextView != null) {
                        currentClickedTextView.setTextColor(Color.WHITE);
                    }

                    // Set text color to #1ED760
                    holder.mText_1.setTextColor(Color.parseColor("#1ED760"));

                    currentClickedTextView = holder.mText_1;
                }

                Log.i(TAG, "Pressed");
            }
        });

        holder.mText_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MusicActivity) {
                    MusicActivity activity = (MusicActivity) context;
                    MediaPlayer mediaPlayer = new MediaPlayer(holder.url, tracksUrl, tracksUri, holder.positionInList);
                    activity.popupFragment(mediaPlayer);
                    activity.hideFragmentsAndTabLayout();
                    activity.playSong(holder.uri);

                    if (currentClickedTextView != null) {
                        currentClickedTextView.setTextColor(Color.WHITE);
                    }

                    // Set text color to #1ED760
                    holder.mText_1.setTextColor(Color.parseColor("#1ED760"));

                    currentClickedTextView = holder.mText_1;
                }

                Log.i(TAG, "Pressed");
            }
        });


    }


    @Override
    public int getItemCount() {
        return itemSongListList.size();
    }
}
