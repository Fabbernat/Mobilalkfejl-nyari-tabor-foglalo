package com.example.mobilalkfejl_nyari_tabor_foglalo;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

import kotlin.NotImplementedError;

public class GoogleSignIn {
    public static GoogleSignInClient getClient(MainActivity mainActivity, GoogleSignInOptions gso) {
        throw new NotImplementedError();
    }

    public static Task<GoogleSignInAccount> getSignedInAccountFromIntent(Intent data) {
        throw new NotImplementedError();
    }
}
