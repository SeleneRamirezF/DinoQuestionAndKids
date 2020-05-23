package com.example.dinoquestionandkids.informacion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dinoquestionandkids.R;
import java.util.ArrayList;

public class HistoriaAdapter extends RecyclerView.Adapter<HistoriaAdapter.MiVistaHolder> {

    private ArrayList<Historia> historias;

    public HistoriaAdapter(ArrayList<Historia> a) {
        historias = a;
    }

    public class MiVistaHolder extends RecyclerView.ViewHolder {

        private TextView nombre,descripcion;
        private ImageView logo;

        public MiVistaHolder(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.tvDescripcion);
            nombre = itemView.findViewById(R.id.tvNombre);
            logo = itemView.findViewById(R.id.tvLogo);
        }
    }

    @NonNull
    @Override
    public HistoriaAdapter.MiVistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.lista_historias, parent, false);
        return new MiVistaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriaAdapter.MiVistaHolder holder, int position) {
        holder.descripcion.setText(historias.get(position).getDescripcion());
        holder.nombre.setText(historias.get(position).getNombre());
        holder.logo.setImageResource(historias.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return historias.size();
    }


}
