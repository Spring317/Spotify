package vn.edu.usth.spotify;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<Fragment>();

    public CustomPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragments.add(new HomeFragment());
        fragments.add(new SearchFragment());
        fragments.add(new LibraryFragment());
    }
    String TAG = "CustomPagerAdapter";
    @NonNull
    @Override
    public Fragment getItem(int position) {
         return fragments.get(position);
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
