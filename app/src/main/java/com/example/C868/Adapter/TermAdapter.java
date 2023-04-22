package com.example.c196.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Entities.TermModel;
import com.example.c196.R;
import com.example.c196.Utility;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    Context context;
    List<TermModel> termList;

    public TermAdapter(Context context, List<TermModel> termList) {
        this.context = context;
        this.termList = termList;
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TermViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {

        holder.title.setText(Utility.capitalizeString(termList.get(position).getTermTitle()));
        holder.username.setText(Utility.capitalizeString(termList.get(position).getUsername()));
        holder.startDate.setText(termList.get(position).getTermStartDate());
        holder.endDate.setText(termList.get(position).getTermEndDate());

    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

    class TermViewHolder extends RecyclerView.ViewHolder{

        TextView title, username, startDate, endDate;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rv_title);
            username = itemView.findViewById(R.id.rv_owner);
            startDate = itemView.findViewById(R.id.rv_start_date);
            endDate = itemView.findViewById(R.id.rv_end_date);

        }
    }
}
