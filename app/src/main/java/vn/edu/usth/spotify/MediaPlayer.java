package vn.edu.usth.spotify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MediaPlayer extends Fragment {
    private static final String TAG1 = "MediaPlayer";

    // Declare pager and its adapter
    ViewPager songPicPager;
    SongPicPagerAdapter songPicPagerAdapter;

    // Declare view
    View view;

    // Initial value of the MediaPlayer
    private int image = R.drawable.blank_song;
    private int song_length = 0;
    private String song_title = "Blank_Song";
    private String song_author = "No one";

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null; // Nullify the reference to the view

        Log.i(TAG1, "View destroyed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_song, container, false);

        Log.i(TAG1, "View created");

        UpdateValue(R.drawable.light_switch, 0, "Light Switch", "Charlie Puth");
        UpdateContent();

        // Initialize songPicPagerAdapter and songPicPager
        songPicPager = view.findViewById(R.id.Song_pic);
        songPicPagerAdapter = new SongPicPagerAdapter(requireContext(), this.image);
        songPicPager.setAdapter(songPicPagerAdapter);
        songPicPager.setCurrentItem(Integer.MAX_VALUE / 2,false);

        songPicPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int previousPosition = Integer.MAX_VALUE / 2;
            int isSwipeRight = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                // Fail safe for people who swipe 1073741822 times!!!
                if (position == 0 || position == Integer.MAX_VALUE)
                    songPicPager.setCurrentItem(Integer.MAX_VALUE / 2,false);

                // Return 0 if equal, negative value if less and positive value if bigger
                isSwipeRight = Integer.compare(position, previousPosition);

                previousPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE: {
                        Swipe(isSwipeRight);

                        Log.i("SongPicPager", "SCROLL_STATE_IDLE");
                        break;
                    }
                    case ViewPager.SCROLL_STATE_SETTLING: {

                        Log.i("SongPicPager", "SCROLL_STATE_SETTLING");
                        break;
                    }
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void Swipe(int isSwipeRight) {
        if (isSwipeRight > 0) {
            Log.i("SongPicPager", "User scrolled right");

            // Call function to update all the value
            UpdateValue(R.drawable.kill_this_love, 188, "Kill This Love", "Blackpink");

            // Call function to update all the content
            UpdateContent();
        }
        else if (isSwipeRight < 0) {
            Log.i("SongPicPager", "User scrolled left");
        }
        else Log.i("SongPicPager", "User didn't scroll");
    }

    // Function to update all the content value
    private void UpdateValue(int image, int song_length, String title, String author) {
        this.image = image;
        this.song_length = song_length;
        this.song_title = title;
        this.song_author = author;

        Log.i(TAG1, "All the content value has been updated");
    }

    // Function to update and display all the content of MediaPlayer fragment
    private void UpdateContent() {
        UpdateBackGround(this.image);

        try { // Will output error first time create fragment!! (Null exception)
            songPicPagerAdapter.updatePic(this.image); // Call func to update the song picture
        } catch (Exception exception) {
            Log.i(TAG1, "error while update image" + exception);
        }

        setProgressBar(this.song_length);
        UpdateTitleNAuthor(this.song_title, this.song_author);

        Log.i(TAG1, "All content updated");
    }

    // Function to update the song title and the author
    private void UpdateTitleNAuthor(String title, String author) {
        TextView song_title = view.findViewById(R.id.Song_title);
        TextView song_author = view.findViewById(R.id.Song_author);
        TextView song_header = view.findViewById(R.id.song_header);

        song_header.setText(title);
        song_title.setText(title);
        song_author.setText(author);

        Log.i(TAG1, "Song title, header and author updated");
    }

    // Function to get the dominant color of Song picture
    // set gradient background and lyrics background
    private void UpdateBackGround(int image) {
        // Simulate gradient background
        // Load the picture into a Bitmap
        Bitmap picBit = BitmapFactory.decodeResource(getResources(), image);

        // Use Palette to extract dominant colors
        Palette palette = Palette.from(picBit).generate();

        // Get dominant color
        int defaultColor = Color.BLACK;
        int dominantColor = palette.getDominantColor(defaultColor);

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {dominantColor, defaultColor}
        );

        gradientDrawable.setCornerRadius(0f);

        // Set the background to gradient
        view.setBackground(gradientDrawable);

        Log.i(TAG1, "Gradient background updated");

        // Set lyrics background to dominant color
        LinearLayout lyrics_area = view.findViewById(R.id.lyrics_area);

        // Get the background for drawable/lyrics_background
        GradientDrawable lyrics_drawable = (GradientDrawable) lyrics_area.getBackground();

        // Change background color
        lyrics_drawable.setColor(dominantColor);
        lyrics_area.setBackground(lyrics_drawable);

        Log.i(TAG1, "Lyrics background updated");
    }

    // Function to increment the progress bar value,
    // increment the current time,
    // decrement the remain time
    // and display on view
    private void setProgressBar(float time) {
        if (time == 0)
            return;

        // Declare
        ProgressBar progressBar;
        TextView current_time, remain_time;

        // Initialize the progressbar
        progressBar = view.findViewById(R.id.progressBar);

        // Initialize current-time and remain-time
        current_time = view.findViewById(R.id.current_time);
        remain_time = view.findViewById(R.id.remain_time);

        final int totalProgress = 1000;
        final int SecProgress = (int)((time / 100) * 100);
        final Handler handler = new Handler(Looper.getMainLooper());

        // Runnable() aka lambda function!!!
        new Thread(new Runnable() {

            private static final String TAG2 = "SongProgressBar";

            @Override
            public void run() {

                Log.i(TAG2, "THREAD: Started new one");

                int progress = 0;
                while (progress < totalProgress) {
                    progress += 1;
                    final int finalProgress = progress;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            progressBar.setProgress(finalProgress);

                            current_time.setText(convertMillsToMin(finalProgress * SecProgress, ""));
                            remain_time.setText(convertMillsToMin((totalProgress - finalProgress) * SecProgress + 1000, "-"));
                        }
                    });
                    try {
                        // Sleep for a while to simulate time-consuming
                        Thread.sleep(SecProgress);

                        Log.i(TAG2, "THREAD: sleeping for a certain of time");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Reset remain time to 0 because the remain time was plus 1 sec
                remain_time.setText(getString(R.string.remain_time_default));

                Log.i(TAG2, "THREAD: finished");
            }
        }).start();

        Log.i(TAG1, "Progress bar updated and running on new thread");
    }

    // Function to convert current time and remain time from milliseconds to {minutes:seconds}
    private String convertMillsToMin(int time, String header) {
        int min, secs;
        String min_str, secs_str;

        // 60000 mills means 1 min and 1000 mills means 1 sec
        min = (int) Math.floor((double) time / 60000);
        secs = (int) Math.floor(((double) time % 60000) / 1000);

        min_str = Integer.toString(min);
        secs_str = Integer.toString(secs);

        if (secs_str.length() == 1)
            return header + min_str + ":0" + secs_str;
        else return header + min_str + ":" + secs_str;
    }
}