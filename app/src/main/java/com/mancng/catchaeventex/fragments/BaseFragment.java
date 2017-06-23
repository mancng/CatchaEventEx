package com.mancng.catchaeventex.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;

import com.mancng.catchaeventex.Infrastructure.Utils;
import com.mancng.catchaeventex.R;

public class BaseFragment extends Fragment {

    protected CompositeDisposable mCompositeDisposable;
    protected SharedPreferences mSharedPreferences;
    protected FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCompositeDisposable = new CompositeDisposable();

        //This allow us to get the userEmail and userName for all fragments.
        mSharedPreferences = getActivity().getSharedPreferences(Utils.USER_INFO_PREFERENCE, Context.MODE_PRIVATE);

        //Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        //Crashlytics
        Fabric.with(getActivity(), new Crashlytics());
    }


    public void setUpBottomBar(BottomBar bottomBar, final int index) {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (index) {
                    //If user is inside the Events tab
                    case 1:
                        if (tabId == R.id.tab_profile) {
                            Intent intent = new Intent(getActivity(), ProfileActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        } else if (tabId == R.id.tab_friends) {
                            Intent intent = new Intent(getActivity(), FriendsActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        } else if (tabId == R.id.tab_chat) {
                            Intent intent = new Intent(getActivity(), InboxActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        }
                        break;
                    //If user is inside the Chat tab
                    case 2:
                        if (tabId == R.id.tab_events) {
                            Intent intent = new Intent(getActivity(), EventsActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        } else if (tabId == R.id.tab_profile) {
                            Intent intent = new Intent(getActivity(), ProfileActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        } else if (tabId == R.id.tab_friends) {
                            Intent intent = new Intent(getActivity(), FriendsActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        }
                        break;
                    //If user is inside the Friends tab
                    case 3:
                        if (tabId == R.id.tab_events) {
                            Intent intent = new Intent(getActivity(), EventsActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        } else if (tabId == R.id.tab_profile) {
                            Intent intent = new Intent(getActivity(), ProfileActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        } else if (tabId == R.id.tab_chat) {
                            Intent intent = new Intent(getActivity(), InboxActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        }
                        break;

                    //If user is in the Profile tab
                    case 4:
                        if (tabId == R.id.tab_events) {
                            Intent intent = new Intent(getActivity(), EventsActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        } else if (tabId == R.id.tab_friends) {
                            Intent intent = new Intent(getActivity(), FriendsActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        } else if (tabId == R.id.tab_chat) {
                            Intent intent = new Intent(getActivity(), InboxActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                        }
                        break;
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }




}
