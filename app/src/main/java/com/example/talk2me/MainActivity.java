package com.example.talk2me;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.talk2me.Activity.CadastroActivity;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen(true);
        setButtonBackVisible(false);
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
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build()
        );
    }

    public void chamaTelaCadastro(View view){
        Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
        startActivity(intent);
    }
}
