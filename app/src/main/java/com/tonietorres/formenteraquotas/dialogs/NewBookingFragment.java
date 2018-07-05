package com.tonietorres.formenteraquotas.dialogs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tonietorres.formenteraquotas.R;

/**
 * Created by Toni on 30/06/2018.
 */

public class NewBookingFragment extends Fragment {

    private OnCompleteListener listener;
    private String fecha;
    private int leftNine;
    private int leftTen;
    private int leftFive;
    private int leftSix;

    public static interface OnCompleteListener {
        public abstract void onCompleteBooking(String fecha, String paxNine, String paxTen, String paxFive, String paxSix);
    }

    public OnCompleteListener getListener() {
        return this.listener;
    }

    /**
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnCompleteListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " debe implementar OnCompleteListener!!!!!");
        }
    }
    **/

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
        View rootView = inflater.inflate(R.layout.fragment_new_booking, container, false);

        ((TextView) rootView.findViewById(R.id.date)).setText(fecha);
        ((TextView) rootView.findViewById(R.id.quotaHalfNine)).setText("/" + leftNine);
        ((TextView) rootView.findViewById(R.id.quotaHalfTen)).setText("/" + leftTen);
        ((TextView) rootView.findViewById(R.id.quotaQuarterFive)).setText("/" + leftFive);
        ((TextView) rootView.findViewById(R.id.quotaHalfSix)).setText("/" + leftSix);
        final TextView paxNine =  rootView.findViewById(R.id.paxHalfNine);
        paxNine.setText(String.valueOf(0));
        final TextView paxTen = rootView.findViewById(R.id.paxHalfTen);
        paxTen.setText(String.valueOf(0));
        final TextView paxFive = rootView.findViewById(R.id.paxQuarterFive);
        paxFive.setText(String.valueOf(0));
        final TextView paxSix = rootView.findViewById(R.id.paxHalfSix);
        paxSix.setText(String.valueOf(0));

        (rootView.findViewById(R.id.completeBooking)).setOnClickListener(new View.OnClickListener() {
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

        (rootView.findViewById(R.id.addHalfNine)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                if (paxNine.getText().length()!=0) {
                    num = Integer.valueOf(paxNine.getText().toString());
                }
                if (num < leftFive) {
                    num++;
                    paxNine.setText(String.valueOf(num));
                }
            }
        });
        (rootView.findViewById(R.id.addHalfTen)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                if (paxTen.getText().length()!=0) {
                    num = Integer.valueOf(paxTen.getText().toString());
                }
                if (num < leftTen) {
                    num++;
                    paxTen.setText(String.valueOf(num));
                }
            }
        });
        (rootView.findViewById(R.id.addQuarterFive)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                if (paxFive.getText().length()!=0) {
                    num = Integer.valueOf(paxFive.getText().toString());
                }
                if (num < leftFive) {
                    num++;
                    paxFive.setText(String.valueOf(num));
                }
            }
        });
        (rootView.findViewById(R.id.addHalfSix)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                if (paxSix.getText().length()!=0) {
                    num = Integer.valueOf(paxSix.getText().toString());
                }
                if (num < leftSix) {
                    num++;
                    paxSix.setText(String.valueOf(num));
                }
            }
        });
        (rootView.findViewById(R.id.subsHalfNine)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                if (paxNine.getText().length()!=0) {
                    num = Integer.valueOf(paxNine.getText().toString());
                }
                if (num > 0) {
                    num--;
                    paxNine.setText(String.valueOf(num));
                } else {
                    paxNine.setText(String.valueOf(0));
                }
            }
        });
        (rootView.findViewById(R.id.subsHalfTen)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int num = 0;
                if (paxTen.getText().length()!=0) {
                    num = Integer.valueOf(paxTen.getText().toString());
                }
                if (num > 0) {
                    num--;
                    paxTen.setText("" + num);
                } else {
                    paxTen.setText("" + 0);
                }
            }
        });
        (rootView.findViewById(R.id.subsQuarterFive)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                if (paxFive.getText().length()!=0) {
                    num = Integer.valueOf(paxFive.getText().toString());
                }
                if (num > 0) {
                    num--;
                    paxFive.setText(String.valueOf(num));
                } else {
                    paxFive.setText(String.valueOf(0));
                }
            }
        });
        (rootView.findViewById(R.id.subsHalfSix)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                if (paxSix.getText().length()!=0) {
                    num = Integer.valueOf(paxSix.getText().toString());
                }
                if (num > 0) {
                    num--;
                    paxSix.setText(String.valueOf(num));
                } else {
                    paxSix.setText(String.valueOf(0));
                }
            }
        });

        EditText paxNineEt = rootView.findViewById(R.id.paxHalfNine);
        paxNineEt.setFilters(new InputFilter[]{new InputFilterMinMax(0, leftNine)});
        EditText paxTenEt = rootView.findViewById(R.id.paxHalfTen);
        paxTenEt.setFilters(new InputFilter[]{new InputFilterMinMax(0, leftTen)});
        EditText paxFiveEt = rootView.findViewById(R.id.paxQuarterFive);
        paxFiveEt.setFilters(new InputFilter[]{new InputFilterMinMax(0, leftFive)});
        EditText PaxSixEt = rootView.findViewById(R.id.paxHalfSix);
        PaxSixEt.setFilters(new InputFilter[]{new InputFilterMinMax(0, leftSix)});


        return rootView;
    }

    class InputFilterMinMax implements InputFilter {
        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

}
