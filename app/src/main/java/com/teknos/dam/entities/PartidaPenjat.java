package com.teknos.dam.entities;

import java.io.Serializable;

public class PartidaPenjat implements Serializable {
    //Atributs - Propietats de l'estat del joc
    private String paraulaSecreta;
    private String lletresUtilitzadesString;
    private int errorsActuals;
    private int maxErrors;

    //Constructor - s'execturà al crear la partida
    public PartidaPenjat(String paraulaSecreta, int maxErrors){
        this.paraulaSecreta = paraulaSecreta.toUpperCase();
        this.maxErrors = maxErrors;
        this.errorsActuals = 0;
        this.lletresUtilitzadesString = "";
    }

    //Mètodes (La lògica del Penjat)
    public int comprovarLletra(String lletra){
        String lletraMajus = lletra.toUpperCase();

        if(lletresUtilitzadesString.contains(lletraMajus)){
            return 0;
        }

        lletresUtilitzadesString += lletraMajus;

        if (paraulaSecreta.contains(lletraMajus)){
            return 1;
        } else {
            errorsActuals++;
            return -1;
        }
    }

    public boolean isDerrota(){
        return errorsActuals >= maxErrors;
    }

    public String getParaulaSecreta(){
        return paraulaSecreta;
    }

    public String getLletresUtilitzadesString(){
        return lletresUtilitzadesString;
    }

    public int getErrorsActuals(){
        return errorsActuals;
    }

}
