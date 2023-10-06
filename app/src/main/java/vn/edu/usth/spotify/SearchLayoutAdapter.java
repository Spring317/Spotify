package vn.edu.usth.spotify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchLayoutAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    Context context;
    List<SearchLayoutData> datas;

    List<String> tracksUrl;
    List<String> tracksUri;

    public SearchLayoutAdapter(Context context2, List<SearchLayoutData> datas2, List<String> tracksUrl, List<String> tracksUri) {
        this.context = context2;
        this.datas = datas2;
        this.tracksUrl = tracksUrl;
        this.tracksUri = tracksUri;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.search_layout_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Picasso.get().load(datas.get(position).getImage()).into(holder.image);
        holder.name.setText(datas.get(position).getName());
        holder.declare.setText(datas.get(position).getDeclare());
        holder.position = datas.get(position).getPosition();
        holder.url = datas.get(position).getUrl();
        holder.uri = datas.get(position).getUri();

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchLayoutAdapter", "Clicked on button");

                if (context instanceof MusicActivity) {
                    MusicActivity activity = (MusicActivity) context;
                    MediaPlayer songList = new MediaPlayer(holder.url, tracksUrl, tracksUri, holder.position);
                    activity.playSong(holder.uri);
                    activity.popupFragment(songList);
                    activity.hideFragmentsAndTabLayout();
                }
            }
        });
        holder.declare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchLayoutAdapter", "Clicked on button");

                if (context instanceof MusicActivity) {
                    MusicActivity activity = (MusicActivity) context;
                    MediaPlayer songList = new MediaPlayer(holder.url, tracksUrl, tracksUri, holder.position);
                    activity.playSong(holder.uri);
                    activity.popupFragment(songList);
                    activity.hideFragmentsAndTabLayout();
                }
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchLayoutAdapter", "Clicked on button");

                if (context instanceof MusicActivity) {
                    MusicActivity activity = (MusicActivity) context;
                    MediaPlayer songList = new MediaPlayer(holder.url, tracksUrl, tracksUri, holder.position);
                    activity.playSong(holder.uri);
                    activity.popupFragment(songList);
                    activity.hideFragmentsAndTabLayout();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
