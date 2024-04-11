package com.example.diseaze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScanResultAdapter extends RecyclerView.Adapter<ScanResultAdapter.ViewHolder> {

    private Context context;
    private List<ScanResult> scanResultList;

    public ScanResultAdapter(Context context, List<ScanResult> scanResultList) {
        this.context = context;
        this.scanResultList = scanResultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_scan_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScanResult scanResult = scanResultList.get(position);
        holder.bind(scanResult);
    }

    @Override
    public int getItemCount() {
        return scanResultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTimestamp;
        private TextView tvClassName;
        private TextView tvConfidence;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvConfidence = itemView.findViewById(R.id.tvConfidence);
        }

        public void bind(ScanResult scanResult) {
            tvTimestamp.setText(String.valueOf(scanResult.getTimestamp()));
            tvClassName.setText(scanResult.getClassName());
            tvConfidence.setText(String.valueOf(scanResult.getConfidenceLevel()));
        }
    }
}
