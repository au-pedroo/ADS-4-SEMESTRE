package com.example.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterNota extends RecyclerView.Adapter<AdapterNota.ViewHolder> {
    private ArrayList<Nota>listaNotas;

    public AdapterNota(ArrayList<Nota> listaNotas) {
        this.listaNotas = listaNotas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tela = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nota, parent, false);
        return new ViewHolder(tela);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Nota item = listaNotas.get(position);
            holder.txtTitulo.setText(item.getTitulo());
            holder.txtDescricao.setText(item.getDescricao());
    }

    @Override
    public int getItemCount() {
        return listaNotas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo;
        TextView txtDescricao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtDescricao = itemView.findViewById(R.id.txtDescricao);
        }

        }
    }


