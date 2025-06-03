package com.example.appreceitas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListaReceitasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReceitaAdapter adapter;
    private List<Receita> listaReceitas;

    private static final String PREFS_NAME = "ReceitasPrefs";
    private static final String RECEITAS_KEY = "lista_receitas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_receitas);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Carrega receitas salvas com SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(RECEITAS_KEY, null);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Receita>>() {}.getType();
        listaReceitas = json == null ? new ArrayList<>() : gson.fromJson(json, type);

        adapter = new ReceitaAdapter(listaReceitas, receita -> {
            Intent intent = new Intent(ListaReceitasActivity.this, DetalhesReceitaActivity.class);
            intent.putExtra("nome", receita.getNome());
            intent.putExtra("ingredientes", receita.getIngredientes());
            intent.putExtra("modoPreparo", receita.getModoPreparo()); // agora incluindo modo de preparo
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        // Botão para adicionar nova receita
        FloatingActionButton adicionar = findViewById(R.id.AdicionarReceita);
        adicionar.setOnClickListener(v -> {
            Intent intent = new Intent(ListaReceitasActivity.this, NovaReceitaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarrega receitas ao voltar para esta tela
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(RECEITAS_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Receita>>() {}.getType();
        listaReceitas = json == null ? new ArrayList<>() : gson.fromJson(json, type);

        adapter.updateLista(listaReceitas);
    }
}
