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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    LibraryAdapter libraryAdapter;

    List<LibraryItem> items = new ArrayList<>();


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
        MusicActivity musicActivity = (MusicActivity) getActivity();
        if (musicActivity != null) {
            // Initiate an API call to retrieve information about a Spotify album using the makeAPICall method
            // and provide a callback to handle the API call result.

            musicActivity.makeAPICall("https://api.spotify.com/v1/users/31hxiif37rtibeakz7c44wkesoj4/playlists", new Callback() {
                @Override
                public void onAPICallComplete(JSONObject jsonObject) {
                    if (jsonObject != null) {
                        Log.i("jsonObject", jsonObject.toString());
                        try {
                            JSONArray itemArray = jsonObject.getJSONArray("items");
                            //Init RecyclerView for LibraryFragment
                            items.clear();
                            for (int i = 0; i < itemArray.length(); i++) {
                                JSONObject albumObj = itemArray.getJSONObject(i);
                                JSONArray imageArray = albumObj.getJSONArray("images");
                                String imageURL = imageArray.getJSONObject(0).getString("url");
                                Log.i("imageURL", imageURL);

                                String albumName = albumObj.getString("name");
                                Log.i("albumName", albumName);

                                String albumType = albumObj.getString("type");
                                Log.i("albumType", albumType);

                                items.add(new LibraryItem(albumName, albumType, imageURL));
                            }

                            libraryAdapter = new LibraryAdapter(requireContext(), items);
                            recyclerView.setAdapter(libraryAdapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }


                }
            });
            recyclerView = view.findViewById(R.id.libraryrecyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setHasFixedSize(true);

            ImageButton button = (ImageButton) view.findViewById(R.id.menu);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!menu_clicked) {
                        button.setImageResource(R.drawable.list);
                        menu_clicked = true;
                    } else {
                        button.setImageResource(R.drawable.menu);
                        menu_clicked = false;
                    }
                }
            });


            switchButton = view.findViewById(R.id.switch_button);
            switchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("LibraryFragment", "Click on Searchbar");

                    MusicActivity musicActivity = (MusicActivity) getActivity();
                    if (musicActivity != null) {
                        musicActivity.popupFragment(new LibrarySearchLayoutFragment(libraryAdapter));
                    }
                }
            });

        }
        return view;
    }
}