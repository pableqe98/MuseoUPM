package com.example.museoupm;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Generacion implements Parcelable, Serializable {

    ArrayList<Pregunta> preguntas;

    public Generacion(ArrayList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    protected Generacion(Parcel in) {
        this.preguntas = in.createTypedArrayList(Pregunta.CREATOR); //in.readArrayList(Pregunta.getSystemClassLoader());
    }

    public static final Creator<Generacion> CREATOR = new Creator<Generacion>() {
        @Override
        public Generacion createFromParcel(Parcel in) {
            return new Generacion(in);
        }

        @Override
        public Generacion[] newArray(int size) {
            return new Generacion[size];
        }
    };

    public ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(preguntas);
    }
}
