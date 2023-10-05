package vn.edu.usth.spotify;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LibraryViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, typeView;
    public LibraryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.library_image_view);
        nameView = itemView.findViewById(R.id.library_name);
        typeView = itemView.findViewById(R.id.library_type);
    }
}
