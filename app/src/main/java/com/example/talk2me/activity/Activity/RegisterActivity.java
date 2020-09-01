package com.example.talk2me.activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.talk2me.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilSenha, tilSenha2, tilSobrenome, tilNome, tilNascimento, tilEmail;
    private TextView txfNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilSenha = findViewById(R.id.tilSenha);
        tilSenha2 = findViewById(R.id.tilSenha2);
        tilSobrenome = findViewById(R.id.tilSobrenome);
        tilNome = findViewById(R.id.tilNome);
        tilNascimento = findViewById(R.id.tilNascimento);
        txfNascimento = findViewById(R.id.txfNascimento);
        tilEmail = findViewById(R.id.tilEmail);

        //Mask Formatter for data nascimento input
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        
        //First parameter is a TextView and the second one is the SimpleMaskFormatter(smf) applied
        MaskTextWatcher mtw = new MaskTextWatcher(txfNascimento, smf);
        txfNascimento.addTextChangedListener(mtw);
    }

    // This method deselect (clear the focus of) the fields when an action click occurs outside them
    public void deselectFields(View view) {

        tilSenha.clearFocus();
        tilSenha2.clearFocus();
        tilSobrenome.clearFocus();
        tilNome.clearFocus();
        tilNascimento.clearFocus();
        tilEmail.clearFocus();

    }

}