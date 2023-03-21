package com.example.c196.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Entities.UserModel;
import com.example.c196.R;
import com.example.c196.Utility;

import java.util.List;

public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    Context context;
    List<UserModel> list;

    public UserAdapter(Context context, List<UserModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.name.setText(Utility.capitalizeString(list.get(position).getName()));
        holder.username.setText(Utility.capitalizeString(list.get(position).getUsername()));
        holder.password.setText(list.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        TextView name, username, password;
        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.rv_owner);
            username = itemView.findViewById(R.id.rv_title);
            password = itemView.findViewById(R.id.rv_start_date);

        }
    }
}
