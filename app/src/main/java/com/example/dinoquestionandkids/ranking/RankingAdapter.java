package com.example.dinoquestionandkids.ranking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinoquestionandkids.R;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MiVistaHolder> {

    private ArrayList<Ranking> puntuaciones;

    public RankingAdapter(ArrayList<Ranking> a) {
        puntuaciones = a;
    }

    public class MiVistaHolder extends RecyclerView.ViewHolder {

        private TextView nombre, puntuacion;

        public MiVistaHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombre);
            puntuacion = itemView.findViewById(R.id.tvPuntos);
        }
    }
    @NonNull
    @Override
    public RankingAdapter.MiVistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.lista_ranking, parent, false);
        return new MiVistaHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.MiVistaHolder holder, int position) {
        holder.nombre.setText(puntuaciones.get(position).getNombre());
        holder.puntuacion.setText(puntuaciones.get(position).getPuntuacion());
    }
    @Override
    public int getItemCount() {
        return puntuaciones.size();
    }
}
