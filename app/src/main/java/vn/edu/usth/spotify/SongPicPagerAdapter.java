package vn.edu.usth.spotify;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.Objects;

public class SongPicPagerAdapter extends PagerAdapter {
    private final LayoutInflater layoutInflater;

    private ViewGroup container;

    private final String TAG = "SongPicPagerAdapter";

    private int direction;

    public SongPicPagerAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View itemView = layoutInflater.inflate(R.layout.song_pic_item, container, false);

        Log.i(TAG, "View " + position + " has been created");

        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        this.container = container;

        return itemView;
    }

    // Function to update the Song picture
    public void updatePic(Bitmap image, int direction) {
        int index = 0;

        // direction: 1 min right and 0 min left
        if (this.direction != 0) {
            if (this.direction == direction)
                index = 1;
        } else if (direction != 0)
            index = 1;

        // Referencing the image view from the item.xml file
        ImageView SongPic = container.getChildAt(index)
                .findViewById(R.id.Pic);
        SongPic.setImageBitmap(image);

        this.direction = direction;

        Log.i(TAG, "Picture updated at view: " + index + "in ViewGroup container");
    }
}
