package com.example.dinoquestionandkids.dinosaurios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinoquestionandkids.R;

import java.util.ArrayList;

public class DinoAdapter extends RecyclerView.Adapter<DinoAdapter.MiVistaHolder>  {

    private ArrayList<Dino> dinos;
    final ListClickItem myListener;

    public class MiVistaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView logo;

        public MiVistaHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.tvDino);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int p = getAdapterPosition();
            myListener.onListClickItem(p);
        }
    }

    public DinoAdapter(ArrayList<Dino> e, ListClickItem lis){
        myListener = lis;
        dinos = e;
    }

    @NonNull
    @Override
    public DinoAdapter.MiVistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.lista_dinos, parent, false);
        return new MiVistaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DinoAdapter.MiVistaHolder holder, int position) {
        holder.logo.setImageResource(dinos.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return dinos.size();
    }

    public interface ListClickItem{
        void onListClickItem(int posicion);
    }
}
