package com.example.appreceitas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NovaReceitaActivity extends AppCompatActivity {

    private EditText etNomeReceita, etIngredientes, etModoPreparo;
    private Button btnSalvarReceita, btnExcluirReceita;

    private String nomeOriginal; // Para saber qual receita estamos editando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_receita);

        // Ligações com os campos
        etNomeReceita = findViewById(R.id.etNomeReceita);
        etIngredientes = findViewById(R.id.etIngredientes);
        etModoPreparo = findViewById(R.id.etModoPreparo);
        btnSalvarReceita = findViewById(R.id.btnSalvarReceita);
        btnExcluirReceita = findViewById(R.id.btnExcluirReceita);

        // Verifica se veio uma receita para edição
        Intent intent = getIntent();
        nomeOriginal = intent.getStringExtra("nome");
        String ingredientesRecebidos = intent.getStringExtra("ingredientes");
        String modoPreparoRecebido = intent.getStringExtra("modoPreparo");

        if (nomeOriginal != null) {
            etNomeReceita.setText(nomeOriginal);
            etIngredientes.setText(ingredientesRecebidos);
            etModoPreparo.setText(modoPreparoRecebido);
            btnExcluirReceita.setVisibility(View.VISIBLE); // Exibe o botão excluir
        } else {
            btnExcluirReceita.setVisibility(View.GONE); // Oculta se for nova receita
        }

        // Botão Salvar
        btnSalvarReceita.setOnClickListener(v -> {
            String nome = etNomeReceita.getText().toString().trim();
            String ingredientes = etIngredientes.getText().toString().trim();
            String modoPreparo = etModoPreparo.getText().toString().trim();

            // Validação
            if (nome.isEmpty()) {
                etNomeReceita.setError("Informe o nome da receita");
                return;
            }
            if (ingredientes.isEmpty()) {
                etIngredientes.setError("Informe os ingredientes");
                return;
            }
            if (modoPreparo.isEmpty()) {
                etModoPreparo.setError("Informe o modo de preparo");
                return;
            }

            // Retorna os dados
            Intent resultado = new Intent();
            resultado.putExtra("nome", nome);
            resultado.putExtra("ingredientes", ingredientes);
            resultado.putExtra("modoPreparo", modoPreparo);
            resultado.putExtra("original", nomeOriginal); // Se for edição
            setResult(RESULT_OK, resultado);
            finish();
        });

        // Botão Excluir
        btnExcluirReceita.setOnClickListener(v -> {
            Intent resultado = new Intent();
            resultado.putExtra("deletar", true);
            resultado.putExtra("nome", nomeOriginal); // Para identificar qual deletar
            setResult(RESULT_OK, resultado);
            finish();
        });
    }
}
