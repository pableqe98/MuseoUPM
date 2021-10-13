package com.example.museoupm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class PreguntaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        Intent intent = getIntent();
        Generacion generacion = intent.getExtras().getParcelable("GENERACION");

        ArrayList<Pregunta> preguntas = generacion.getPreguntas();

        //for (Pregunta pregunta : preguntas){
            System.out.println(preguntas.size());
        //}


    }
}