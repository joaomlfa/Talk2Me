package com.example.talk2me.activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talk2me.R;
import com.example.talk2me.activity.User.User;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilSenha, tilSenha2, tilSobrenome, tilNome, tilNascimento, tilEmail;
    private String senha, senha2, nome, sobrenome, dtNascimento, email, sexo;
    private TextView txfNascimento;
    private RadioGroup rgpSexo;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("users");

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

        rgpSexo = findViewById(R.id.rgpSexo);

        //Mask Formatter for data nascimento input
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        
        //First parameter is a TextView and the second one is the SimpleMaskFormatter(smf) applied
        MaskTextWatcher mtw = new MaskTextWatcher(txfNascimento, smf);
        txfNascimento.addTextChangedListener(mtw);
    }

    // This method applied on the activity_register btnRegister onClick
    // sends the user to database if all the fields contains valid information
    public void createUser(View view){

        // Getting the text of editTexts
        nome = tilNome.getEditText().getText().toString();
        senha = tilSenha.getEditText().getText().toString();
        senha2 = tilSenha2.getEditText().getText().toString();
        sobrenome = tilSobrenome.getEditText().getText().toString();
        dtNascimento = tilNascimento.getEditText().getText().toString();
        email = tilEmail.getEditText().getText().toString();

        sexo = getSelectedRbt();//Getting the value of the selected RadioButton

        // If all the information in the fields are valid, user is sent to database
        if (isValidName() & isValidLastName() & isValidEmail() & isValidPassword() & isValidDate()) {

            User user = new User(nome, sobrenome,dtNascimento, sexo, email, senha);
            databaseReference.child(nome).setValue(user);

            //String x = nome + " " + sobrenome + " " + dtNascimento + " " + email + " " + senha + " " + senha2 + " " + sexo;
            //Log.i("ValoresVariaveis", x);
        } else {
            Toast.makeText(getApplicationContext(),"Preencha corretamente os dados.", Toast.LENGTH_SHORT).show();
        }
    }

    /* This method identifies the selected RadioButton from the ButtonGroup
     and returns in a String*/
    public String getSelectedRbt(){

        String description = "";
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
        if (nome.length() <= 3) {
            tilNome.setError("Nome precisa ter pelo menos 3 caracteres.");
            return false;
        } else {
            tilNome.setErrorEnabled(false);
            return true;
        }
    }

    // This method validates sobrenome
    public boolean isValidLastName(){
        if (sobrenome.length() <= 3) {
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

        // Regex Strings
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
            System.out.println(date);

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