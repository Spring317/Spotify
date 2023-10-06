package vn.edu.usth.spotify;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemPlaylistTitleAdapter extends RecyclerView.Adapter<ItemPlaylistTitleAdapter.FeaturedChartsViewHolder> {

    private static String TAG = "Home Button";
    private Context context;
    private List<ItemPlaylistTitle> itemPlaylistTitleList;
    public ItemPlaylistTitleAdapter(Context context, List<ItemPlaylistTitle> itemPlaylistTitleList) {
        this.itemPlaylistTitleList = itemPlaylistTitleList;
        this.context = context;
    }

    @NonNull
    @Override
    public FeaturedChartsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false);
        return new FeaturedChartsViewHolder(view);
    }

    public class FeaturedChartsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mText;
        private String url;
        private String type;
        // Demo
        public FeaturedChartsViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.playlist_image);
            mText = itemView.findViewById(R.id.playlist_title);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPlaylistTitleAdapter.FeaturedChartsViewHolder holder, int position) {
        holder.mText.setText(itemPlaylistTitleList.get(position).getPlaylistTitle());
        // holder.mImageView.setImageResource(itemPlaylistTitleList.get(position).getImage());
        Picasso.get().load(itemPlaylistTitleList.get(position).getImage()).into(holder.mImageView);
        holder.url = itemPlaylistTitleList.get(position).getUrl();
        holder.type = itemPlaylistTitleList.get(position).getType();

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(200); // Set the duration of the animation in milliseconds
                animation.setInterpolator(new DecelerateInterpolator());

                // Add an animation listener to listen for the end of the animation
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Animation has started
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Animation has ended, now you can call popupFragment
                        if (context instanceof MusicActivity) {
                            MusicActivity activity = (MusicActivity) context;
                            SongList songList = new SongList();
                            activity.popupFragment(songList);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Animation has repeated
                    }
                });

                // Apply the animation to the ImageView
                holder.mImageView.startAnimation(animation);

                Log.i(TAG, "Pressed");
            }
        });

        holder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MusicActivity) {
                    MusicActivity activity = (MusicActivity) context;
                    SongList songList = new SongList();
                    activity.popupFragment(songList);
                }

                Log.i(TAG, "Pressed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemPlaylistTitleList.size();
    }


}
