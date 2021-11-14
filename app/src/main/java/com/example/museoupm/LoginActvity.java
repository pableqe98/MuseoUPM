package com.example.museoupm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActvity extends AppCompatActivity {

    private int GOOGLE_SIGN_IN = 100;

    Button googleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);

        googleButton = (Button) findViewById(R.id.btnLogin);


        session();
    }

    private void goMainActivity(String email, String tipo){
        Intent intent = new Intent(LoginActvity.this, MainActivity.class);
        intent.putExtra("email",email);
        intent.putExtra("tipo", tipo);
        startActivity(intent);
    }


    private void session(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email_saved = prefs.getString("emailGoogle",null);
        String password_saved = prefs.getString("password",null);
        String tipo_saved = prefs.getString("tipo",null);

        if (email_saved != null && password_saved != null && tipo_saved != null ){
            //TODO: Iniciar sesión
        }
    }

    public void googleLogin(View v){

        GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleClient = GoogleSignIn.getClient(this,googleConf);

        googleClient.signOut();

        startActivityForResult(googleClient.getSignInIntent(),GOOGLE_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult();

            try {
                if (account != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Task completed successfully
                                goMainActivity(account.getEmail(),"google");
                            } else {
                                // Task failed with an exception
                                Exception exception = task.getException();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}