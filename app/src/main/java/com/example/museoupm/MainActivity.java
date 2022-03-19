package com.example.museoupm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String GENERACION_1 = "generacion1";
    private static final String GENERACION_2 = "generacion2";
    private static final String GENERACION_3 = "generacion3";
    private static final String GENERACION_4 = "generacion4";
    private static final String GENERACION_5 = "generacion5";

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String email = "";
        String tipo = "google";

        if (getIntent().hasExtra("email")) {
            Bundle content = getIntent().getExtras();
            email = content.getString("email");
            tipo = content.getString("tipo");

            SharedPreferences.Editor prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
            prefs.putString("emailGoogle", email);
            prefs.putString("tipo", tipo);
            prefs.apply();
            //Inicializo información en Realtime Database si no existía este usuario
            initializeDBinfo(email);
        }

    }

    public void initializeDBinfo(String email){

        //elimino los '.' del email
        String email_no_dot = email.replace(".","");
        //Comprobar si existe
        DatabaseReference existsUser = database.getReference().child("Usuarios").child(email_no_dot);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Si no existe, añadir a RTDB
                if(!dataSnapshot.exists()) {
                    System.out.println("NUEVO USUARIO QUE AÑADIR");

                    existsUser.child("medallas").child("ejemplo").setValue("ejemplo");
                    existsUser.child("respuestas_correctas").child("ejemplo").setValue("ejemplo");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        existsUser.addListenerForSingleValueEvent(eventListener);

    }

    public void logOut(View v){
        SharedPreferences.Editor prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
        prefs.clear();
        prefs.apply();

        FirebaseAuth.getInstance().signOut();
        onBackPressed();
    }

    public void empezarEscanerQR(View v){
        Intent intent = new Intent(MainActivity.this, ScanQR.class);

        startActivity(intent);
    }
}