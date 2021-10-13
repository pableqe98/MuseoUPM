package com.example.museoupm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void generacion1(View v){
        DatabaseReference ref = database.getReference("preguntas/generacion1");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Pregunta> preguntas = new ArrayList<>();

                HashMap respuesta =(HashMap) dataSnapshot.getValue();

                Pregunta pregunta1 = new Pregunta(((HashMap)respuesta.get("pregunta1")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta1")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta1);

                Pregunta pregunta2 = new Pregunta(((HashMap)respuesta.get("pregunta2")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta2")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta2);

                Pregunta pregunta3 = new Pregunta(((HashMap)respuesta.get("pregunta3")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta3")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta3);

                Pregunta pregunta4 = new Pregunta(((HashMap)respuesta.get("pregunta4")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta4")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta4);

                Pregunta pregunta5 = new Pregunta(((HashMap)respuesta.get("pregunta5")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta5")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta5);

                Generacion generacion = new Generacion(preguntas);

                Intent intent = new Intent(MainActivity.this, PreguntaActivity.class);
                intent.putExtra("GENERACION", (Parcelable) generacion);
                startActivity(intent);
                //System.out.println("HOLAAA "+respuesta.get("pregunta1").getClass().getName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    public void generacion2(View v){
        DatabaseReference ref = database.getReference("preguntas/generacion2");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Pregunta> preguntas = new ArrayList<>();

                HashMap respuesta =(HashMap) dataSnapshot.getValue();

                Pregunta pregunta1 = new Pregunta(((HashMap)respuesta.get("pregunta1")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta1")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta1);

                Pregunta pregunta2 = new Pregunta(((HashMap)respuesta.get("pregunta2")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta2")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta2);

                Pregunta pregunta3 = new Pregunta(((HashMap)respuesta.get("pregunta3")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta3")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta3);

                Pregunta pregunta4 = new Pregunta(((HashMap)respuesta.get("pregunta4")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta4")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta4);

                Pregunta pregunta5 = new Pregunta(((HashMap)respuesta.get("pregunta5")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta5")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta5);

                Generacion generacion = new Generacion(preguntas);

                Intent intent = new Intent(MainActivity.this, PreguntaActivity.class);
                intent.putExtra("GENERACION", (Parcelable) generacion);
                startActivity(intent);
                //System.out.println("HOLAAA "+respuesta.get("pregunta1").getClass().getName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void generacion3(View v){
        DatabaseReference ref = database.getReference("preguntas/generacion3");

        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Pregunta> preguntas = new ArrayList<>();

                HashMap respuesta =(HashMap) dataSnapshot.getValue();

                Pregunta pregunta1 = new Pregunta(((HashMap)respuesta.get("pregunta1")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta1")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta1")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta1);

                Pregunta pregunta2 = new Pregunta(((HashMap)respuesta.get("pregunta2")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta2")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta2")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta2);

                Pregunta pregunta3 = new Pregunta(((HashMap)respuesta.get("pregunta3")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta3")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta3")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta3);

                Pregunta pregunta4 = new Pregunta(((HashMap)respuesta.get("pregunta4")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta4")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta4")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta4);

                Pregunta pregunta5 = new Pregunta(((HashMap)respuesta.get("pregunta5")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta5")).get("enunciado").toString(),
                        new ArrayList<String>(){{
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta1").toString();
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta2").toString();
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta3").toString();
                            ((HashMap) respuesta.get("pregunta5")).get("respuesta4").toString();
                        }}
                );
                preguntas.add(pregunta5);

                Generacion generacion = new Generacion(preguntas);

                Intent intent = new Intent(MainActivity.this, PreguntaActivity.class);
                intent.putExtra("GENERACION", (Parcelable) generacion);
                startActivity(intent);
                //System.out.println("HOLAAA "+respuesta.get("pregunta1").getClass().getName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


}