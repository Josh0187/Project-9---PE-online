package com.example.peonline.studentmain;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peonline.R;
import com.example.peonline.gps.GPSActivity;
import com.example.peonline.teachermain.Assignment;
import com.example.peonline.teachermain.Class;
import com.example.peonline.video.VideoSubmission;

import java.util.List;

public class RecycleVewAdaptorAssignments extends RecyclerView.Adapter<RecycleVewAdaptorAssignments.Viewholder>  {

    private TextView title;
    private TextView body;
    private List<Assignment> assignmentList;
    public RecycleVewAdaptorAssignments(List<Assignment> classlist) {
        this.assignmentList = classlist;
    }
    public View.OnClickListener mListner;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layoutassignmentview, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {
        String title = assignmentList.get(position).getAss_title();
        String body = assignmentList.get(position).getAss_body();

       // viewholder.checkBox_submit.setChecked(assignmentList.get(position).isSubmited());
        viewholder.setData(title,body);
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }


    class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.assignment_name);
            body = itemView.findViewById(R.id.assignment_instructions);

        }

        private void setData(String titleText, String bodyText) {
            title.setText(titleText);
            body.setText(bodyText);
        }

        @Override
        public void onClick(View view) {
            Assignment coolAssignment = assignmentList.get(this.getAdapterPosition());
            String classID = coolAssignment.getCourseKey();
            int assignmentNum  = coolAssignment.getAssignmentNum();

            if (coolAssignment.getStationary()) {
                Intent i = new Intent(view.getContext(), VideoSubmission.class);
                i.putExtra("classID", classID);
                i.putExtra("assignmentNum", assignmentNum);
                view.getContext().startActivity(i);
            }
            else {
                Intent i = new Intent(view.getContext(), GPSActivity.class);
                i.putExtra("classID", classID);
                i.putExtra("assignmentNum", assignmentNum);
                view.getContext().startActivity(i);
            }
            /*
            String classID = coolClass.getClassID();

            String className = coolClass.getClassName();

            Intent i = new Intent(view.getContext(), ViewAssignments.class);
            i.putExtra("classID", classID);
            i.putExtra("className", className);
            view.getContext().startActivity(i);
            */
        }
    }
}
