package com.teknos.dam.singletons

import com.teknos.dam.entities.Jugador

class App_Singleton private constructor() {
    private lateinit var jugador : Jugador
    companion object {
        @Volatile
        private var instance: App_Singleton? = null
        fun getInstance(): App_Singleton {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = App_Singleton()
                    }
                }
            }
            return instance!!
        }
    }

    //Mètode per ASSIGNAR (set)
    fun setJugador(jugador : Jugador){
        this.jugador = jugador
    }
    //Mètode per LLEGIR (get)
    fun getJugador() : Jugador{
        return this.jugador
    }

    fun anemAferCoses() = "anem a fer coses"
}