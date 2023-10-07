package vn.edu.usth.spotify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryViewHolder> implements Filterable {
    private Context context;
    List<LibraryItem> items;
    List<LibraryItem> itemsExample;

    public LibraryAdapter(Context context, List<LibraryItem> items) {
        this.context = context;
        this.items = items;
        this.itemsExample = new ArrayList<>(items);
        Log.i("LibraryAdapter", String.valueOf(itemsExample.size()));
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LibraryViewHolder(LayoutInflater.from(context).inflate(R.layout.library_activity,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getLibrary_name());
        holder.typeView.setText(items.get(position).getLibrary_type());
        Picasso.get().load(items.get(position).getLibrary_image_view()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the item click event here
                // You can perform any desired action for the clicked item
                // For example, you can launch a new activity, show a dialog, etc.
                Log.i("LibraryAdapter", "Clicked on button");

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
        return items.size();
    }
    @Override
    public Filter getFilter(){
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<LibraryItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length()==0){
                filteredList.addAll(itemsExample);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (LibraryItem item : itemsExample){
                    if(item.getLibrary_name().toLowerCase().contains(filterPattern)){{
                        filteredList.add(item);
                    }}
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            itemsExample.clear();
            itemsExample.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
