package vn.edu.usth.spotify;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    Context context;
    List<SearchFragmentData> datas;

    public SearchAdapter(Context context, List<SearchFragmentData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.image1.setImageResource(datas.get(position).getImage1());
        holder.image2.setImageResource(datas.get(position).getImage2());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
