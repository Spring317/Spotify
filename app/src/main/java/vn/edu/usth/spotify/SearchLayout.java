package vn.edu.usth.spotify;

import android.os.Bundle;

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

public class SearchLayout extends Fragment {

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
        View view = inflater.inflate(R.layout.search_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.searchlistrecyclerview);
        List<SearchFragmentData> datas = new ArrayList<>();

        TextView Cancelbtn = view.findViewById(R.id.Cancelbtn);
        Cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchExtend", "Click on Cancel button");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(SearchLayout.this);
                fragmentTransaction.commit();
            }
        });

        RecyclerView recyclerView2 = view.findViewById(R.id.searchlistrecyclerview);
        List<SearchLayoutData> datas2 = new ArrayList<>();

        recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView2.setHasFixedSize(true);
        SearchLayoutAdapter searchLayoutAdapter = new SearchLayoutAdapter(requireContext(),datas2);
        recyclerView2.setAdapter(searchLayoutAdapter);

        return view;
    }
}