package com.mancng.catchaeventex.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FriendsViewPagerAdapter extends FragmentStatePagerAdapter {

    public FriendsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment returnFragment;

        switch (position) {
            case 0:
                returnFragment = FriendsListFragment.newInstance();
                break;
            case 1:
                returnFragment = FriendRequestFragment.newInstance();
                break;
            case 2:
                returnFragment = FindFriendsFragment.newInstance();
                break;

            default:
                return null;
        }
        return returnFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title;

        switch (position) {
            case 0:
                title = "Friends";
                break;
            case 1:
                title = "Requests";
                break;
            case 2:
                title = "Add Friends";
                break;
            default:
                return null;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
