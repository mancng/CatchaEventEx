package com.mancng.catchaeventex.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mancng.catchaeventex.R;

import java.util.ArrayList;

public class EventDetailsFragment extends BaseFragment{

    public static final String EVENT_DETAILS_EXTRA = "EVENT_DETAILS_EXTRA";

    public static FragmentManager mFragmentManager;


    public static EventDetailsFragment newInstance(ArrayList<String> eventDetails) {

        //Get eventDetails bundle from EventDetailsActivity
        Bundle arguments = new Bundle();
        arguments.putStringArrayList(EVENT_DETAILS_EXTRA, eventDetails);

        //Set eventDetails bundle to EventDetailsFragment
        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        eventDetailsFragment.setArguments(arguments);

        return eventDetailsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_event_details, container, false);
        mFragmentManager = getFragmentManager();
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> eventDetails = getArguments().getStringArrayList(EVENT_DETAILS_EXTRA);

        Bundle eventDetailsData = new Bundle();
        eventDetailsData.putStringArrayList(EVENT_DETAILS_EXTRA, eventDetails);

        //AddEventDetailsDetailFragment
        EventDetailsDetailFragment eventDetailsDetailFragment = new EventDetailsDetailFragment();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.activity_event_details_layout, eventDetailsDetailFragment, "EventDetailsDetailFragment");
        eventDetailsDetailFragment.setArguments(eventDetailsData); //Pass bundle with fragment transaction
        transaction.commit();
    }


    public static void detachEventDetailsDetailFragment() {

        EventDetailsDetailFragment eventDetailsDetailFragment = (EventDetailsDetailFragment) mFragmentManager.findFragmentByTag("EventDetailsDetailFragment");
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if(eventDetailsDetailFragment != null) {
            transaction.detach(eventDetailsDetailFragment);
            transaction.addToBackStack("detachEventDetailsDetailFragment");
            transaction.commit();
        }
    }


    public static void attachEventDetailsDetailFragment() {

        EventDetailsDetailFragment eventDetailsDetailFragment = (EventDetailsDetailFragment) mFragmentManager.findFragmentByTag("EventDetailsDetailFragment");
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if(eventDetailsDetailFragment != null) {
            transaction.attach(eventDetailsDetailFragment);
            mFragmentManager.popBackStackImmediate();
            transaction.commit();
        }
    }
}