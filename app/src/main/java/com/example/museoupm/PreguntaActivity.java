package com.example.museoupm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PreguntaActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    ArrayList<Pregunta> preguntas = new ArrayList<>();
    TextView titulo_generacion;
    TextView enunciado;
    RadioGroup radioGroup;
    RadioButton respuesta1;
    RadioButton respuesta2;
    RadioButton respuesta3;
    RadioButton respuesta4;
    TextView contador;

    String respuesta_correcta;
    int n_pregunta;
    int preguntas_acertadas;
    String email_saved;
    String dificultad_cadena;
    int dificultad ;
    String generacion_nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        preguntas_acertadas=0;

        titulo_generacion = (TextView) findViewById(R.id.txtTituloGeneracion);
        enunciado = (TextView) findViewById(R.id.txtPregunta);
        respuesta1 = (RadioButton) findViewById(R.id.respuesta1);
        respuesta2 = (RadioButton) findViewById(R.id.respuesta2);
        respuesta3 = (RadioButton) findViewById(R.id.respuesta3);
        respuesta4 = (RadioButton) findViewById(R.id.respuesta4);
        radioGroup = (RadioGroup) findViewById(R.id.rgRespuestas);
        contador = (TextView) findViewById(R.id.txtAciertos);

        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        email_saved = prefs.getString("emailGoogle",null);

        Intent intent = getIntent();
        Generacion generacion = intent.getExtras().getParcelable("GENERACION");
        String generacion_titulo = intent.getExtras().getString("generacion_titulo");
        dificultad_cadena = intent.getExtras().getString("dificultad");
        switch (dificultad_cadena){
            case "facil":
                dificultad = 2;
                break;
            case "normal":
                dificultad = 3;
                break;
            case "dificil":
                dificultad = 4;
                break;
        }
        generacion_nombre = intent.getExtras().getString("generacion_nombre");

        titulo_generacion.setText(generacion_titulo);

        preguntas = generacion.getPreguntas();

        n_pregunta = 0;
        cargarPregunta(n_pregunta);


    }

    protected void cargarPregunta(int n_preg){
        n_pregunta = n_preg;

        Pregunta pregunta = preguntas.get(n_pregunta);
        ArrayList<String> respuestas = pregunta.getRespuestas();
        System.out.println(respuestas.toString());

        enunciado.setText(pregunta.getEnunciado());
        respuesta1.setText(respuestas.get(0));
        respuesta2.setText(respuestas.get(1));
        respuesta3.setText(respuestas.get(2));
        respuesta4.setText(respuestas.get(3));

        respuesta_correcta = pregunta.getCorrecta();

        String text = Integer.toString(preguntas_acertadas) + "/5 aciertos"; //getString(R.string.mostrar_aciertos, Integer.toString(preguntas_acertadas));
        contador.setText(text);
    }

    public void responderPregunta(View v){


        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton respuesta = (RadioButton) findViewById(selectedId);
        String msg ="";
        String rp = respuesta.getText().toString();
        if(respuesta.getText().toString().equals(respuesta_correcta)){
            preguntas_acertadas++;
            msg = "ACERTASTE";

        }
        else{
            msg = "FALLASTE";
        }

        n_pregunta += 1;

        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        if (n_pregunta<5) {
            cargarPregunta(n_pregunta);
        }
        else {
            String text = Integer.toString(preguntas_acertadas) + "/5 aciertos"; //getString(R.string.mostrar_aciertos, Integer.toString(preguntas_acertadas));
            contador.setText(text);

            Toast.makeText(this,"FIN DE GENERACION",Toast.LENGTH_LONG).show();

            SystemClock.sleep(4500);

            String email_no_dot = email_saved.replace(".","");
            if (preguntas_acertadas > dificultad) {
                database.getReference().child("Usuarios").child(email_no_dot)
                        .child("medallas").child(generacion_nombre).setValue(true);
            }
            else {
                database.getReference().child("Usuarios").child(email_no_dot)
                        .child("medallas").child(generacion_nombre).setValue(false);
            }

            //Añado las respuestas correctas a la BD
            Date dt = new Date();
            DateFormat formatter = new SimpleDateFormat("yyyy", Locale.GERMANY);
            String today = formatter.format(dt);

            DatabaseReference punctuation = database.getReference().child("Usuarios").child(email_no_dot)
                    .child("respuestas_correctas").child(today);

            punctuation.runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                    if (currentData.getValue(Integer.class) == null){
                        currentData.setValue(preguntas_acertadas * dificultad);
                    }
                    else {
                        currentData.setValue(currentData.getValue(Integer.class) + preguntas_acertadas * dificultad);
                    }
                    return Transaction.success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                    Log.d("Transactiion update score", "transaction:onComplete:" + error);
                }
            });

            Intent intent = new Intent(PreguntaActivity.this, MainActivity.class);
            intent.putExtra("email",email_saved);
            intent.putExtra("tipo","google");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }
}