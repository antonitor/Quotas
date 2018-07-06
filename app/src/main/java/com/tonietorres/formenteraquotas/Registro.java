package com.tonietorres.formenteraquotas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.tonietorres.formenteraquotas.data.QuotaContact;
import com.tonietorres.formenteraquotas.data.QuotaDbHelper;
import com.tonietorres.formenteraquotas.data.RegistroAdapter;
import com.tonietorres.formenteraquotas.data.utils;
import com.tonietorres.formenteraquotas.dialogs.DatePickerFragment;

public class Registro extends AppCompatActivity implements RegistroAdapter.ListItemClickListener{

    private RegistroAdapter mAdapter;
    private SQLiteDatabase mDb;
    private String mLogin;
    private String mFecha;
    private Button fechaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Intent intent = getIntent();
        if (intent.hasExtra("login")) {
            mLogin = intent.getStringExtra("login");
            Log.d("LOGIN ES ", mLogin);
        } else {
            mLogin = "a";
        }
        if(intent.hasExtra("fecha")){
            mFecha = intent.getStringExtra("fecha");
        } else {
            mFecha = utils.obtenerFechaDeHoy();
        }

        fechaButton = findViewById(R.id.fecha_button);
        fechaButton.setText(mFecha);
        fechaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        RecyclerView registroRecyclerView = (RecyclerView) this.findViewById(R.id.rv_registro);
        registroRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        QuotaDbHelper dbHelper = new QuotaDbHelper(this);
        mDb = dbHelper.getWritableDatabase();



        Cursor cursor = getAllRegistros();
        mAdapter = new RegistroAdapter(this, cursor, this);
        registroRecyclerView.setAdapter(mAdapter);
    }

    private Cursor getAllRegistros(){
        return mDb.query(QuotaContact.RegistroTable.TABLE_NAME,
                null,
                QuotaContact.RegistroTable.LOGIN_COLUMN + " = ? AND " + QuotaContact.RegistroTable.DATE_COLUMN + " = ?",
                new String[]{mLogin, mFecha},
                null,
                null,
                QuotaContact.RegistroTable.FECHA_RES_COLUMN + " desc");
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                final String selectedDate = day + "-" + (month+1) + "-" + year;
                mFecha = selectedDate;
                fechaButton.setText(selectedDate);
                mAdapter.swapCursor(getAllRegistros());
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

     @Override
    public void onListItemClick(String itemClickedId) {
        mDb.delete(QuotaContact.RegistroTable.TABLE_NAME, QuotaContact.RegistroTable._ID, new String[]{itemClickedId});
    }
}
