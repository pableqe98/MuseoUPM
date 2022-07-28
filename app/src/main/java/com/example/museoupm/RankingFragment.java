package com.example.museoupm;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment {

    private DatabaseReference mDatabase;
    private static final String EMAIL = "email";

    private String email;
    private HashMap<String,Long> actualYear = new HashMap<>();
    private HashMap<String,Long> global = new HashMap<>();

    public RankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment fragment = new RankingFragment();
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
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }

    public void onActivityCreated (Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mDatabase.child("Usuarios").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting users data", task.getException());
                }
                else{
                    HashMap res = (HashMap) task.getResult().getValue();
                    if (res != null){
                        for (Object entry : res.entrySet()) {
                            AtomicReference<Long> totalPoints = new AtomicReference<>((long) 0);
                            Map.Entry<String,Object> user = (Map.Entry<String,Object>) entry;
                            String username = user.getKey();
                            HashMap<String,Object> userData = (HashMap<String,Object>)  user.getValue();
                            HashMap<String,Object> userPoints = (HashMap<String, Object>) userData.get("respuestas_correctas");
                            userPoints.forEach((key,value) -> {
                                if (isNumeric(key)){
                                    totalPoints.updateAndGet(v -> v + (Long) value);
                                    if (Year.now().getValue() == Integer.parseInt(key)){
                                        actualYear.put(username,(Long) value);
                                    }
                                }
                            });
                            global.put(username, totalPoints.get().longValue());
                        }
                    }
                }

            }
        });


    }

    public static boolean isNumeric(String string) {
        int intValue;


        if(string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
}