package com.mancng.catchaeventex.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.mancng.catchaeventex.Infrastructure.Utils;
import com.mancng.catchaeventex.R;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class DeleteEventDialogFragment extends BaseDialog implements View.OnClickListener {

    public static final String EVENT_DETAILS_EXTRA = "EVENT_DETAILS_EXTRA";

    private String mEventIdString;
    private String mEventName;
    private String mCurrentUserEmailString;


    public static DeleteEventDialogFragment newInstance (ArrayList<String> eventDetails) {

        //Get bundle from EventListCreatedFragment
        Bundle arguments = new Bundle();
        arguments.putStringArrayList(EVENT_DETAILS_EXTRA, eventDetails);

        //Pass argument to DeleteEventDialogFragment
        DeleteEventDialogFragment dialogFragment = new DeleteEventDialogFragment();
        dialogFragment.setArguments(arguments);

        return dialogFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentUserEmailString = mSharedPreferences.getString(Utils.USER_EMAIL, "");
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Create Dialog
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_delete_event, null))
                .setPositiveButton("Yes", null)
                .setNegativeButton("Cancel", null)
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(this);
        return dialog;
    }


    @Override
    public void onClick(View v) {

        //If user clicks yes
        Toast.makeText(getContext(), mEventName + " " + getString(R.string.delete_warning), Toast.LENGTH_SHORT).show();
        dismiss();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
