package vn.edu.usth.spotify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Current_Song extends Fragment {

    private ProgressBar progressBar;
    private TextView current_time, remain_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current__song, container, false);

        // Initialize the progressbar
        progressBar = view.findViewById(R.id.progressBar);

        // Initialize current-time and remain-time
        current_time = view.findViewById(R.id.current_time);
        remain_time = view.findViewById(R.id.remain_time);

        // Simulate a song with 188 secs length
        setProgressBar(188);

        // Simulate gradient background
        // Load the picture into a Bitmap
        Bitmap picBit = BitmapFactory.decodeResource(getResources(), R.drawable.kill_this_love);

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

        // Set lyrics background to dominant color
        LinearLayout lyrics_area = view.findViewById(R.id.lyrics_area);

        // Get the background for drawable/lyrics_background
        GradientDrawable lyrics_drawable = (GradientDrawable) lyrics_area.getBackground();

        // Change background color
        lyrics_drawable.setColor(dominantColor);
        lyrics_area.setBackground(lyrics_drawable);

        // Inflate the layout for this fragment
        return view;
    }

    // Function to increment the progress bar value,
    // increment the current time,
    // decrement the remain time
    // and display on view
    private void setProgressBar(float time) {
        final int totalProgress = 1000;
        final int SecProgress = (int)((time / 100) * 100);
        final Handler handler = new Handler(Looper.getMainLooper());

        // Runnable() aka lambda function!!!
        new Thread(new Runnable() {

            private static final String TAG = "SongProgressBar says";

            @Override
            public void run() {

                Log.i(TAG, "THREAD: Started new one");

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

                        Log.i(TAG, "THREAD: sleeping for a certain of time");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Reset remain time to 0 because the remain time was plus 1 sec
                remain_time.setText(getString(R.string.remain_time_default));

                Log.i(TAG, "THREAD: finished");
            }
        }).start();
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