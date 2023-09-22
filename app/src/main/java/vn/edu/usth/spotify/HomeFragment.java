package vn.edu.usth.spotify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("HomeFragment", "View Destroyed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the TextView by its ID
        TextView greetingTextView = view.findViewById(R.id.greetingTextView);

        // Set the greeting in the TextView
        String greeting = getGreeting();
        greetingTextView.setText(greeting);

        LinearLayout play_list1 = view.findViewById(R.id.song_1);

        play_list1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicActivity activity = (MusicActivity) getActivity();
                if (activity != null) {
                    SongList songList = new SongList();
                    activity.popupFragment(songList);
                }

                Log.i("Button", "Pressed");
            }
        });

        return view;
    }

    private String getGreeting() {
        // Get the current time
       Calendar calendar = Calendar.getInstance();
       int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        // Determine the appropriate greeting based on the time
       String greeting;
        if (hourOfDay >= 0 && hourOfDay < 12) {
            greeting = "Good Morning";
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }

        return greeting;
    }


}