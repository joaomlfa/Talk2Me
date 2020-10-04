package com.example.talk2me.activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talk2me.R;
import com.example.talk2me.activity.Config.FirebaseConfig;
import com.example.talk2me.activity.Model.User;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    //region Variables
    private TextInputLayout tilSenha, tilSenha2, tilSobrenome, tilNome, tilNascimento, tilEmail;
    private String senha, senha2, nome, sobrenome, dtNascimento, email, sexo;
    private TextView txfNascimento, txfNome, txfSobrenome, txfSenha, txfSenha2, txfEmail;
    private RadioGroup rgpSexo;
    private User user;

    FirebaseAuth firebaseAuth;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // region Finding Views
        tilSenha = findViewById(R.id.tilSenha);
        tilSenha2 = findViewById(R.id.tilSenha2);
        tilSobrenome = findViewById(R.id.tilSobrenome);
        tilNome = findViewById(R.id.tilNome);
        tilNascimento = findViewById(R.id.tilNascimento);
        tilEmail = findViewById(R.id.tilEmail);

        txfNascimento = findViewById(R.id.txfNascimento);
        txfNome = findViewById(R.id.txfNome);
        txfSenha = findViewById(R.id.txfSenha);
        txfSobrenome = findViewById(R.id.txfSobrenome);
        txfEmail = findViewById(R.id.txfEmail);
        txfSenha = findViewById(R.id.txfSenha);
        txfSenha2 = findViewById(R.id.txfSenha2);

        rgpSexo = findViewById(R.id.rgpSexo);
        //endregion

        //region Mask Formatter for data nascimento input
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        
        //First parameter is a TextView and the second one is the SimpleMaskFormatter(smf) applied
        MaskTextWatcher mtw = new MaskTextWatcher(txfNascimento, smf);
        txfNascimento.addTextChangedListener(mtw);
        //endregion

        // region TextWatchers for activity_register fields
        txfNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nome = txfNome.getText().toString();
                isValidName();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txfSobrenome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sobrenome = txfSobrenome.getText().toString();
                isValidLastName();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txfEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = txfEmail.getText().toString();
                isValidEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txfNascimento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dtNascimento = txfNascimento.getText().toString();
                isValidDate();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txfSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                senha = txfSenha.getText().toString();
                senha2 = txfSenha2.getText().toString();
                isValidPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txfSenha2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                senha = txfSenha.getText().toString();
                senha2 = txfSenha2.getText().toString();
                isValidPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //endregion
    }

    // This method applied on the activity_register btnRegister onClick
    // sends the user to database if all the fields contains valid information
    public void createUser(View view){

        // region Getting value of view elements
        nome = txfNome.getText().toString();
        senha = txfSenha.getText().toString();
        senha2 = txfSenha2.getText().toString();
        sobrenome = txfSobrenome.getText().toString();
        dtNascimento = txfNascimento.getText().toString();
        email = txfEmail.getText().toString();
        sexo = getSelectedRbt();//Getting the value of the selected RadioButton
        //endregion

        // If all the information in the fields are valid, user is sent to database
        if (isValidName() & isValidLastName() & isValidEmail() & isValidPassword() & isValidDate()) {

            // region Create and set user
            user = new User();
            user.setNome(nome);
            user.setSobrenome(sobrenome);
            user.setDtNascimento(dtNascimento);
            user.setSexo(sexo);
            user.setEmail(email);
            user.setSenha(senha);
            //endregion

            //region Sending User to database
            firebaseAuth = FirebaseConfig.getFirebaseAuth();
            firebaseAuth.createUserWithEmailAndPassword(
                    user.getEmail(),
                    user.getSenha())
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Usuário cadastrado com sucesso",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser firebaseUser = task.getResult().getUser();
                                user.setId(firebaseUser.getUid());
                                user.saveUserInDatabase();
                            } else {
                                Toast.makeText(getApplicationContext(),"Erro ao cadastrar usuario",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            //endregion

        } else {
            Toast.makeText(getApplicationContext(),"Preencha corretamente os dados", Toast.LENGTH_SHORT).show();
        }
    }

    // This method identifies the selected RadioButton from the ButtonGroup and returns in a String
    public String getSelectedRbt(){

        String description;
        int selected = rgpSexo.getCheckedRadioButtonId();

        if (selected == R.id.rbtFeminino){
            description = "feminino";
        } else if (selected == R.id.rbtMasculino){
            description = "masculino";
        } else {
            description = "outro";
        }

        return description;
    }

    //This method validates nome
    public boolean isValidName(){
        if (nome.length() < 3) {
            tilNome.setError("Nome precisa ter pelo menos 3 caracteres.");
            return false;
        } else {
            tilNome.setErrorEnabled(false);
            return true;
        }
    }

    // This method validates sobrenome
    public boolean isValidLastName(){
        if (sobrenome.length() < 3) {
            tilSobrenome.setError("Nome precisa ter pelo menos 3 caracteres.");
            return false;
        } else {
            tilSobrenome.setErrorEnabled(false);
            return true;
        }
    }

    // This method validates email
    public boolean isValidEmail(){
        // Regex String
        String emailPattern = "[a-zA-Z0-9._-]{3,}+@[a-z]+\\.+[a-z]{2,3}";

        if (!email.matches(emailPattern)) {
            tilEmail.setError("Formato de e-mail inválido.");
            return false;
        } else {
            tilEmail.setErrorEnabled(false);
            return true;
        }
    }

    // This method validates senha and if senha2 (confirmation) matches
    public boolean isValidPassword(){
        // Regex String
        String senhaPattern = "(?=^.{8,}$)(?=.*\\d)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";

        if(!senha.matches(senhaPattern) || senha.contains(" ")){
            tilSenha.setError("Senha precisa conter pelo menos 8 caracteres, 1 numeral, letras maiúsculas e minúsculas");
            return false;
        } else {
            tilSenha.setErrorEnabled(false);
            if (!senha2.equals(senha)){
                tilSenha2.setError("Senhas não correspondem.");
                return false;
            } else {
                tilSenha2.setErrorEnabled(false);
                return true;
            }
        }
    }

    // This method validates data
    /*https://mkyong.com/java/how-to-check-if-date-is-valid-in-java/*/
    public boolean isValidDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dtNascimento);
        } catch (ParseException e) {
            e.printStackTrace();
            tilNascimento.setError("Data inválida.");
            return false;
        }
        tilNascimento.setErrorEnabled(false);
        return true;
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