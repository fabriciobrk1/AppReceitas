package com.example.appreceitas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends Activity {

    private EditText edtEmail, edtSenha;
    private Button btnSalvar;
    private SharedPreferences prefs;

    private static final String PREFS_NAME = "app_receitas_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SENHA = "senha";
    private static final String KEY_LOGADO = "logado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        edtEmail  = findViewById(R.id.edtCadastroEmail);
        edtSenha  = findViewById(R.id.edtCadastroSenha);
        btnSalvar = findViewById(R.id.btnSalvarCadastro);

        btnSalvar.setOnClickListener(view -> {
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            // Validações básicas
            if (email.isEmpty()) {
                edtEmail.setError("Informe seu e-mail");
                edtEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.setError("E-mail inválido");
                edtEmail.requestFocus();
                return;
            }
            if (senha.isEmpty()) {
                edtSenha.setError("Informe sua senha");
                edtSenha.requestFocus();
                return;
            }
            if (senha.length() < 4) {
                edtSenha.setError("Senha muito curta");
                edtSenha.requestFocus();
                return;
            }

            // Salva credenciais e marca como não logado (ainda não fez login)
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_SENHA, senha);
            editor.putBoolean(KEY_LOGADO, false);
            editor.apply();

            Toast.makeText(this, "Cadastro realizado! Faça login.", Toast.LENGTH_SHORT).show();

            // Retorna para a tela de login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
