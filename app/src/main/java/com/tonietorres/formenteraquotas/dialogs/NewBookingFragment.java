package com.tonietorres.formenteraquotas.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tonietorres.formenteraquotas.R;

/**
 * Created by Toni on 30/06/2018.
 */

public class NewBookingFragment extends DialogFragment {

    private OnCompleteListener listener;
    private String fecha;
    private int leftNine;
    private int leftTen;
    private int leftFive;
    private int leftSix;
    private TextView paxNine;
    private TextView paxTen;
    private TextView paxFive;
    private TextView paxSix;

    public static interface OnCompleteListener {
        public abstract void onCompleteBooking(String fecha, String paxNine, String paxTen, String paxFive, String paxSix);
    }

    public OnCompleteListener getListener() {
        return this.listener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnCompleteListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " debe implementar OnCompleteListener!!!!!");
        }
    }
    private static OnCompleteListener sDummyCallbacks = new OnCompleteListener() {
        @Override
        public void onCompleteBooking(String fecha, String paxNine, String paxTen, String paxFive, String paxSix) {
        }
    };

    public static NewBookingFragment newInstance(OnCompleteListener listener, String fecha, int leftNine, int leftTen, int leftFive, int leftSix) {
        NewBookingFragment fragment = new NewBookingFragment();
        fragment.listener = listener;
        fragment.fecha = fecha;
        fragment.leftNine = leftNine;
        fragment.leftTen = leftTen;
        fragment.leftFive = leftFive;
        fragment.leftSix = leftSix;
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_booking, container, false);
        ((TextView) view.findViewById(R.id.date)).setText(fecha);
        ((TextView) view.findViewById(R.id.quotaHalfNine)).setText("/" + leftNine);
        ((TextView) view.findViewById(R.id.quotaHalfTen)).setText("/" + leftTen);
        ((TextView) view.findViewById(R.id.quotaQuarterFive)).setText("/" + leftFive);
        ((TextView) view.findViewById(R.id.quotaHalfSix)).setText("/" + leftSix);
        paxNine = (TextView) view.findViewById(R.id.paxHalfNine);
        paxNine.setText("" + 0);
        paxTen = (TextView) view.findViewById(R.id.paxHalfTen);
        paxTen.setText("" + 0);
        paxFive = (TextView) view.findViewById(R.id.paxQuarterFive);
        paxFive.setText("" + 0);
        paxSix = (TextView) view.findViewById(R.id.paxHalfSix);
        paxSix.setText("" + 0);

        (view.findViewById(R.id.completeBooking)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nine = null;
                if(!paxNine.getText().toString().equals("0"))
                    nine = paxNine.getText().toString();
                String ten = null;
                if(!paxTen.getText().toString().equals("0"))
                    ten = paxTen.getText().toString();
                String five = null;
                if(!paxFive.getText().toString().equals("0"))
                    five = paxFive.getText().toString();
                String six = null;
                if(!paxSix.getText().toString().equals("0"))
                    six = paxSix.getText().toString();

                listener.onCompleteBooking(fecha, nine, ten, five, six);
                getActivity().getFragmentManager().popBackStack();
            }
        });

        (view.findViewById(R.id.addHalfNine)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = new Integer(paxNine.getText().toString());
                if (num < leftFive) {
                    num++;
                    paxNine.setText("" + num);
                }
            }
        });
        (view.findViewById(R.id.addHalfTen)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = new Integer(paxTen.getText().toString());
                if (num < leftTen) {
                    num++;
                    paxTen.setText("" + num);
                }
            }
        });
        (view.findViewById(R.id.addQuarterFive)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = new Integer(paxFive.getText().toString());
                if (num < leftFive) {
                    num++;
                    paxFive.setText("" + num);
                }
            }
        });
        (view.findViewById(R.id.addHalfSix)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = new Integer(paxSix.getText().toString());
                if (num < leftSix) {
                    num++;
                    paxSix.setText("" + num);
                }
            }
        });
        (view.findViewById(R.id.subsHalfNine)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = new Integer(paxNine.getText().toString());
                if (num > 0) {
                    num--;
                    paxNine.setText("" + num);
                }
            }
        });
        (view.findViewById(R.id.subsHalfTen)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = new Integer(paxTen.getText().toString());
                if (num > 0) {
                    num--;
                    paxTen.setText("" + num);
                }
            }
        });
        (view.findViewById(R.id.subsQuarterFive)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = new Integer(paxFive.getText().toString());
                if (num > 0) {
                    num--;
                    paxFive.setText("" + num);
                }
            }
        });
        (view.findViewById(R.id.subsHalfSix)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = new Integer(paxSix.getText().toString());
                if (num > 0) {
                    num--;
                    paxSix.setText("" + num);
                }
            }
        });
        return view;
    }


}
