package com.example.juangui.easystoreapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Juan Gui on 01/03/2017.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private TextView fechaVencimiento;

    public DatePickerFragment() {
    }

    public void setFechaVencimiento(TextView fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // Use the current date as the default date in the picker

        final Calendar c = Calendar.getInstance();
        try {
            // Si en algun momento se ha informado la fecha se recupera
            String format = "MM-dd-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            c.setTime(sdf.parse(fechaVencimiento.getText().toString()));
        } catch (ParseException e) {
            // Si falla utilizaremos la fecha actual
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        try{
            final Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            String format = "MM-dd-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            fechaVencimiento.setText(sdf.format(c.getTime()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}