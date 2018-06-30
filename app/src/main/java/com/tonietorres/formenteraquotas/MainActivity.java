package com.tonietorres.formenteraquotas;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.tonietorres.formenteraquotas.data.QuotaContact.DaysTable;
import com.tonietorres.formenteraquotas.data.QuotaDbHelper;
import com.tonietorres.formenteraquotas.utils.DatePickerFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    TextView tvFecha;
    TextView paxHalfNine;
    TextView paxHalfTen;
    TextView paxQuarterFive;
    TextView paxHalfSix;
    TextView quotaHalfNine;
    TextView quotaHalfTen;
    TextView quotaQuarterFive;
    TextView quotaHalfSix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuotaDbHelper quotaDbHelper = new QuotaDbHelper(this);
        mDb = quotaDbHelper.getWritableDatabase();
        testField();
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
            paxHalfNine.setText(cursor.getString(cursor.getColumnIndex(DaysTable.PAX_HALF_NINE_COLUMN)));
            paxHalfTen.setText(cursor.getString(cursor.getColumnIndex(DaysTable.PAX_HALF_TEN_COLUMN)));
            paxQuarterFive.setText(cursor.getString(cursor.getColumnIndex(DaysTable.PAX_QUARTER_FIVE_COLUMN)));
            paxHalfSix.setText(cursor.getString(cursor.getColumnIndex(DaysTable.PAX_HALF_SIX_COLUMN)));
            quotaHalfNine.setText(cursor.getString(cursor.getColumnIndex(DaysTable.QUOTA_HALF_NINE_COLUMN)));
            quotaHalfTen.setText(cursor.getString(cursor.getColumnIndex(DaysTable.QUOTA_HALF_TEN_COLUMN)));
            quotaQuarterFive.setText(cursor.getString(cursor.getColumnIndex(DaysTable.QUOTA_QUARTER_FIVE_COLUMN)));
            quotaHalfSix.setText(cursor.getString(cursor.getColumnIndex(DaysTable.QUOTA_HALF_SIX_COLUMN)));
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

    private void testField(){
        ContentValues cv = new ContentValues();
        cv.put(DaysTable.PAX_QUARTER_FIVE_COLUMN, 99);
        mDb.update(DaysTable.TABLE_NAME,cv,DaysTable.DATE_COLUMN+"=?",new String[]{"8-5-2018"});
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
