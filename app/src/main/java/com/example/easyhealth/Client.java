package com.example.easyhealth;

import java.util.ArrayList;

public class Client {
    //atributes
    // posarem els que heredi de usuari
    int id;
    String suscription;
    float high;
    float weight;
    //ArrayList<Food>llistaMenjar;
    ArrayList<ReservedClasses> classesReservades;
    boolean notific;

    //metodes




    public Client(int id, String suscription, float high, float weight, ArrayList<ReservedClasses> classesReservades, boolean notific) {
        this.id = id;
        this.suscription = suscription;
        this.high = high;
        this.weight = weight;
        this.classesReservades = classesReservades;
        this.notific = notific;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isNotific() {
        return notific;
    }

    public void setNotific(boolean notific) {
        this.notific = notific;
    }

    public void add_food(){}

    public void add_class(){}

    public void sum_calories(){}
}
