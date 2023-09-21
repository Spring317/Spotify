package vn.edu.usth.spotify;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    public CustomPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    String TAG = "CustomPagerAdapter";
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.i(TAG, "getItem: HomeFragment");
                return new HomeFragment();

            case 1:
                Log.i(TAG, "getItem: searchFragment");
                return new SearchFragment();
            case 2:
                Log.i(TAG, "getItem: LibraryFragment");
                return new LibraryFragment();
            default:

                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
