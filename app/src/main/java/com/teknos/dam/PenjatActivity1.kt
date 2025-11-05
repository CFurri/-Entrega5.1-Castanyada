package com.teknos.dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.teknos.dam.entities.Jugador
import com.teknos.dam.singletons.App_Singleton

class PenjatActivity1 : AppCompatActivity() {
    private lateinit var nameInput : EditText
    private lateinit var paraulaInput : EditText
    private lateinit var pistaInput : EditText
    private lateinit var btnStart : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_penjat1)

        Log.i("EntregaHalloween5.1","PenjatActivity1")

        // Views initialization
        nameInput = findViewById(R.id.nameInput)
        paraulaInput = findViewById(R.id.paraulaInput)
        pistaInput = findViewById(R.id.pistaInput)

        // El botó per començar
        btnStart = findViewById(R.id.btnStart)
    }

    fun goToJoc(view: View){
        Log.i("AD_Penjat","PenjatActivity1:goToJoc")

        val singleton = App_Singleton.getInstance()

        val nom = nameInput.text.toString()
        val paraula= paraulaInput.text.toString()
        val pista = pistaInput.text.toString()

        val nouJugador = Jugador(nom, paraula, pista) //Crear l'objecte
        singleton.setJugador(nouJugador)

        var i = Intent(MainActivity@ this, PenjatActivity2::class.java)

        startActivity(i)
    }
}