package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

public class musicAdaptere extends RecyclerView.Adapter<musicAdaptere.Viewholder> {


    @NonNull
    ArrayList<Song> Songs;
    Context context;
    public musicAdaptere(@NonNull ArrayList<Song> audioModels, Context context) {
        this.Songs = audioModels;
        this.context = context;
    }
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         View view= LayoutInflater.from(context).inflate(R.layout.reculerview,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") final int position) {

          final Song modal = Songs.get(position);
          holder.musicnm.setText(modal.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (context,Music.class);
                intent.putExtra("position",position);
                context.startActivity(intent);

            }
        });
        byte [] image= new byte[0];
        try {
            image = getaLLBUM(modal.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{


          if(image!=null){

                      Glide.with(context)
                      .asBitmap()
                      .load(image)
                      .into(holder.album_img);

          }else{
              Glide.with(context)
                      .load(R.drawable.u)
                  .into(holder.album_img);
          }
          }catch ( Exception e){

          }




    }
    private  byte[] getaLLBUM(String uri) throws IOException {
        MediaMetadataRetriever retriever= new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art=retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

    @Override
    public int getItemCount() {
        return Songs.size();
    }

     class Viewholder extends RecyclerView.ViewHolder {
        TextView musicnm;
        ImageView album_img;
          public Viewholder(@NonNull View itemView) {
             super(itemView);
             musicnm=itemView.findViewById(R.id.musicname);
             album_img =itemView.findViewById(R.id.musi_img);

         }

     }

}
