package com.example.peonline.studentmain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peonline.R;
import com.example.peonline.teachermain.Assignment;

import java.util.List;

public class RecycleVewAdaptor extends RecyclerView.Adapter<RecycleVewAdaptor.Viewholder> {

    private List<Assignment> assignmentList;

    public RecycleVewAdaptor(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {
        String title = assignmentList.get(position).getAss_title();
        String body = assignmentList.get(position).getAss_body();

        viewholder.setData(title,body);
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView body;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Assignment_title);
            body = itemView.findViewById(R.id.Assignment_body);
        }

        private void setData(String titleText, String bodyText) {
            title.setText(titleText);
            body.setText(bodyText);


        }
    }
}
