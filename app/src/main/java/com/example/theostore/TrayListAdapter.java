package com.example.theostore;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class TrayListAdapter extends RecyclerView.Adapter<TrayListAdapter.TrayListViewHolder> {

    Context c;
    private List<Tray> trayList;
    private OnItemClickListener cardListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        cardListener = listener;
    }

    public static class TrayListViewHolder extends RecyclerView.ViewHolder {

        public ImageView trayImageView;
        public TextView trayTextView1;
        public TextView trayTextView2;

        public TrayListViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            trayImageView = itemView.findViewById(R.id.cardImageView);
            trayTextView1 = itemView.findViewById(R.id.cardTextView);
            trayTextView2 = itemView.findViewById(R.id.cardTextView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public TrayListAdapter(Context c, List<Tray> trays) {
        this.c = c;
        trayList = trays;
    }

    @NonNull
    @Override
    public TrayListAdapter.TrayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.tray_list_item, parent, false);
        TrayListViewHolder tlvh = new TrayListViewHolder(v, cardListener);
        return tlvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TrayListAdapter.TrayListViewHolder holder, int position) {
        Tray currentTray = trayList.get(position);

        String imgLoc = "http://10.0.2.2/" + currentTray.getTrayCode() + ".jpg";

        Transformation imgTransform = new RoundedCornersTransformation(40, 0);

        Picasso.get()
                .load(imgLoc)
                .placeholder(R.drawable.empty_tray)
                .error(R.drawable.empty_tray)
                .transform(imgTransform)
                .into(holder.trayImageView);

//        holder.trayImageView.setImageResource(R.drawable.empty_tray);
        holder.trayTextView1.setText("Tray " + currentTray.getId());
        holder.trayTextView2.setText(currentTray.getUserInfo());
    }

    @Override
    public int getItemCount() {
        return trayList.size();
    }

}