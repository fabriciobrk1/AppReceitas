package com.example.appreceitas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NovaReceitaActivity extends AppCompatActivity {

    private EditText etNomeReceita, etIngredientes, etModoPreparo;
    private Button btnSalvarReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_receita);

        etNomeReceita = findViewById(R.id.etNomeReceita);
        etIngredientes = findViewById(R.id.etIngredientes);
        etModoPreparo = findViewById(R.id.etModoPreparo);
        btnSalvarReceita = findViewById(R.id.btnSalvarReceita);

        btnSalvarReceita.setOnClickListener(v -> {
            String nome = etNomeReceita.getText().toString().trim();
            String ingredientes = etIngredientes.getText().toString().trim();
            String modoPreparo = etModoPreparo.getText().toString().trim();

            if (nome.isEmpty() || ingredientes.isEmpty() || modoPreparo.isEmpty()) {
                etNomeReceita.setError(nome.isEmpty() ? "Preencha o nome" : null);
                etIngredientes.setError(ingredientes.isEmpty() ? "Preencha os ingredientes" : null);
                etModoPreparo.setError(modoPreparo.isEmpty() ? "Preencha o modo de preparo" : null);
                return;
            }

            Receita novaReceita = new Receita(nome, ingredientes, modoPreparo);

            SharedPreferences prefs = getSharedPreferences("ReceitasPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            Gson gson = new Gson();
            String jsonAntigo = prefs.getString("lista_receitas", null);
            List<Receita> lista = new ArrayList<>();

            if (jsonAntigo != null) {
                Type type = new TypeToken<List<Receita>>() {}.getType();
                lista = gson.fromJson(jsonAntigo, type);
            }

            lista.add(novaReceita);

            String jsonNovo = gson.toJson(lista);
            editor.putString("lista_receitas", jsonNovo);
            editor.apply();

            finish(); // Encerra a tela
        });
    }
}
