package com.mancng.catchaeventex.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mancng.catchaeventex.Infrastructure.Utils;
import com.mancng.catchaeventex.activities.BaseFragmentActivity;

import org.json.JSONObject;

import java.net.Socket;
import java.net.URISyntaxException;

public class LoginFragment extends BaseFragment {

    @BindView(R2.id.activity_login_linear_layout)
    RelativeLayout mRelativeLayout;

    @BindView(R2.id.activity_login_registerButton)
    Button registerButton;

    @BindView(R2.id.activity_login_loginButton)
    Button loginButton;

    @BindView(R2.id.activity_login_userEmail)
    EditText userEmail;

    @BindView(R2.id.activity_login_userPassword)
    EditText userPassword;

    @BindView(R2.id.activity_login_logo)
    ImageView logo;

    @BindView(R2.id.activity_login_resetPassword)
    TextView resetPassword;

    private Unbinder mUnbinder;

    private Socket mSocket;

    private BaseFragmentActivity mBaseFragmentActivity;
    private LiveAccountServices mLiveAccountServices;
    protected FirebaseAuth auth;

    private Animation rotation;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make sure nobody is signed in for testing
//        FirebaseAuth.getInstance().signOut();

        try {
            mSocket = IO.socket(Utils.IP_HOST);
        } catch (URISyntaxException e) {
            FirebaseCrash.report(e);
            Log.i(LoginFragment.class.getSimpleName(), e.getMessage());
            Toast.makeText(getActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
        }

        mLiveAccountServices = LiveAccountServices.getInstance();
        mSocket.on("token", tokenListener());
        mSocket.connect();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_login, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }


    @OnClick(R2.id.activity_login_loginButton)
    public void setLoginButton() {

        Bundle bundle = new Bundle();
        bundle.putString("user_login_button", "from_LoginFragment");
        mFirebaseAnalytics.logEvent("user_login", bundle);

        rotation = AnimationUtils.loadAnimation(getContext(), R.anim.refresh_button_anim);
        rotation.setRepeatCount(Animation.INFINITE);

        logo.startAnimation(rotation);

        mCompositeDisposable.add(mLiveAccountServices.sendLoginfo(userEmail, userPassword, mSocket, mBaseFragmentActivity, rotation));
    }


    @OnClick(R2.id.activity_login_registerButton)
    public void setRegisterButton() {

        Bundle bundle = new Bundle();
        bundle.putString("go_register_button", "from_LoginFragment");
        mFirebaseAnalytics.logEvent("go_register", bundle);

        startActivity(new Intent(getActivity(), RegisterActivity.class));
    }


    //How we are going to grab the jsonObject from the server
    private Emitter.Listener tokenListener() {
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jsonObject = (JSONObject) args[0];
                mCompositeDisposable.add(mLiveAccountServices
                        .getAuthToken(jsonObject, mBaseFragmentActivity, mSharedPreferences, rotation));
            }
        };
    }


    @OnClick(R2.id.activity_login_resetPassword)
    public void setResetPassword() {

        DialogFragment dialogFragment = ResetPasswordDialogFragment.newInstance();
        dialogFragment.show(getFragmentManager(), ResetPasswordDialogFragment.class.getSimpleName());

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseFragmentActivity = (BaseFragmentActivity) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mBaseFragmentActivity = null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
        mSocket.disconnect();
    }
}