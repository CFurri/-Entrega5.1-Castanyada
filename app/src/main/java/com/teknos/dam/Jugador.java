package com.teknos.dam;

public class Jugador {
    private String name;

    //Constructor
    public Jugador(String name) {
        this.name = name;
    }

    //Getters i Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //toString()
    @Override
    public String toString() {
        return "Jugador{" +
                "name='" + name + '\'' +
                '}';
    }
}
