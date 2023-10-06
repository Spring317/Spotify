package vn.edu.usth.spotify;

import android.content.ClipData;
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
    public SearchLayoutAdapter(Context context2, List<SearchLayoutData> datas2) {
        this.context = context2;
        this.datas = datas2;
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

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchLayoutAdapter", "Clicked on button");

                if (context instanceof MusicActivity) {
                    MusicActivity activity = (MusicActivity) context;
                    SongList songList = new SongList();
                    activity.popupFragment(songList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
