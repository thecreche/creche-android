package com.crecheinfo.recyclerviewproject;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.EventLogTags;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CrecheListAdapter extends RecyclerView.Adapter<CrecheListAdapter.CrecheViewHolder> {


    private List<Creche> crecheList;
    private Context context;


    public CrecheListAdapter(Context context, List<Creche> crecheList) {
        this.context = context;
        this.crecheList = crecheList;
    }

    @NonNull
    @Override
    public CrecheViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new CrecheViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CrecheViewHolder crecheViewHolder, final int position) {

        final Creche creche = crecheList.get(position);

        Picasso.get().setLoggingEnabled(true);

        Picasso.get().load(creche.getImageUrl())
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.ic_launcher_background)
                .into(crecheViewHolder.image);

        crecheViewHolder.name.setText(creche.getName());
        crecheViewHolder.address.setText(creche.getAddress());

        crecheViewHolder.element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);

                intent.putExtra(Constants.CRECHE_ID,creche.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return crecheList != null ? crecheList.size() : 0;
    }

    public class CrecheViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView address;
        CardView element;

        public CrecheViewHolder(View view) {
            super(view);

            image = view.findViewById(R.id.image);
            name = view.findViewById(R.id.name);
            address = view.findViewById(R.id.address);
            element = view.findViewById(R.id.element);

        }

    }
}
