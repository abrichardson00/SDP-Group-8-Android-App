package com.example.theostore;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TrayListAdapter extends RecyclerView.Adapter<TrayListAdapter.TrayListViewHolder> implements Filterable {

    Context c;
    private List<Tray> trayList;
    private List<Tray> completeTrayList;
    private OnItemClickListener cardListener;

    private Filter trayFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> searchWords = new ArrayList<String>(Arrays.asList(constraint.toString().toLowerCase().split("[\\s,]+")));
            handleSCharacters(searchWords);

            // parallel lists
            List<Integer> orderedNumOfMatches = new ArrayList<Integer>();
            List<Tray> orderedResults = new ArrayList<Tray>();

            for (Tray tray : completeTrayList) {
                String trayInfoText = tray.getUserInfo() + tray.getExtractedInfo() + tray.getId();

                // get the number of matches for this given tray
                Integer matches = 0;
                for (String word : searchWords) {
                    if (trayInfoText.contains(word)) matches += 1;
                }

                if (matches == 0) {
                    continue;
                }

                // basically doing an insert sort as we go along, I think?
                for (int i = 0; i <= orderedResults.size(); i++) {
                    if (i == orderedResults.size()) {
                        orderedNumOfMatches.add(matches);
                        orderedResults.add(tray);
                        break;
                    }
                    if (matches > orderedNumOfMatches.get(i)) {
                        orderedNumOfMatches.add(i, matches);
                        orderedResults.add(i, tray);
                        break;
                    }
                }
            }
            // now orderedResults is in order, with most matches at the start
            FilterResults searchResults = new FilterResults();
            searchResults.values = orderedResults;
            return searchResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            trayList.clear();
            trayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return trayFilter;
    }

    // if a word ends with an 's', also consider the word without ending with an 's'
    // we just add extra words to the given list.
    protected static void handleSCharacters(ArrayList<String> words) {
        int length = words.size();
        for (int i = 0; i < length; i++) {
            if (words.get(i).endsWith("s")) {
                String word = words.get(i);
                words.add(word.substring(0, word.length()-1));
            }
        }
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



    @NonNull
    @Override
    public TrayListAdapter.TrayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.tray_list_item, parent, false);
        return new TrayListViewHolder(v, cardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrayListAdapter.TrayListViewHolder holder, int position) {
        Tray currentTray = trayList.get(position);

//         rmatimgTransform = new RoundedCornersTransformation(40, 0);

//        System.out.println(currentTray.getURI());

//        Picasso.get().invalidate(currentTray.getURI());

//        System.out.println("tray" + position);

//        Picasso.get()
//                .load(currentTray.getURI())
////                .memoryPolicy(MemoryPolicy.NO_CACHE )
////                .networkPolicy(NetworkPolicy.NO_CACHE)
//                .placeholder(R.drawable.empty_tray)
//                .error(R.drawable.empty_tray)
//                .transform(imgTransform)
//                .into(holder.trayImageView);


//        Glide.with(c)
//                .load(currentTray.getURI())
////                .placeholder(R.drawable.empty_tray)
////                .error(Glide.with(holder.trayImageView).load(R.drawable.empty_tray))
////                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(40, 0)))
//                .into(holder.trayImageView);

        Glide.with(c).load(currentTray.getURI()).placeholder(R.drawable.empty_tray).into(holder.trayImageView);

        holder.trayTextView1.setText("Tray " + currentTray.getId());
        holder.trayTextView2.setText(currentTray.getUserInfo());
    }

    @Override
    public int getItemCount() {
        return trayList.size();
    }









    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        cardListener = listener;
    }



    public TrayListAdapter(Context c, List<Tray> trays) {
        this.c = c;
        trayList = trays;
        completeTrayList = new ArrayList<>(trayList);
    }



}



