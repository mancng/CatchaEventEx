package com.mancng.catchaeventex.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

public class DatePickerDialogFragment extends BaseDialog implements DatePickerDialog.OnDateSetListener {

    public static final int DATE_PICKER_FROM = 0;
    public static final int DATE_PICKER_TO = 1;

    public static DatePickerDialog.OnDateSetListener from_dateListener, to_dateListener;

    DatePickerDialog startDatePickerDialog, endDatePickerDialog;

    DatePickerData datePickerData;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Use the current date as the default date in the picker
        DateTime defaultStartTimeInt = DateTime.now();
        String mEventTimeZone = String.valueOf(defaultStartTimeInt.getZone());
        DateTimeZone zone = DateTimeZone.forID(mEventTimeZone);
        DateTime dt = DateTime.now(zone);

        int mYear = dt.getYear();
        int mMonth = dt.getMonthOfYear() - 1;
        int mDay = dt.getDayOfMonth();

        long minimumDate = dt.getMillis();
        long maximumDate = minimumDate + 604800000;

        Integer dialogId = getArguments().getInt("datePicker");
        Integer getStartYear = getArguments().getInt("endPickerStartYear");
        Integer getStartMonth = getArguments().getInt("endPickerStartMonth");
        Integer getStartDay = getArguments().getInt("endPickerStartDay");

        if (dialogId == DATE_PICKER_FROM) {
            from_dateListener = (DatePickerDialog.OnDateSetListener) getTargetFragment();
            startDatePickerDialog = new DatePickerDialog(getActivity(), from_dateListener, mYear, mMonth, mDay);
            startDatePickerDialog.getDatePicker().setMinDate(minimumDate);
            startDatePickerDialog.getDatePicker().setMaxDate(maximumDate);
            return startDatePickerDialog;

        } else if (dialogId == DATE_PICKER_TO) {
            to_dateListener = (DatePickerDialog.OnDateSetListener) getTargetFragment();
            endDatePickerDialog = new DatePickerDialog(getActivity(), to_dateListener, getStartYear, getStartMonth - 1, getStartDay);
            endDatePickerDialog.getDatePicker().setMinDate(minimumDate);
            endDatePickerDialog.getDatePicker().setMaxDate(maximumDate);
            return endDatePickerDialog;
        }
        return null;
    }

    @Override
    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
        DateTime selectedDate = new DateTime(selectedYear, selectedMonth, selectedDayOfMonth, 0, 0, 0);
        passDate(selectedDate);

    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        datePickerData = (DatePickerData) childFragment;
    }

    public void passDate(DateTime dateTime) {
        datePickerData.DateInString(dateTime);
    }

    public interface DatePickerData {
        public void DateInString(DateTime date);
    }
}
