package com.example.appreceitas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListaReceitasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReceitaAdapter adapter;
    private List<Receita> listaReceitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_receitas);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaReceitas = new ArrayList<>();
        listaReceitas.add(new Receita("Bolo de Chocolate", "Farinha, Ovo, Chocolate..."));
        listaReceitas.add(new Receita("Panqueca", "Leite, Farinha, Ovo..."));

        adapter = new ReceitaAdapter(listaReceitas, receita -> {
            Intent intent = new Intent(ListaReceitasActivity.this, DetalhesReceitaActivity.class);
            intent.putExtra("nome", receita.getNome());
            intent.putExtra("ingredientes", receita.getIngredientes());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.AdicionarReceita);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(ListaReceitasActivity.this, NovaReceitaActivity.class);
            startActivity(intent);
        });
    }
}
