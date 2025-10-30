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
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var nom : EditText
    private lateinit var btnGoToJoc1 : Button
    private lateinit var btnGoToJoc2 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.i("EntregaHalloween5.1","MainActivity")

        // Initializing Views
        nom.findViewById<EditText>(R.id.nomInput)

        //Initializing Buttons
        btnGoToJoc1.findViewById<Button>(R.id.btnJoc1)
        btnGoToJoc2.findViewById<Button>(R.id.btnJoc2)
    }

    fun goToJoc1(view: View){
        Log.i("EntregaHalloween5.1","MainActivity")
        var i = Intent(MainActivity@this, Joc1Activity::class.java)
        startActivity(i)
    }

    fun goToJoc2(view:View){
        Log.i("EntregaHalloween5.1","MainActivity")
        var i = Intent(MainActivity@this, Joc2Activity::class.java)
        startActivity(i)
    }


}