package com.project.zhimer.studentdesk.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.zhimer.studentdesk.R;
import com.project.zhimer.studentdesk.model.Tagihan;

import java.util.List;

public class KeuanganAdapter extends RecyclerView.Adapter<KeuanganAdapter.ViewHolder> {

    private Context context;
    private List<Tagihan> tagihanList;
    private LayoutInflater inflater;

    public KeuanganAdapter(List<Tagihan> tagihanList, Context context) {
        this.tagihanList = tagihanList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public KeuanganAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_keuangan, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeuanganAdapter.ViewHolder holder, int position) {
        final Tagihan tagihan = tagihanList.get(position);

        if (tagihan.getSemester().equals("1")) {
            holder.semester.setText("Ganjil");
        }
        else if (tagihan.getSemester().equals("2")) {
            holder.semester.setText("Genap");
        }else {
            holder.semester.setText("konversi");
        }


        holder.biaya.setText(tagihan.getBiaya());
        holder.potongan.setText(tagihan.getPotongan());
        holder.bayar.setText(tagihan.getBayar());

        if (tagihan.getStatus().equals("0")) {
            holder.status.setText("Lunas");
            holder.status.setTextColor(Color.BLUE);
        } else {
            holder.status.setText("Kurang");
            holder.status.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return tagihanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView semester, biaya, potongan, bayar, status;

        public ViewHolder(View itemView) {
            super(itemView);

            semester = itemView.findViewById(R.id.tvSemester);
            biaya = itemView.findViewById(R.id.tvBiaya);
            potongan = itemView.findViewById(R.id.tvPotongan);
            bayar = itemView.findViewById(R.id.tvBayar);
            status = itemView.findViewById(R.id.tvStatus);
        }
    }
}
