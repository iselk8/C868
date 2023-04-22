package com.example.c196.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Database;
import com.example.c196.Entities.CourseModel;
import com.example.c196.R;
import com.example.c196.Utility;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    Context context;
    List<CourseModel> courseList;

    public CourseAdapter(Context context, List<CourseModel> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        String termTitle = Database.getDatabase(context.getApplicationContext()).getTermDAO().getTermByID(courseList.get(position).getTermId()).getTermTitle();
        holder.title.setText(Utility.capitalizeString(courseList.get(position).getCourseTitle()));
        holder.term.setText(Utility.capitalizeString(termTitle));
        holder.startDate.setText(courseList.get(position).getCourseStartDate());
        holder.endDate.setText(courseList.get(position).getCourseEndDate());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }


    class CourseViewHolder extends RecyclerView.ViewHolder{

        TextView title, term, startDate, endDate;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rv_title);
            term = itemView.findViewById(R.id.rv_owner);
            startDate = itemView.findViewById(R.id.rv_start_date);
            endDate = itemView.findViewById(R.id.rv_end_date);
        }
    }
}
