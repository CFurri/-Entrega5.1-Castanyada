package com.teknos.dam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.teknos.dam.entities.Jugador

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Log.i("EntregaHalloween5.1","MainActivity:onCreate")
    }

    fun goToJoc1(view: View){
        Log.i("EntregaHalloween5.1","MainActivity:goToJoc1")
        var i = Intent(MainActivity@this, Joc1Activity::class.java)
        startActivity(i)
    }

    fun goToJoc2(view:View){
        Log.i("EntregaHalloween5.1","MainActivity:goToJoc2")
        var i = Intent(MainActivity@this, PenjatActivity1::class.java)
        startActivity(i)
    }
}