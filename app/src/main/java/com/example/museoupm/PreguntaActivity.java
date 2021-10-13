package com.example.museoupm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class PreguntaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        TextView enunciado = (TextView) findViewById(R.id.txtPregunta);
        RadioButton respuesta1 = (RadioButton) findViewById(R.id.respuesta1);
        RadioButton respuesta2 = (RadioButton) findViewById(R.id.respuesta2);
        RadioButton respuesta3 = (RadioButton) findViewById(R.id.respuesta3);
        RadioButton respuesta4 = (RadioButton) findViewById(R.id.respuesta4);


        Intent intent = getIntent();
        Generacion generacion = intent.getExtras().getParcelable("GENERACION");

        ArrayList<Pregunta> preguntas = generacion.getPreguntas();

        Pregunta pregunta1 = preguntas.get(0);
        ArrayList<String> respuestas = pregunta1.getRespuestas();
        System.out.println(respuestas.toString());

        enunciado.setText(pregunta1.getEnunciado());
        respuesta1.setText(respuestas.get(0));
        respuesta2.setText(respuestas.get(1));
        respuesta3.setText(respuestas.get(2));
        respuesta4.setText(respuestas.get(3));

        //for (Pregunta pregunta : preguntas){
        System.out.println(preguntas.size());
        //}


    }
}