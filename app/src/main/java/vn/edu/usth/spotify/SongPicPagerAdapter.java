package vn.edu.usth.spotify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SongPicPagerAdapter extends PagerAdapter {
    private final LayoutInflater layoutInflater;

    private View itemView;

    private int image;

    private final String TAG = "SongPicPagerAdapter";

    public SongPicPagerAdapter(Context context, int image) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.image = image;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
        Log.i(TAG, "View " + position + " has been destroyed");
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate song_pic_item.xml
        itemView = layoutInflater.inflate(R.layout.song_pic_item, container, false);

        Log.i(TAG, "View " + position + " has been created");

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        if (position == Integer.MAX_VALUE / 2)
            updatePic(image);

        return itemView;
    }

    // Function to update the Song picture
    public void updatePic(int image) {
        // Referencing the image view from the item.xml file
        ImageView SongPic = itemView.findViewById(R.id.Pic);

        // Setting the image in the imageView
        SongPic.setImageResource(image);

        Log.i(TAG, "Picture updated");
    }
}
