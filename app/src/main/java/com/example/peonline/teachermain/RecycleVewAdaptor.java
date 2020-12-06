package com.example.peonline.teachermain;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peonline.R;

import java.util.List;

public class RecycleVewAdaptor extends RecyclerView.Adapter<RecycleVewAdaptor.Viewholder>  {

    private TextView title;
    private TextView body;
    private List<Class> classList;
    public RecycleVewAdaptor(List<Class> classlist) {
        this.classList = classlist;
    }
    public View.OnClickListener mListner;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {
        String title = classList.get(position).getClassName();
        String body = classList.get(position).getClassID();

        viewholder.setData(title,body);
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
            body = itemView.findViewById(R.id.distanceLabel);

        }

        private void setData(String titleText, String bodyText) {
            title.setText(titleText);
            body.setText(bodyText);


        }

        @Override
        public void onClick(View view) {
            Class coolClass = classList.get(this.getAdapterPosition());
            String classID = coolClass.getClassID();
            String className = coolClass.getClassName();

            Intent i = new Intent(view.getContext(), ViewClass.class);
            i.putExtra("classID", classID);
            i.putExtra("className", className);
            view.getContext().startActivity(i);
        }
    }
}
