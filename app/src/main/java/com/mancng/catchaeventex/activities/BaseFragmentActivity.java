package com.mancng.catchaeventex.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mancng.catchaeventex.Infrastructure.Utils;
import com.mancng.catchaeventex.R;

public abstract class BaseFragmentActivity extends AppCompatActivity {
    abstract Fragment createFragment();

    protected FirebaseAuth auth;
    protected FirebaseAuth.AuthStateListener authStateListener;
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    protected FirebaseAnalytics mFirebaseAnalytics;
    protected SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_base);

        //Crashlytics
        Fabric.with(this, new Crashlytics());

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mSharedPreferences = getSharedPreferences(Utils.USER_INFO_PREFERENCE,
                Context.MODE_PRIVATE);
        final String userName = mSharedPreferences.getString(Utils.USER_NAME, "");
        final String userEmail = mSharedPreferences.getString(Utils.USER_EMAIL, "");

        auth = FirebaseAuth.getInstance();

        if (!((this instanceof LoginActivity) || (this instanceof RegisterActivity || (this instanceof SplashActivity)))) {
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    if (user == null) {
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else if (userEmail.equals("")) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }

                }
            };
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_fragment_base_fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.activity_fragment_base_fragmentContainer, fragment)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!((this instanceof LoginActivity) || (this instanceof RegisterActivity || (this instanceof SplashActivity)))) {
            auth.addAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!((this instanceof LoginActivity) || (this instanceof RegisterActivity || (this instanceof SplashActivity)))) {
            auth.removeAuthStateListener(authStateListener);
        }

        mCompositeDisposable.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.find_friends:
                Bundle bundle = new Bundle();
                bundle.putString("find_friends_button", "from_action_bar");
                mFirebaseAnalytics.logEvent("find_friends_action_bar", bundle);

                startActivity(new Intent(getApplicationContext(), FindFriendsActivity.class));
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                return true;

            case R.id.app_invite:
                Bundle bundle2 = new Bundle();
                bundle2.putString("invite_friends_button", "from_action_bar");
                mFirebaseAnalytics.logEvent("invite_friends_action_bar", bundle2);

                startActivity(new Intent(getApplicationContext(), InviteAppActivity.class));
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                return true;

            case R.id.action_logout:
                //Make sure we clear the data stored in shared preferences
                mSharedPreferences.edit().putString(Utils.USER_PICTURE, "").apply();
                mSharedPreferences.edit().putString(Utils.USER_NAME, "").apply();
                mSharedPreferences.edit().putString(Utils.USER_EMAIL, "").apply();

                FirebaseAuth.getInstance().signOut();
                finish();
                return true;


            case R.id.action_add_event:
                Bundle bundle3 = new Bundle();
                bundle3.putString("event_creation_button", "from_action_bar");
                mFirebaseAnalytics.logEvent("create_event_action_bar", bundle3);

                startActivity(new Intent(getApplicationContext(), EventCreationActivity.class));
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
