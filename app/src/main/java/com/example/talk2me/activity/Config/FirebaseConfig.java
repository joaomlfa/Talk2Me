package com.example.talk2me.activity.Config;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseConfig {
    private static FirebaseAuth firebaseAuth;

    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;

    }
}
