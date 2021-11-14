package com.example.museoupm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String email = "";
        String tipo = "google";
        String password = "";

        SharedPreferences.Editor prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
        prefs.putString("emailGoogle",email);
        prefs.putString("tipo", tipo);
        prefs.apply();
    }

    //public void generacion1(View v){
    //    generacion(GENERACION_1);
    //}

    //public void generacion2(View v){
    //    generacion(GENERACION_2);
    //}

    //public void generacion3(View v){
//        generacion(GENERACION_3);
  //  }


    public void empezarEscanerQR(View v){
        Intent intent = new Intent(MainActivity.this, ScanQR.class);

        startActivity(intent);
    }
}