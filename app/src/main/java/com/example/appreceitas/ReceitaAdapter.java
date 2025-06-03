package com.example.appreceitas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReceitaAdapter extends RecyclerView.Adapter<ReceitaAdapter.ViewHolder> {
    private List<Receita> listaReceitas;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Receita receita);
        void onItemEdit(Receita receita, int position); // 👈 Adicionado para editar
    }

    public ReceitaAdapter(List<Receita> listaReceitas, OnItemClickListener listener) {
        this.listaReceitas = listaReceitas;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_receita, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receita receita = listaReceitas.get(position);
        holder.tvNomeReceita.setText(receita.getNome());
        holder.tvIngredientesReceita.setText(receita.getIngredientes());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(receita));

        // 👇 Clique longo para editar a receita
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemEdit(receita, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listaReceitas.size();
    }

    public void updateLista(List<Receita> novaLista) {
        this.listaReceitas = novaLista;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeReceita, tvIngredientesReceita;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNomeReceita = itemView.findViewById(R.id.tvNomeReceita);
            tvIngredientesReceita = itemView.findViewById(R.id.tvIngredientesReceita);
        }
    }
}
