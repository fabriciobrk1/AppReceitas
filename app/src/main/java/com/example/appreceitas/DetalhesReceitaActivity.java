package com.example.appreceitas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class DetalhesReceitaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_receita);

        TextView tvNome = findViewById(R.id.tvNome);
        TextView tvIngredientes = findViewById(R.id.tvIngredientes);

        String nome = getIntent().getStringExtra("nome");
        String ingredientes = getIntent().getStringExtra("ingredientes");

        tvNome.setText(nome);
        tvIngredientes.setText(ingredientes);
    }
}
