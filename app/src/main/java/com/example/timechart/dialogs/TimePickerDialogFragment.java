package com.example.timechart.dialogs;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static TimePickerDialogFragment newInstance(String id) {
        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    private OnTimeSetListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getContext(), this, hour, minute,
                DateFormat.is24HourFormat(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimeSetListener) {
            listener = (OnTimeSetListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (listener != null) {
            listener.onTimeSet(getArguments().getString("id"), hourOfDay, minute);
        }
    }

    public interface OnTimeSetListener {
        void onTimeSet(String id, int hourOfDay, int minute);
    }
}