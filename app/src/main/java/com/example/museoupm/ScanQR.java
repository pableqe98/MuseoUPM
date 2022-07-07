package com.example.museoupm;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.webkit.URLUtil;
import android.widget.RadioButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class ScanQR extends AppCompatActivity {

    private String token = "";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String dificultad = "";
    String email = "";

    SurfaceView cameraView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        if (getIntent().hasExtra("email")) {
            Bundle content = getIntent().getExtras();
            email = content.getString("email");
            email = email.replace(".","");
        }

        initQR();

    }

    public void initQR() {
        IntentIntegrator integrador = new IntentIntegrator(ScanQR.this);
        integrador.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrador.setPrompt("Lector QR");
        integrador.setCameraId(0);
        integrador.setBeepEnabled(true);
        integrador.setBarcodeImageEnabled(true);
        integrador.initiateScan();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this,"Lectura cancelada",Toast.LENGTH_LONG).show();
            }
            else {
                token = result.getContents();
                Log.e("Result",token);
                database.getReference().child("Usuarios").child(email).child("dificultad").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            dificultad = Objects.requireNonNull(task.getResult().getValue()).toString();
                        }
                    }
                });
                generacion(token);

            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void generacion(String nombreGeneracion){
        DatabaseReference ref = database.getReference("preguntas/" + nombreGeneracion );
        String n_generacion = nombreGeneracion.substring(nombreGeneracion.length() - 1);
        String titulo_generacion = "Generación " + n_generacion;
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Pregunta> preguntas = new ArrayList<>();

                HashMap respuesta =(HashMap) dataSnapshot.getValue();

                ArrayList<String> respuestas1 = new ArrayList<>(Arrays.asList(
                        ((HashMap) respuesta.get("pregunta1")).get("respuesta1").toString(),
                        ((HashMap) respuesta.get("pregunta1")).get("respuesta2").toString(),
                        ((HashMap) respuesta.get("pregunta1")).get("respuesta3").toString(),
                        ((HashMap) respuesta.get("pregunta1")).get("respuesta4").toString())
                );
                Pregunta pregunta1 = new Pregunta(((HashMap)respuesta.get("pregunta1")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta1")).get("enunciado").toString(),
                        respuestas1
                );

                preguntas.add(pregunta1);

                ArrayList<String> respuestas2 = new ArrayList<>(Arrays.asList(
                        ((HashMap) respuesta.get("pregunta2")).get("respuesta1").toString(),
                        ((HashMap) respuesta.get("pregunta2")).get("respuesta2").toString(),
                        ((HashMap) respuesta.get("pregunta2")).get("respuesta3").toString(),
                        ((HashMap) respuesta.get("pregunta2")).get("respuesta4").toString())
                );
                Pregunta pregunta2 = new Pregunta(((HashMap)respuesta.get("pregunta2")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta2")).get("enunciado").toString(),
                        respuestas2
                );
                preguntas.add(pregunta2);


                ArrayList<String> respuestas3 = new ArrayList<>(Arrays.asList(
                        ((HashMap) respuesta.get("pregunta3")).get("respuesta1").toString(),
                        ((HashMap) respuesta.get("pregunta3")).get("respuesta2").toString(),
                        ((HashMap) respuesta.get("pregunta3")).get("respuesta3").toString(),
                        ((HashMap) respuesta.get("pregunta3")).get("respuesta4").toString())
                );
                Pregunta pregunta3 = new Pregunta(((HashMap)respuesta.get("pregunta3")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta3")).get("enunciado").toString(),
                        respuestas3
                );
                preguntas.add(pregunta3);


                ArrayList<String> respuestas4 = new ArrayList<>(Arrays.asList(
                        ((HashMap) respuesta.get("pregunta4")).get("respuesta1").toString(),
                        ((HashMap) respuesta.get("pregunta4")).get("respuesta2").toString(),
                        ((HashMap) respuesta.get("pregunta4")).get("respuesta3").toString(),
                        ((HashMap) respuesta.get("pregunta4")).get("respuesta4").toString())
                );
                Pregunta pregunta4 = new Pregunta(((HashMap)respuesta.get("pregunta4")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta4")).get("enunciado").toString(),
                        respuestas4
                );
                preguntas.add(pregunta4);


                ArrayList<String> respuestas5 = new ArrayList<>(Arrays.asList(
                        ((HashMap) respuesta.get("pregunta5")).get("respuesta1").toString(),
                        ((HashMap) respuesta.get("pregunta5")).get("respuesta2").toString(),
                        ((HashMap) respuesta.get("pregunta5")).get("respuesta3").toString(),
                        ((HashMap) respuesta.get("pregunta5")).get("respuesta4").toString())
                );
                Pregunta pregunta5 = new Pregunta(((HashMap)respuesta.get("pregunta5")).get("correcta").toString(),
                        ((HashMap)respuesta.get("pregunta5")).get("enunciado").toString(),
                        respuestas5
                );
                preguntas.add(pregunta5);

                Generacion generacion = new Generacion(preguntas);


                Intent intent = new Intent(ScanQR.this, PreguntaActivity.class);
                intent.putExtra("GENERACION", (Parcelable) generacion);
                intent.putExtra("generacion_titulo", titulo_generacion);
                intent.putExtra("dificultad", dificultad);
                intent.putExtra("generacion_nombre", nombreGeneracion);
                startActivity(intent);

                //finish();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

}