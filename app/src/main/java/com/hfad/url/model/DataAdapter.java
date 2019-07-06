package com.hfad.url.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.hfad.url.R;
import com.hfad.url.itity.Voting;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Voting> voitings = new ArrayList<>();

    public DataAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setVoitings(List<Voting> voitings) {
        this.voitings = voitings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_item, parent, false);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        String voting = voitings.get(position).getTitle();
        holder.nameView.setText(voting);

        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(inflater.getContext(),"click",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return voitings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;

        ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.name_vot);
        }
    }

}
