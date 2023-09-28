package vn.edu.usth.spotify;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    ImageButton image1;
    ImageButton image2;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        image1 = itemView.findViewById(R.id.image1);
        image2 = itemView.findViewById(R.id.image2);
    }
}
