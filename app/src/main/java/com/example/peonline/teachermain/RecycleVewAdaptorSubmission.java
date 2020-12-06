package com.example.peonline.teachermain;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peonline.R;
import com.example.peonline.video.PlayVideoActivity;
import com.example.peonline.video.Video;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RecycleVewAdaptorSubmission extends RecyclerView.Adapter<RecycleVewAdaptorSubmission.Viewholder>  {

    private TextView title;
    private List<Submission> submissionList;
    private StorageReference videoRef;
    public RecycleVewAdaptorSubmission(List<Submission> submissionlist) {
        this.submissionList = submissionlist;
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
        String title= submissionList.get(position).getSubmitterName();
        viewholder.setData(title);
    }

    @Override
    public int getItemCount() {
        return submissionList.size();
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
        public void onClick(final View view) {
            Submission coolSubmission = submissionList.get(this.getAdapterPosition());
            Boolean isStationary = coolSubmission.getStationary();

            // Stationary(Video submission)
            if (isStationary) {
                Video coolVideo = coolSubmission.getVideoSubmission();

                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                videoRef = storageRef.child("videos/"+coolSubmission.getSubmitterID()+"/"+coolVideo.getVideoName()+".mp4");
                videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Intent playIntent = new Intent(view.getContext(), PlayVideoActivity.class);
                        playIntent.putExtra("videoUri", uri.toString());
                        view.getContext().startActivity(playIntent);
                    }
                });


            }
            // Non-stationary (GPS stats)
            else {
                Submission coolSubmission1 = submissionList.get(this.getAdapterPosition());
                Intent statsIntent = new Intent(view.getContext(), NonStationarySubmission.class);
                statsIntent.putExtra("studentName", coolSubmission1.getSubmitterName());
                statsIntent.putExtra("distance", coolSubmission1.getDistance());
                statsIntent.putExtra("speed", coolSubmission1.getSpeed());
                statsIntent.putExtra("time", coolSubmission1.getTime());
                view.getContext().startActivity(statsIntent);

            }
        }
    }
}
