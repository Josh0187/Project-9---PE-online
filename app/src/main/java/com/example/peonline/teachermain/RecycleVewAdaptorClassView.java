package com.example.peonline.teachermain;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peonline.R;
import com.example.peonline.database.Student;

import java.util.List;

public class RecycleVewAdaptorClassView extends RecyclerView.Adapter<RecycleVewAdaptorClassView.Viewholder>  {

    private TextView title;
    private List<Student> classList;
    public RecycleVewAdaptorClassView(List<Student> classlist) {
        this.classList = classlist;
    }
    public View.OnClickListener mListner;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layoutclassview, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {
        String title = classList.get(position).getName();

        viewholder.setData(title);
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }


    class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.studentname);
        }

        private void setData(String titleText) {
            title.setText(titleText);
        }

        @Override
        public void onClick(View view) {
            Student coolStudent = classList.get(this.getAdapterPosition());
            String studentID = coolStudent.getClassID();
            String studentName = coolStudent.getName();

            Intent i = new Intent(view.getContext(), ViewStudentStats.class);
            i.putExtra("studentID", studentID);
            i.putExtra("studentName", studentName);
            view.getContext().startActivity(i);
        }
    }
}
