package com.example.talk2me.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.talk2me.R;
import com.google.android.material.textfield.TextInputLayout;

public class CadastroActivity extends AppCompatActivity {

    private TextInputLayout tilSenha, tilSenha2, tilSobrenome, tilNome, tilNascimento, tilEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        tilSenha = findViewById(R.id.tilSenha);
        tilSenha2 = findViewById(R.id.tilSenha2);
        tilSobrenome = findViewById(R.id.tilSobrenome);
        tilNome = findViewById(R.id.tilNome);
        tilNascimento = findViewById(R.id.tilNascimento);
        tilEmail = findViewById(R.id.tilEmail);
    }

    public void tiraSelecao(View view){
        tilSenha.clearFocus();
        tilSenha2.clearFocus();
        tilSobrenome.clearFocus();
        tilNome.clearFocus();
        tilNascimento.clearFocus();
        tilEmail.clearFocus();
//        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
//                .hideSoftInputFromWindow(til.getWindowToken(), 0);
    }
}