package com.teknos.dam.entities;

import java.io.Serializable;

public class Jugador implements Serializable {
    private String name;
    private String paraula;
    private String pista;

    public Jugador(String name, String paraula, String pista){
        this.name = name;
        this.paraula = paraula;
        this.pista = pista;
    }

    //Getters i Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPista() {
        return pista;
    }

    public void setPista(String pista) {
        this.pista = pista;
    }

    public String getParaula() {
        return paraula;
    }

    public void setParaula(String paraula) {
        this.paraula = paraula;
    }


    //Mètode toString()
    @Override
    public String toString() {
        return "Jugador{" +
                "name='" + name + '\'' +
                ", paraula='" + paraula + '\'' +
                ", pista='" + pista + '\'' +
                '}';
    }
}
