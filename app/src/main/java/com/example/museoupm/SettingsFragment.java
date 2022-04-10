package com.example.museoupm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference mDatabase;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String EMAIL = "email";
    RadioGroup rgDificultad;

    // TODO: Rename and change types of parameters
    private String email;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(EMAIL, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(EMAIL);
            email = email.replace(".","");
        }


        mDatabase = FirebaseDatabase.getInstance().getReference();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btn = (Button) rootView.findViewById(R.id.btnGuardarDificultad);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int selectedId = rgDificultad.getCheckedRadioButtonId();
                RadioButton selected = (RadioButton) requireView().findViewById(selectedId);
                String val = "";
                switch (selected.getText().toString()){
                    case "Fácil":
                        val = "facil";
                        break;
                    case "Normal":
                        val = "normal";
                        break;
                    case "Difícil":
                        val = "dificil";
                        break;
                }
                mDatabase.child("Usuarios").child(email).child("dificultad").setValue(val);
            }
        });

        return rootView;
    }

    public void onActivityCreated (Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        rgDificultad = (RadioGroup) requireView().findViewById(R.id.rgDificultad);


        mDatabase.child("Usuarios").child(email).child("dificultad").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());

                }
                else {
                    int pos = 0;
                    switch (Objects.requireNonNull(task.getResult().getValue()).toString()){
                        case "facil":
                            pos = 0;
                            break;
                        case "normal":
                            pos = 1;
                            break;
                        case "dificil":
                            pos = 2;
                            break;
                    }
                    RadioButton selected = (RadioButton) rgDificultad.getChildAt(pos);
                    selected.setChecked(true);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}