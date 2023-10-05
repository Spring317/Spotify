package vn.edu.usth.spotify;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.searchrecyclerview);
        List<SearchFragmentData> datas = new ArrayList<>();

        datas.add(new SearchFragmentData(R.drawable.glow,R.drawable.leny));
        datas.add(new SearchFragmentData(R.drawable.madeforu,R.drawable.newreleases));
        datas.add(new SearchFragmentData(R.drawable.vietnamesemusics,R.drawable.pop));
        datas.add(new SearchFragmentData(R.drawable.kpop,R.drawable.hiphop));
        datas.add(new SearchFragmentData(R.drawable.charts,R.drawable.freshfinds));
        datas.add(new SearchFragmentData(R.drawable.equal,R.drawable.glow));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        SearchAdapter searchAdapter = new SearchAdapter(requireContext(),datas);
        recyclerView.setAdapter(searchAdapter);

        TextView search_bar = view.findViewById(R.id.search_bar);
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchFragment", "Click on Searchbar" );

                MusicActivity musicActivity = (MusicActivity) getActivity();
                if (musicActivity !=null){
                    musicActivity.popupFragment(new SearchLayout());
                }
            }
        });

        return view;
    }
}