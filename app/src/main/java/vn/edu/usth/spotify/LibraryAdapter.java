package vn.edu.usth.spotify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryViewHolder> {
    private Context context;
    List<LibraryItem> items;

    public LibraryAdapter(Context context, List<LibraryItem> items) {
        this.context = context;
        this.items = items;
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
        holder.imageView.setImageResource(items.get(position).getLibrary_image_view());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the item click event here
                // You can perform any desired action for the clicked item
                // For example, you can launch a new activity, show a dialog, etc.
                Log.i("buttonclick", "Clicked on button");

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
}
