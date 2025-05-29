package com.example.appreceitas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "app_receitas_prefs";
    private static final String KEY_LOGADO = "logado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean jaLogado = prefs.getBoolean(KEY_LOGADO, false);

        new Handler().postDelayed(() -> {
            Intent intent;
            /*if (jaLogado) {
                // Se já está logado, vai direto para lista
                intent = new Intent(SplashActivity.this, ListaReceitasActivity.class);
            } else */ {
                // Se não, vai para login
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 2000);
    }
}
