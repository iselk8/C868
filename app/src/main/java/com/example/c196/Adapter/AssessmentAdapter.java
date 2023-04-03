package com.example.c196.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Database;
import com.example.c196.Entities.AssessmentModel;
import com.example.c196.R;
import com.example.c196.Utility;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    Context context;
    List<AssessmentModel> assessmentList;

    public AssessmentAdapter(Context context, List<AssessmentModel> assessmentList) {
        this.context = context;
        this.assessmentList = assessmentList;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssessmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        String courseTitle = Database.getDatabase(context.getApplicationContext()).getCourseDAO().getCourseNameByID(assessmentList.get(position).getCourseID());
        holder.title.setText(Utility.capitalizeString(assessmentList.get(position).getAssessmentTitle()));
        holder.course.setText(Utility.capitalizeString(courseTitle));
        holder.startDate.setText(assessmentList.get(position).getAssessmentType());
        holder.endDate.setText(assessmentList.get(position).getAssessmentDueDate());
    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder{

        TextView title, course, startDate, endDate;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rv_title);
            course = itemView.findViewById(R.id.rv_owner);
            startDate = itemView.findViewById(R.id.rv_start_date);
            endDate = itemView.findViewById(R.id.rv_end_date);
        }
    }
}
