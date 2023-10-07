package vn.edu.usth.spotify;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LibrarySearchLayoutFragment extends Fragment {

    private LibraryAdapter libraryAdapter;

    public LibrarySearchLayoutFragment(LibraryAdapter libraryAdapter) {
        this.libraryAdapter = libraryAdapter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MusicActivity musicActivity = (MusicActivity) getActivity();
        if (musicActivity !=null){
            musicActivity.restoreViewPager();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.library_fragment_searchbar, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.librarySearchListRecyclerView);
        List<LibraryItem> items = new ArrayList<>();

        TextView cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchExtend", "Click on Cancel button");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(LibrarySearchLayoutFragment.this);
                fragmentTransaction.commit();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(libraryAdapter);

        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.library_search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                libraryAdapter.getFilter().filter(s);
                return false;
            }
        });
        return view;
    }
}