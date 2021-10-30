package com.example.museoupm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PreguntaActivity extends AppCompatActivity {

    ArrayList<Pregunta> preguntas = new ArrayList<>();
    TextView enunciado;
    RadioGroup radioGroup;
    RadioButton respuesta1;
    RadioButton respuesta2;
    RadioButton respuesta3;
    RadioButton respuesta4;
    String respuesta_correcta;
    int n_pregunta;
    int preguntas_acertadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        preguntas_acertadas=0;

        enunciado = (TextView) findViewById(R.id.txtPregunta);
        respuesta1 = (RadioButton) findViewById(R.id.respuesta1);
        respuesta2 = (RadioButton) findViewById(R.id.respuesta2);
        respuesta3 = (RadioButton) findViewById(R.id.respuesta3);
        respuesta4 = (RadioButton) findViewById(R.id.respuesta4);
        radioGroup = (RadioGroup) findViewById(R.id.rgRespuestas) ;


        Intent intent = getIntent();
        Generacion generacion = intent.getExtras().getParcelable("GENERACION");

        preguntas = generacion.getPreguntas();

        n_pregunta = 1;
        cargarPregunta(1);

        //for (Pregunta pregunta : preguntas){
        System.out.println(preguntas.size());
        //}


    }

    protected void cargarPregunta(int n_pregunta){
        n_pregunta = n_pregunta-1;

        Pregunta pregunta = preguntas.get(n_pregunta);
        ArrayList<String> respuestas = pregunta.getRespuestas();
        System.out.println(respuestas.toString());

        enunciado.setText(pregunta.getEnunciado());
        respuesta1.setText(respuestas.get(0));
        respuesta2.setText(respuestas.get(1));
        respuesta3.setText(respuestas.get(2));
        respuesta4.setText(respuestas.get(3));

        respuesta_correcta = pregunta.getCorrecta();

    }

    public void responderPregunta(View v){
        n_pregunta += 1;

        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton respuesta = (RadioButton) findViewById(selectedId);
        String msg ="";
        if(respuesta.getText() == respuesta_correcta){
            preguntas_acertadas++;
            msg = "ACERTASTE";
        }
        else{
            msg = "FALLASTE";
        }

        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        if (n_pregunta<=5) {
            cargarPregunta(n_pregunta);
        }
        else {
            Toast.makeText(this,"FIN DE GENERACION",Toast.LENGTH_LONG).show();

            //TODO: Gestionar medallas

            Intent intent = new Intent(PreguntaActivity.this, MainActivity.class);

            startActivity(intent);
        }

    }
}