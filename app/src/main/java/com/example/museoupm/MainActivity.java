package com.example.museoupm;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.museoupm.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    ActivityMainBinding binding;

    private static final String GENERACION_1 = "generacion1";
    private static final String GENERACION_2 = "generacion2";
    private static final String GENERACION_3 = "generacion3";
    private static final String GENERACION_4 = "generacion4";
    private static final String GENERACION_5 = "generacion5";

    String email;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        email = "";
        String tipo = "google";

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        if (getIntent().hasExtra("email")) {
            Bundle content = getIntent().getExtras();
            email = content.getString("email");
            tipo = content.getString("tipo");

            if (!email.equals("anonimo")) {
                SharedPreferences.Editor prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
                prefs.putString("emailGoogle", email);
                prefs.putString("tipo", tipo);
                prefs.apply();
                //Inicializo información en Realtime Database si no existía este usuario
                initializeDBinfo(email);
            }
        }
        /*else {
            SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
            email = prefs.getString("emailGoogle",null);
        }*/

        //Preparo el primer fragment a mostrar
        Bundle bundleInit = new Bundle();
        bundleInit.putString("email", email);

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundleInit);
        replaceLayout(homeFragment);

        setObtainedMedals();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Bundle bundle = new Bundle();
            bundle.putString("email", email);

            switch (item.getItemId()){
                case R.id.home:
                    HomeFragment homeFragmentSelect = new HomeFragment();
                    homeFragmentSelect.setArguments(bundle);
                    setObtainedMedals();
                    replaceLayout(homeFragmentSelect);
                    break;
                case R.id.qr:
                    replaceLayout(new QrFragment());
                    break;
                case R.id.settings:
                    if (email.equals("anonimo")){
                        Toast.makeText(this,"SOLO PARA REGISTRADOS",Toast.LENGTH_LONG).show();
                    }
                    else {
                        SettingsFragment settingsFragment = new SettingsFragment();
                        settingsFragment.setArguments(bundle);
                        replaceLayout(settingsFragment);
                    }
                    break;
            }
            return true;
        });

    }

    private void setObtainedMedals() {
        String email_no_dot = email.replace(".","");

        if (!email_no_dot.equals("anonimo")) {
            database.getReference().child("Usuarios").child(email_no_dot)
                    .child("medallas").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting medals data", task.getException());
                            } else {
                                HashMap res = (HashMap) task.getResult().getValue();
                                if (res != null) {
                                    String boolGen1 = res.get("generacion1").toString();
                                    String boolGen2 = res.get("generacion2").toString();
                                    String boolGen3 = res.get("generacion3").toString();
                                    String boolGen4 = res.get("generacion4").toString();
                                    String boolGen5 = res.get("generacion5").toString();
                                    String boolGen6 = res.get("generacion6").toString();

                                    ImageView imageGen1 = (ImageView) findViewById(R.id.imagegeneracion1);
                                    ImageView imageGen2 = (ImageView) findViewById(R.id.imagegeneracion2);
                                    ImageView imageGen3 = (ImageView) findViewById(R.id.imagegeneracion3);
                                    ImageView imageGen4 = (ImageView) findViewById(R.id.imagegeneracion4);
                                    ImageView imageGen5 = (ImageView) findViewById(R.id.imagegeneracion5);
                                    ImageView imageGen6 = (ImageView) findViewById(R.id.imagegeneracion6);

                                    ColorMatrix matrixBW = new ColorMatrix();
                                    matrixBW.setSaturation(0);
                                    ColorMatrixColorFilter filterBW = new ColorMatrixColorFilter(matrixBW);

                                    ColorMatrix matrixColor = new ColorMatrix();
                                    matrixColor.setSaturation(1);
                                    ColorMatrixColorFilter filterColor = new ColorMatrixColorFilter(matrixColor);

                                    if (boolGen1.equals("true")) {
                                        imageGen1.setColorFilter(filterColor);
                                    } else {
                                        imageGen1.setColorFilter(filterBW);
                                    }

                                    if (boolGen2.equals("true")) {
                                        imageGen2.setColorFilter(filterColor);
                                    } else {
                                        imageGen2.setColorFilter(filterBW);
                                    }

                                    if (boolGen3.equals("true")) {
                                        imageGen3.setColorFilter(filterColor);
                                    } else {
                                        imageGen3.setColorFilter(filterBW);
                                    }

                                    if (boolGen4.equals("true")) {
                                        imageGen4.setColorFilter(filterColor);
                                    } else {
                                        imageGen4.setColorFilter(filterBW);
                                    }

                                    if (boolGen5.equals("true")) {
                                        imageGen5.setColorFilter(filterColor);
                                    } else {
                                        imageGen5.setColorFilter(filterBW);
                                    }

                                    if (boolGen6.equals("true")) {
                                        imageGen6.setColorFilter(filterColor);
                                    } else {
                                        imageGen6.setColorFilter(filterBW);
                                    }
                                }

                            }

                        }
                    });
        }
    }

    public void replaceLayout(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
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

                    existsUser.child("medallas").child("generacion1").setValue(false);
                    existsUser.child("medallas").child("generacion2").setValue(false);
                    existsUser.child("medallas").child("generacion3").setValue(false);
                    existsUser.child("medallas").child("generacion4").setValue(false);
                    existsUser.child("medallas").child("generacion5").setValue(false);
                    existsUser.child("medallas").child("generacion6").setValue(false);
                    existsUser.child("respuestas_correctas").child("ejemplo").setValue("ejemplo");
                    existsUser.child("dificultad").setValue("normal");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR", databaseError.getMessage()); //Don't ignore errors!
            }
        };
        existsUser.addListenerForSingleValueEvent(eventListener);

    }

    public void openRanking(View v){
        Bundle bundle = new Bundle();
        bundle.putString("email", email);

        RankingFragment rankingFragment = new RankingFragment();
        rankingFragment.setArguments(bundle);
        replaceLayout(rankingFragment);
    }

    public void backToMain(View v){
        Bundle bundle = new Bundle();
        bundle.putString("email", email);

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        setObtainedMedals();
        replaceLayout(homeFragment);
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
        intent.putExtra("email",email);
        startActivity(intent);
    }
}