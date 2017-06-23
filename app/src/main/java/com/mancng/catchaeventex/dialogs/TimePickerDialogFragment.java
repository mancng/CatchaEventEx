package com.mancng.catchaeventex.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerDialogFragment extends BaseDialog implements TimePickerDialog.OnTimeSetListener {

    public static final int TIME_PICKER_FROM = 2;
    public static final int TIME_PICKER_TO = 3;

    public static TimePickerDialog.OnTimeSetListener from_timeListener, to_timeListener;

    TimePickerDialog startTimePickerDialog, endTimePickerDialog;

    TimePickerData timePickerData;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Use the round up of the current time as the default time in the picker

        DateTime defaultStartTimeInt = DateTime.now();
        String mEventTimeZone = String.valueOf(defaultStartTimeInt.getZone());
        DateTimeZone zone = DateTimeZone.forID(mEventTimeZone);
        DateTime dt = DateTime.now(zone).hourOfDay().roundCeilingCopy();

        int mHour = dt.getHourOfDay();
        int mMinute = dt.getMinuteOfHour();

        Integer dialogId = getArguments().getInt("timePicker");
        Integer endPickedHour = getArguments().getInt("startHour");
        Integer endPickedMinute = getArguments().getInt("startMinute");

        if (dialogId == TIME_PICKER_FROM) {
            from_timeListener = (TimePickerDialog.OnTimeSetListener) getTargetFragment();
            startTimePickerDialog = new TimePickerDialog(getActivity(), from_timeListener, mHour, mMinute, DateFormat.is24HourFormat(getActivity()));
            return startTimePickerDialog;

        } else if (dialogId == TIME_PICKER_TO) {
            to_timeListener = (TimePickerDialog.OnTimeSetListener) getTargetFragment();
            endTimePickerDialog = new TimePickerDialog(getActivity(), to_timeListener, endPickedHour, endPickedMinute, DateFormat.is24HourFormat(getActivity()));
            return endTimePickerDialog;
        }
        return null;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        DateTime defaultStartTimeInt = DateTime.now();
        String mEventTimeZone = String.valueOf(defaultStartTimeInt.getZone());
        DateTimeZone zone = DateTimeZone.forID(mEventTimeZone);
        DateTime selectedTime = new DateTime(zone);
        passTime(selectedTime);

    }


    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        timePickerData = (TimePickerData)childFragment;
    }

    public void passTime(DateTime dateTime) {
        timePickerData.TimeInString(dateTime);
    }

    public interface TimePickerData {
        public void TimeInString(DateTime date);
    }
}
