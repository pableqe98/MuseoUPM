package com.example.museoupm;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private DatabaseReference mDatabase;
    private static final String EMAIL = "email";
    TextView txtEmailHome;


    private String email;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
            email = email.split("@")[0];
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onActivityCreated (Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        txtEmailHome = (TextView) requireView().findViewById(R.id.txtEmailHome);

        String mensaje = getString(R.string.home_message,email);
        txtEmailHome.setText(mensaje);

        if (email.equals("anonimo")){

            TextView noMedals = (TextView) requireView().findViewById(R.id.txtMedallas);
            noMedals.setText(R.string.no_medals);
            noMedals.setTextSize(20);

            ImageView imageGen1 = (ImageView) requireView().findViewById(R.id.imagegeneracion1);
            ImageView imageGen2 = (ImageView) requireView().findViewById(R.id.imagegeneracion2);
            ImageView imageGen3 = (ImageView) requireView().findViewById(R.id.imagegeneracion3);
            ImageView imageGen4 = (ImageView) requireView().findViewById(R.id.imagegeneracion4);
            ImageView imageGen5 = (ImageView) requireView().findViewById(R.id.imagegeneracion5);
            ImageView imageGen6 = (ImageView) requireView().findViewById(R.id.imagegeneracion6);

            ColorMatrix matrixBW = new ColorMatrix();
            matrixBW.setSaturation(0);
            ColorMatrixColorFilter filterBW = new ColorMatrixColorFilter(matrixBW);

            imageGen1.setColorFilter(filterBW);
            imageGen2.setColorFilter(filterBW);
            imageGen3.setColorFilter(filterBW);
            imageGen4.setColorFilter(filterBW);
            imageGen5.setColorFilter(filterBW);
            imageGen6.setColorFilter(filterBW);

        }


    }
}