package com.tonietorres.formenteraquotas.dialogs;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonietorres.formenteraquotas.R;

/**
 * Created by Toni on 30/06/2018.
 */

public class NewBookingFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;

    public static NewBookingFragment newInstance(DatePickerDialog.OnDateSetListener listener){
        NewBookingFragment fragment = new NewBookingFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_booking, container, false);
    }

    public DatePickerDialog.OnDateSetListener getListener() {
        return this.listener;
    }
}
