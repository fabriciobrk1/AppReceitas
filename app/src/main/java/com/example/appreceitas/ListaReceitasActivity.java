package com.example.appreceitas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

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
    private static final int REQUEST_NOVA_RECEITA = 1;
    private static final int REQUEST_EDITAR_RECEITA = 2;
    private int posicaoEditando = -1;

    private SharedPreferences prefs;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_receitas);

        prefs = getSharedPreferences("AppReceitas", MODE_PRIVATE);
        listaReceitas = carregarReceitas();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReceitaAdapter(listaReceitas, new ReceitaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Receita receita) {
                Intent intent = new Intent(ListaReceitasActivity.this, DetalhesReceitaActivity.class);
                intent.putExtra("nome", receita.getNome());
                intent.putExtra("ingredientes", receita.getIngredientes());
                intent.putExtra("modoPreparo", receita.getModoPreparo());
                startActivity(intent);
            }

            @Override
            public void onItemEdit(Receita receita, int position) {
                posicaoEditando = position;
                Intent intent = new Intent(ListaReceitasActivity.this, NovaReceitaActivity.class);
                intent.putExtra("nome", receita.getNome());
                intent.putExtra("ingredientes", receita.getIngredientes());
                intent.putExtra("modoPreparo", receita.getModoPreparo());
                startActivityForResult(intent, REQUEST_EDITAR_RECEITA);
            }
        });

        recyclerView.setAdapter(adapter);

        FloatingActionButton adicionar = findViewById(R.id.AdicionarReceita);
        adicionar.setOnClickListener(v -> {
            posicaoEditando = -1;
            Intent intent = new Intent(ListaReceitasActivity.this, NovaReceitaActivity.class);
            startActivityForResult(intent, REQUEST_NOVA_RECEITA);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            boolean deletar = data.getBooleanExtra("deletar", false);
            String nome = data.getStringExtra("nome");
            String ingredientes = data.getStringExtra("ingredientes");
            String modoPreparo = data.getStringExtra("modoPreparo");

            if (deletar && requestCode == REQUEST_EDITAR_RECEITA && posicaoEditando != -1) {
                listaReceitas.remove(posicaoEditando);
                Toast.makeText(this, "Receita excluída", Toast.LENGTH_SHORT).show();
            } else {
                Receita novaReceita = new Receita(nome, ingredientes, modoPreparo);

                if (requestCode == REQUEST_EDITAR_RECEITA && posicaoEditando != -1) {
                    listaReceitas.set(posicaoEditando, novaReceita);
                    Toast.makeText(this, "Receita editada", Toast.LENGTH_SHORT).show();
                } else {
                    listaReceitas.add(novaReceita);
                    Toast.makeText(this, "Receita adicionada", Toast.LENGTH_SHORT).show();
                }
            }

            salvarReceitas(listaReceitas);
            adapter.updateLista(listaReceitas);
        }
    }

    private List<Receita> carregarReceitas() {
        String json = prefs.getString("receitas", null);
        if (json != null) {
            Type type = new TypeToken<List<Receita>>() {}.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>();
    }

    private void salvarReceitas(List<Receita> lista) {
        String json = gson.toJson(lista);
        prefs.edit().putString("receitas", json).apply();
    }
}
