package com.tonietorres.formenteraquotas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tonietorres.formenteraquotas.data.QuotaContact;
import com.tonietorres.formenteraquotas.data.QuotaDbHelper;
import com.tonietorres.formenteraquotas.data.RegistroAdapter;

public class Registro extends AppCompatActivity {

    private RegistroAdapter mAdapter;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        RecyclerView registroRecyclerView = (RecyclerView) this.findViewById(R.id.rv_registro);
        registroRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        QuotaDbHelper dbHelper = new QuotaDbHelper(this);
        mDb = dbHelper.getReadableDatabase();
        Cursor cursor = getAllRegistros();
        mAdapter = new RegistroAdapter(this, cursor);
        registroRecyclerView.setAdapter(mAdapter);
    }

    private Cursor getAllRegistros(){
        return mDb.query(QuotaContact.RegistroTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                QuotaContact.RegistroTable.FECHA_RES_COLUMN + " desc");
    }
}
