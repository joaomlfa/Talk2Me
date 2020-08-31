package com.example.talk2me.activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.talk2me.R;
import com.example.talk2me.activity.Activity.MainScreen;
import com.example.talk2me.activity.Activity.RegisterActivity;
import com.example.talk2me.activity.Config.FirebaseConfig;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

public class MainActivity extends IntroActivity {
    private GoogleSignInClient gsc;
    private GoogleSignInButton googleSignInButton;
    private FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleSignInButton = findViewById(R.id.google_button);
        mAuth = FirebaseConfig.getFirebaseAuth();


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.WEB_CLIENT_ID))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        gsc = GoogleSignIn.getClient(this, gso);

        //Criação dos Sliders da tela Inicial
        setFullscreen(true); //Setando a tela como FullScreen
        setButtonBackVisible(false); //Removendo botão de back e next
        setButtonNextVisible(false);


        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorVermelhoPastel)
                .backgroundDark(R.color.colorVermelhoPastel)
                .fragment(R.layout.intro_1)
                .canGoBackward(false)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorAzulPastel)
                .backgroundDark(R.color.colorAzulPastel)
                .fragment(R.layout.intro_2)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorVermelhoPastel)
                .backgroundDark(R.color.colorVermelhoPastel)
                .fragment(R.layout.intro_3)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorAzulPastel)
                .backgroundDark(R.color.colorAzulPastel)
                .fragment(R.layout.intro_4)
                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorVermelhoPastel)
                .backgroundDark(R.color.colorVermelhoPastel)
                .fragment(R.layout.intro_register)
                .canGoForward(false)
                .build()
        );
    }

    public void googlebuttonLogin(View view){
        signIn();
    }

    private void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("signIn", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("signIn", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.


        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signInCredential", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Redirecionar para tela Principal
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signInCredential", "signInWithCredential:failure", task.getException());
                            //Retornar para tela de Login

                        }
                    }
                });
    }

    public void mainScreenRedirect(){
        startActivity(new Intent(this, MainScreen.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Log.i("signInUser","Usuário Não Logado");
            //Ir para tela de login
        }else{
            Log.i("signInUser","Usuário  Logado");
            //redirecionar para tela principal
            //mainScreenRedirect();
        }
    }

    public void buttonRegisterClick(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
