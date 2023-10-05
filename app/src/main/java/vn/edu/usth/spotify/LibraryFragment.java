package vn.edu.usth.spotify;
import android.annotation.SuppressLint;
import android.media.Image;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LibraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LibraryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean menu_clicked = false;

    private RecyclerView recyclerView;
    private ImageView switchButton;
    List<LibraryItem> items;
    public LibraryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LibraryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LibraryFragment newInstance(String param1, String param2) {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library, container, false);


        ImageButton button = (ImageButton) view.findViewById(R.id.menu);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menu_clicked) {
                    button.setImageResource(R.drawable.list);
                    menu_clicked = true;
                } else {
                    button.setImageResource(R.drawable.menu);
                    menu_clicked =false;
                }
            }
        });
        recyclerView = view.findViewById(R.id.libraryrecyclerview);
        List<LibraryItem> items = new ArrayList<LibraryItem>();
        items.add(new LibraryItem("Liked Songs", "Playlist", R.drawable.liked_songs));
        items.add(new LibraryItem("Top Gaming Tracks", "Playlist", R.drawable.img2));
        items.add(new LibraryItem("Dance Hits", "Album", R.drawable.img3));
        items.add(new LibraryItem("Pop shots", "Album", R.drawable.img4));
        items.add(new LibraryItem("Your Summer Rewind", "Album", R.drawable.img5));
        items.add(new LibraryItem("Dinner And Chill", "Album", R.drawable.img8));
        items.add(new LibraryItem("Coffee Table Jazz", "Album", R.drawable.img13));
        items.add(new LibraryItem("Release Radar", "Album", R.drawable.img16));




        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        LibraryAdapter libraryAdapter = new LibraryAdapter(requireContext(),items);
        recyclerView.setAdapter(libraryAdapter);

        switchButton = view.findViewById(R.id.switch_button);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchFragment", "Click on Searchbar" );

                MusicActivity musicActivity = (MusicActivity) getActivity();
                if (musicActivity !=null){
                    musicActivity.popupFragment(new LibrarySearchLayoutFragment());
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_appbar, menu);
    }
}