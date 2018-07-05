package com.tonietorres.formenteraquotas.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tonietorres.formenteraquotas.R;
import com.tonietorres.formenteraquotas.data.QuotaContact.RegistroTable;

public class RegistroAdapter  extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public RegistroAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }



    @Override
    public RegistroViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.registro_item, viewGroup, false);

        return new RegistroViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RegistroViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        String fecha = mCursor.getString(mCursor.getColumnIndex(RegistroTable.DATE_COLUMN));
        String login = mCursor.getString(mCursor.getColumnIndex(RegistroTable.LOGIN_COLUMN));
        String paxNine = mCursor.getString(mCursor.getColumnIndex(RegistroTable.PAX_HALF_NINE_COLUMN));
        String paxTen = mCursor.getString(mCursor.getColumnIndex(RegistroTable.PAX_HALF_TEN_COLUMN));
        String paxFive = mCursor.getString(mCursor.getColumnIndex(RegistroTable.PAX_QUARTER_FIVE_COLUMN));
        String paxSix = mCursor.getString(mCursor.getColumnIndex(RegistroTable.PAX_HALF_SIX_COLUMN));
        String fechaRes = mCursor.getString(mCursor.getColumnIndex(RegistroTable.FECHA_RES_COLUMN));
        String horaRes = mCursor.getString(mCursor.getColumnIndex(RegistroTable.HORA_RES_COLUMN));
        int active = mCursor.getInt(mCursor.getColumnIndex(RegistroTable.ACTIVE_COLUMN));

        holder.tvFecha.setText(fecha);
        holder.tvPaxNine.setText(paxNine);
        holder.tvPaxTen.setText(paxTen);
        holder.tvPaxFive.setText(paxFive);
        holder.tvPaxSix.setText(paxSix);
        holder.tvLogin.setText(login);
        holder.tvFechaRes.setText(fechaRes.substring(0, fechaRes.lastIndexOf(" ")));
        holder.tvHoraRes.setText(horaRes.substring(fechaRes.lastIndexOf(" ")+1, fechaRes.length()));

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


    class RegistroViewHolder extends RecyclerView.ViewHolder {

        TextView tvFecha;
        TextView tvPaxNine;
        TextView tvPaxTen;
        TextView tvPaxFive;
        TextView tvPaxSix;
        TextView tvFechaRes;
        TextView tvHoraRes;
        TextView tvLogin;
        ImageButton borrar;

        public RegistroViewHolder(View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tv_fecha);
            tvPaxNine = itemView.findViewById(R.id.tv_pax_nine);
            tvPaxTen = itemView.findViewById(R.id.tv_pax_ten);
            tvPaxFive = itemView.findViewById(R.id.tv_pax_five);
            tvPaxSix = itemView.findViewById(R.id.tv_pax_six);
            tvFechaRes = itemView.findViewById(R.id.tv_fecha_res);
            tvHoraRes = itemView.findViewById(R.id.tv_hora_res);
            tvLogin = itemView.findViewById(R.id.tv_login);
            borrar = itemView.findViewById(R.id.borrar_reserva);
        }
    }
}
