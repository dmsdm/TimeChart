package com.example.timechart.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static DatePickerDialogFragment newInstance(String id) {
        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    private OnDateSetListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getContext(), this, year, month, day);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDateSetListener) {
            listener = (OnDateSetListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (listener != null) {
            listener.onDateSet(getArguments().getString("id"), year, month, dayOfMonth);
        }
    }

    public interface OnDateSetListener {
        void onDateSet(String id, int year, int month, int dayOfMonth);
    }
}