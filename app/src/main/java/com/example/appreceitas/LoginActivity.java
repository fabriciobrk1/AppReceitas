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

public class LoginActivity extends Activity {

    private EditText edtEmail, edtSenha;
    private Button btnEntrar, btnCadastrar;
    private SharedPreferences prefs;

    private static final String PREFS_NAME = "app_receitas_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SENHA = "senha";
    private static final String KEY_LOGADO = "logado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        edtEmail     = findViewById(R.id.edtEmail);
        edtSenha     = findViewById(R.id.edtSenha);
        btnEntrar    = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnEntrar.setOnClickListener(view -> {
            String emailDigitado = edtEmail.getText().toString().trim();
            String senhaDigitada = edtSenha.getText().toString().trim();

            // Validações básicas
            if (emailDigitado.isEmpty()) {
                edtEmail.setError("Informe seu e-mail");
                edtEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(emailDigitado).matches()) {
                edtEmail.setError("E-mail inválido");
                edtEmail.requestFocus();
                return;
            }
            if (senhaDigitada.isEmpty()) {
                edtSenha.setError("Informe sua senha");
                edtSenha.requestFocus();
                return;
            }

            // Recupera credenciais salvas
            String emailSalvo = prefs.getString(KEY_EMAIL, "");
            String senhaSalva = prefs.getString(KEY_SENHA, "");

            if (emailDigitado.equals(emailSalvo) && senhaDigitada.equals(senhaSalva)) {
                // Marca usuário como logado
                prefs.edit().putBoolean(KEY_LOGADO, true).apply();

                Toast.makeText(this, "Login efetuado!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ListaReceitasActivity.class));
                finish();
            } else {
                Toast.makeText(this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
            }
        });

        btnCadastrar.setOnClickListener(view -> {
            // Abre a tela de cadastro
            startActivity(new Intent(this, CadastroActivity.class));
        });
    }
}
