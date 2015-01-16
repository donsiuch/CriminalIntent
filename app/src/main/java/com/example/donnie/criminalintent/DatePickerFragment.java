package com.example.donnie.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by donnie on 1/11/15.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";

    private Date mDate;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // This must be called when this fragment dies. This will send whatever date has been chosen
    // to the target activity... CrimeFragment
    private void sendResult(int resultCode){
        if (getTargetFragment() == null){
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        // Get the date argument and extract year, month and day
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Get the view so we can do stuff to it then return it up.
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        // Doing stuff to view (1)
        // Have the date picker view reflect the current date to start
        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_picker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener(){
           public void onDateChanged(DatePicker view, int year, int month, int day){
               mDate = new GregorianCalendar(year, month, day).getTime();

               getArguments().putSerializable(EXTRA_DATE, mDate);
           }
        });

        // Doing stuff to view (2)
        // Then returning it
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)

                // When the user clicks OK, we run the sendResult method of this instance
                // which will send information back to the target fragment
                // If the button is click, we sendResult() that the fragment ended perfect and intended
                // .... which is indicated by Activity.RESULT_OK!!!
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }

}
