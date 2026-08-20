package com.example.primeiroapp;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CicloVida extends AppCompatActivity {
    private static final String TAG = "LogCicloVida";

    private int contador = 0;

    private TextView saida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ciclo_vida);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.i(TAG, "Entrou no onCreate");
        Log.e(TAG, "Entrou no onCreate");
        Log.d(TAG, "Entrou no onCreate");
        Log.v(TAG, "Entrou no onCreate");
        Log.w(TAG, "Entrou no onCreate");

        Button btmais = findViewById(R.id.btmais);
        saida = findViewById(R.id.saida);
        btmais.setOnClickListener(v -> {
            contador++;
            saida.setText(String.valueOf(contador));
        });

        }

        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            Log.e(TAG, "Entrou no onSaveInstanceState");
            outState.putInt("valor", contador);
        }

        @Override
        protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
            super.onRestoreInstanceState(savedInstanceState);


            if(savedInstanceState != null){
                contador = savedInstanceState.getInt("valor");
                saida.setText(String.valueOf(contador));


            }
        }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "Entrou no onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "Entrou no onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "Entrou no onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Entrou no onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "Entrou no onRestart");
    }
    }



