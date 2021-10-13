package com.example.museoupm;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Pregunta extends ClassLoader implements Parcelable, Serializable {

    public String correcta;
    public String enunciado;
    public ArrayList<String> respuestas;

    public Pregunta(){}
    public Pregunta(String correcta, String enunciado, ArrayList<String> respuestas) {
        this.correcta = correcta;
        this.enunciado = enunciado;
        this.respuestas = respuestas;
    }

    protected Pregunta(Parcel in) {
        correcta = in.readString();
        enunciado = in.readString();
        respuestas = in.createStringArrayList();
    }

    public static final Creator<Pregunta> CREATOR = new Creator<Pregunta>() {
        @Override
        public Pregunta createFromParcel(Parcel in) {
            return new Pregunta(in);
        }

        @Override
        public Pregunta[] newArray(int size) {
            return new Pregunta[size];
        }
    };

    public String getCorrecta() {
        return correcta;
    }

    public void setCorrecta(String correcta) {
        this.correcta = correcta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public ArrayList<String> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<String> respuestas) {
        this.respuestas = respuestas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(correcta);
        dest.writeString(enunciado);
        dest.writeStringList(respuestas);
    }
}
