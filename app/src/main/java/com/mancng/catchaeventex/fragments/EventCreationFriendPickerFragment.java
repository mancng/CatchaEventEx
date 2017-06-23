package com.mancng.catchaeventex.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mancng.catchaeventex.Infrastructure.Utils;
import com.mancng.catchaeventex.R;
import com.mancng.catchaeventex.activities.BaseFragmentActivity;
import com.mancng.catchaeventex.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class EventCreationFriendPickerFragment extends BaseFragment implements EventCreationFriendPickerAdapter.UserClickedListener {

    @BindView(R2.id.fragment_friends_picker_searchBar)
    EditText mSearchBarEt;

    @BindView(R2.id.fragment_friends_picker_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R2.id.fragment_friends_picker_noResults)
    TextView mTextView;

    @BindView(R2.id.fragment_friends_picker_done)
    Button mDoneBtn;

    FragmentManager mFragmentManager;

    private DatabaseReference mGetAllCurrentUserFriendsReference;
    private ValueEventListener mGetAllCurrentUserFriendsListener;

    private Unbinder mUnbinder;

    private String mCurrentUserEmailString;
    private EventCreationFriendPickerAdapter mEventCreationFriendPickerAdapter;

    private LiveFriendServices mLiveFriendServices;

    public HashMap<String, User> mPickedFriendsMap;

    private PublishSubject<String> mSearchBarString;


    public static EventCreationFriendPickerFragment newInstance() {
        return new EventCreationFriendPickerFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUserEmailString = mSharedPreferences.getString(Utils.USER_EMAIL, "");
        mLiveFriendServices = LiveFriendServices.getInstance();
        mPickedFriendsMap = new HashMap<>();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends_picker, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        mUserFriends = new ArrayList<>();
        mFragmentManager = getFragmentManager();
        mEventCreationFriendPickerAdapter = new EventCreationFriendPickerAdapter((BaseFragmentActivity) getActivity(), this);

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mGetAllCurrentUserFriendsListener = getAllUserFriends(mEventCreationFriendPickerAdapter, mCurrentUserEmailString);

        //Get database reference from Firebase node to get all userFriends
        mGetAllCurrentUserFriendsReference = FirebaseDatabase.getInstance().getReference()
                .child(Utils.FIRE_BASE_PATH_USER_FRIENDS)
                .child(Utils.encodeEmail(mCurrentUserEmailString));
        mGetAllCurrentUserFriendsReference.addValueEventListener(mGetAllCurrentUserFriendsListener);

        //Set mRecyclerView to show all user friends
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mEventCreationFriendPickerAdapter);

        mCompositeDisposable.add(createSearchBarSubscription());
        listenToSearchBar();
    }


    //Create subscription for SearchBar
    private DisposableObserver<List<User>> createSearchBarSubscription() {

        mSearchBarString = PublishSubject.create();
        return mSearchBarString
                //set delay
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<User>>() {
                    @Override
                    public List<User> apply(String searchString) { //String will be user email strings in the system
                        return mLiveFriendServices.getMatchingUsers(mUserFriends, searchString, searchString);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<User>>() {
                    @Override
                    public void onNext(List<User> users) {
                        //Set spinnerAdapter for the list of users
                        if (users.isEmpty()) {
                            mTextView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        } else if (mSearchBarEt.getText().toString().isEmpty()) {
                            mRecyclerView.setVisibility(View.GONE);
                        } else {
                            mTextView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                        mEventCreationFriendPickerAdapter.setUserFriends(users);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    //Set a listener to the SearchBar and grab user input
    private void listenToSearchBar() {
        mSearchBarEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchBarString.onNext(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick(R2.id.fragment_friends_picker_done)
    public void setDoneButton() {

        mFragmentManager.popBackStack();

        mFragmentManager.beginTransaction()
                .remove(EventCreationFriendPickerFragment.this)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

        if (mGetAllCurrentUserFriendsListener != null) {
            mGetAllCurrentUserFriendsReference.removeEventListener(mGetAllCurrentUserFriendsListener);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}