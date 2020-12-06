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

public class RecycleVewAdaptorStudentStats extends RecyclerView.Adapter<RecycleVewAdaptorStudentStats.Viewholder>  {

    private TextView Distance;
    private TextView Speed;
    private TextView Time;
    private List<Stats> statsList;
    public RecycleVewAdaptorStudentStats(List<Stats> statslist) {
        this.statsList = statslist;
    }
    public View.OnClickListener mListner;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layoutstudentstats, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int position) {
        String distance = Float.toString(statsList.get(position).getDistance());
        String speed = Double.toString(statsList.get(position).getSpeed());
        String time = Double.toString(statsList.get(position).getTime());

        viewholder.setData(distance, speed, time);
    }

    @Override
    public int getItemCount() {
        return statsList.size();
    }


    class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Distance = itemView.findViewById(R.id.Distance);
            Speed = itemView.findViewById(R.id.Speed);
            Time = itemView.findViewById(R.id.Time);

        }

        private void setData(String distance, String speed, String time) {
            Distance.setText(distance);
            Speed.setText(speed);
            Time.setText(time);
        }

        @Override
        public void onClick(View view) {
            // Use if needed
        }
    }
}
