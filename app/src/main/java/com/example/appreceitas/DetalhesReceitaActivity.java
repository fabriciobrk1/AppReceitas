package com.example.appreceitas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.TextView;

public class DetalhesReceitaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_receita);

        TextView tvNome = findViewById(R.id.tvNome);
        TextView tvIngredientes = findViewById(R.id.tvIngredientes);
        TextView tvModoPreparo = findViewById(R.id.tvModoPreparo);

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());

        String nome = getIntent().getStringExtra("nome");
        String ingredientes = getIntent().getStringExtra("ingredientes");
        String modoPreparo = getIntent().getStringExtra("modoPreparo");

        tvNome.setText(nome);
        tvIngredientes.setText(ingredientes);
        tvModoPreparo.setText(modoPreparo);
    }
}
