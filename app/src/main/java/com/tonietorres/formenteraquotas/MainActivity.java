package com.tonietorres.formenteraquotas;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.tonietorres.formenteraquotas.data.QuotaContact.DaysTable;
import com.tonietorres.formenteraquotas.data.QuotaContact.RegistroTable;
import com.tonietorres.formenteraquotas.data.QuotaDbHelper;
import com.tonietorres.formenteraquotas.dialogs.DatePickerFragment;
import com.tonietorres.formenteraquotas.dialogs.NewBookingFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NewBookingFragment.OnCompleteListener {

    private static final String LOGIN = "admin";

    private SQLiteDatabase mDb;
    private TextView tvFecha;
    private TextView paxHalfNine;
    private TextView paxHalfTen;
    private TextView paxQuarterFive;
    private TextView paxHalfSix;
    private TextView quotaHalfNine;
    private TextView quotaHalfTen;
    private TextView quotaQuarterFive;
    private TextView quotaHalfSix;
    private int leftNine;
    private int leftTen;
    private int leftFive;
    private int leftSix;

    private NewBookingFragment newBookingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuotaDbHelper quotaDbHelper = new QuotaDbHelper(this);
        mDb = quotaDbHelper.getWritableDatabase();
        newDataBase();
        paxHalfNine = findViewById(R.id.paxHalfNine);
        paxHalfTen = findViewById(R.id.paxHalfTen);
        paxQuarterFive = findViewById(R.id.paxQuarterFive);
        paxHalfSix = findViewById(R.id.paxHalfSix);
        quotaHalfNine = findViewById(R.id.quotaHalfNine);
        quotaHalfTen = findViewById(R.id.quotaHalfTen);
        quotaQuarterFive = findViewById(R.id.quotaQuarterFive);
        quotaHalfSix = findViewById(R.id.quotaHalfSix);
        tvFecha = findViewById(R.id.date);
        establecerFecha();
        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        String fecha = tvFecha.getText().toString();

        populateFields(fecha);
    }

    public void registro(View view) {
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    private void populateFields(String fecha) {
        Cursor cursor = mDb.query(
                DaysTable.TABLE_NAME,
                null,
                DaysTable.DATE_COLUMN + "=?",
                new String[]{fecha},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            Log.d("DATE", fecha + " FOUND");
            String paxNine = cursor.getString(cursor.getColumnIndex(DaysTable.PAX_HALF_NINE_COLUMN));
            String paxTen =cursor.getString(cursor.getColumnIndex(DaysTable.PAX_HALF_TEN_COLUMN));
            String paxFive = cursor.getString(cursor.getColumnIndex(DaysTable.PAX_QUARTER_FIVE_COLUMN));
            String paxSix = cursor.getString(cursor.getColumnIndex(DaysTable.PAX_HALF_SIX_COLUMN));
            String quotaNine = cursor.getString(cursor.getColumnIndex(DaysTable.QUOTA_HALF_NINE_COLUMN));
            String quotaTen = cursor.getString(cursor.getColumnIndex(DaysTable.QUOTA_HALF_TEN_COLUMN));
            String quotaFive = cursor.getString(cursor.getColumnIndex(DaysTable.QUOTA_QUARTER_FIVE_COLUMN));
            String quotaSix = cursor.getString(cursor.getColumnIndex(DaysTable.QUOTA_HALF_SIX_COLUMN));

            leftNine = (new Integer(quotaNine)) - (new Integer(paxNine));
            leftTen = (new Integer(quotaTen)) - (new Integer(paxTen));
            leftFive = (new Integer(quotaFive) - (new Integer(paxFive)));
            leftSix = (new Integer(quotaSix) - (new Integer(paxSix)));

            paxHalfNine.setText(paxNine);
            paxHalfTen.setText(paxTen);
            paxQuarterFive.setText(paxFive);
            paxHalfSix.setText(paxSix);
            quotaHalfNine.setText(quotaNine);
            quotaHalfTen.setText(quotaTen);
            quotaQuarterFive.setText(quotaFive);
            quotaHalfSix.setText(quotaSix);
        } else {
            Log.d("DATE", fecha + " NOT FOUND");
        }
    }

    private void establecerFecha(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        tvFecha.setText(day+"-"+(month+1)+"-"+year);
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                final String selectedDate = day + "-" + (month+1) + "-" + year;
                tvFecha.setText(selectedDate);
                populateFields(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void newBooking(View view) {


        newBookingFragment = NewBookingFragment.newInstance(this, tvFecha.getText().toString(), leftNine, leftTen, leftFive, leftSix);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.new_booking_fragment_container, newBookingFragment)
                .commit();

    }

    @Override
    public void onCompleteBooking(String fecha, String paxNine, String paxTen, String paxFive, String paxSix) {
        if (!(paxNine==null && paxTen==null && paxFive==null && paxSix==null)) {
            ContentValues cv = new ContentValues();
            ContentValues registroCv = new ContentValues();
            if (paxNine != null && paxNine.length()>0) {
                cv.put(DaysTable.PAX_HALF_NINE_COLUMN, (Integer.valueOf(this.paxHalfNine.getText().toString()) + Integer.valueOf(paxNine)));
                registroCv.put(RegistroTable.PAX_HALF_NINE_COLUMN, (Integer.valueOf(this.paxHalfNine.getText().toString()) + Integer.valueOf(paxNine)));
            }
            if (paxTen != null && paxTen.length()>0) {
                cv.put(DaysTable.PAX_HALF_TEN_COLUMN, (Integer.valueOf(this.paxHalfTen.getText().toString()) + Integer.valueOf(paxTen)));
                registroCv.put(RegistroTable.PAX_HALF_TEN_COLUMN, (Integer.valueOf(this.paxHalfTen.getText().toString()) + Integer.valueOf(paxTen)));
            }
            if (paxFive != null && paxFive.length()>0) {
                cv.put(DaysTable.PAX_QUARTER_FIVE_COLUMN, (Integer.valueOf(this.paxQuarterFive.getText().toString()) + Integer.valueOf(paxFive)));
                registroCv.put(RegistroTable.PAX_QUARTER_FIVE_COLUMN, (Integer.valueOf(this.paxQuarterFive.getText().toString()) + Integer.valueOf(paxFive)));
            }
            if (paxSix != null && paxSix.length()>0) {
                cv.put(DaysTable.PAX_HALF_SIX_COLUMN, (Integer.valueOf(this.paxHalfSix.getText().toString()) + Integer.valueOf(paxSix)));
                registroCv.put(RegistroTable.PAX_HALF_SIX_COLUMN, (Integer.valueOf(this.paxHalfSix.getText().toString()) + Integer.valueOf(paxSix)));
            }
            registroCv.put(RegistroTable.DATE_COLUMN, fecha);
            registroCv.put(RegistroTable.LOGIN_COLUMN, LOGIN);
            mDb.update(DaysTable.TABLE_NAME, cv, DaysTable.DATE_COLUMN + "=?", new String[]{fecha});
            mDb.insert(RegistroTable.TABLE_NAME, null, registroCv);
        }
        populateFields(fecha);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .remove(newBookingFragment)
                .commit();
    }


    private void newDataBase() {
        Cursor cursor = mDb.query(
                DaysTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.getCount() == 0) {
            Log.d("TESTING", "BD VACIA, GENERANDO CAMPOS");
            for (int m = 5; m <= 10; m++) {
                int daysMonth = 31;
                if (m == 6 || m == 9) {
                    daysMonth = 30;
                }
                for (int d = 0; d <= daysMonth; d++) {
                    ContentValues cv = new ContentValues();
                    cv.put(DaysTable.DATE_COLUMN, d + "-" + m + "-2018");
                    cv.put(DaysTable.QUOTA_HALF_NINE_COLUMN, 250);
                    cv.put(DaysTable.QUOTA_HALF_TEN_COLUMN, 250);
                    cv.put(DaysTable.QUOTA_QUARTER_FIVE_COLUMN, 250);
                    cv.put(DaysTable.QUOTA_HALF_SIX_COLUMN, 250);
                    cv.put(DaysTable.PAX_HALF_NINE_COLUMN, 0);
                    cv.put(DaysTable.PAX_HALF_TEN_COLUMN, 0);
                    cv.put(DaysTable.PAX_QUARTER_FIVE_COLUMN, 0);
                    cv.put(DaysTable.PAX_HALF_SIX_COLUMN, 0);
                    mDb.insert(DaysTable.TABLE_NAME, null, cv);
                }
            }
        }
    }



}
