package com.teknos.dam

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import com.teknos.dam.entities.Jugador
import com.teknos.dam.entities.PartidaPenjat
import com.teknos.dam.singletons.App_Singleton

class PenjatActivity2 : AppCompatActivity() {
    private lateinit var partida : PartidaPenjat
    lateinit var tv_jugador: TextView
    lateinit var tv_pista : TextView
    lateinit var btnComprovar : Button
    lateinit var tvLletresUtilitzades : TextView
    lateinit var provarLletraInput : EditText
    lateinit var llistaLletresComprovades : TextView
    private lateinit var pecesPenjat: List<ImageView>
    private lateinit var line_vertical1 : ImageView
    private lateinit var line_horitzontal : ImageView
    private lateinit var line_vertical2 : ImageView
    private lateinit var cap : ImageView
    private lateinit var cos : ImageView
    private lateinit var braços : ImageView
    private lateinit var cama1 : ImageView
    private lateinit var cama2 : ImageView
    private val lletresEnPantalla = mutableListOf<TextView>()
    lateinit var tvMissatgesEstat : TextView
    lateinit var btnSortir : Button
    lateinit var btnReset : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_penjat2)

        Log.i("EntregaHalloween5.1", "PenjatActivity2:onCreate")

        val singleton = App_Singleton.getInstance()
        val jugador = singleton.getJugador()
        val paraulaDelJoc = jugador.paraula.uppercase()

        //Imatges i gestió d'aquestes
        line_vertical1 = findViewById(R.id.vertical1)
        line_horitzontal = findViewById(R.id.horitzontal)
        line_vertical2 = findViewById(R.id.vertical2)
        cap = findViewById(R.id.cap)
        cos = findViewById(R.id.cos)
        braços = findViewById(R.id.braços)
        cama1 = findViewById(R.id.cama1)
        cama2 = findViewById(R.id.cama2)

        pecesPenjat = listOf(
            line_vertical1,
            line_horitzontal,
            line_vertical2,
            cap,
            cos,
            braços,
            cama1,
            cama2
        )

        val maxErrorsJoc = pecesPenjat.size

        //Amaguem les imatges
        for (peça in pecesPenjat) {
            peça.visibility = View.INVISIBLE
        }

        partida = PartidaPenjat(paraulaDelJoc, maxErrorsJoc)

        //Part per passar paràmetres de l'objecte
        tv_jugador = findViewById(R.id.tvJugador)
        tv_pista = findViewById(R.id.tvPista)
        tv_jugador.text = jugador.name
        tv_pista.text = jugador.pista

        //Inicialització de les Views pel penjat
        tvLletresUtilitzades = findViewById(R.id.tvLletresUtilitzades)
        btnComprovar = findViewById(R.id.btnComprovar)
        provarLletraInput = findViewById(R.id.provarLletraInput)
        llistaLletresComprovades = findViewById(R.id.llistaLletresComprovades)
        btnReset = findViewById(R.id.btnReset)
        btnSortir = findViewById(R.id.btnSortir)


        //Gestionant la paraula i les barres que sortiran a sota
        val wordContainer = findViewById<LinearLayout>(R.id.ll_word_container)

        //Aquest for l'ha fet la sombra, ombra en català
        for (i in partida.paraulaSecreta.indices) {
            // Crear un nou TextView per a cada lletra
            val tvLletra = TextView(this).apply {

                text = "_" // Totes comencen amb la barra baixa
                textSize = 30f
                setPadding(10, 0, 10, 0) // Afegir espaiat lateral per separar les lletres
            }
            // Afegir el TextView al LinearLayout
            wordContainer.addView(tvLletra)
            lletresEnPantalla.add(tvLletra)
        }

        //TV MISSATGES D'ESTAT
        tvMissatgesEstat = findViewById(R.id.tv_missatges_estat)
        tvMissatgesEstat.text = "Comença el joc!"
    }

    // Funció per getionar les lletres utilitzades
    fun omplirLletresUtilitzades(view: View){
        val input = provarLletraInput.text.toString().trim() //Convertim l'input a text

        if (input.isEmpty() || input.length !=1 ){
            tvMissatgesEstat.text = "Introdueix només UNA lletra."
            return
        }

        val lletraProvada = input.uppercase() //El fem majus i guardem
        provarLletraInput.text.clear() //Netegem el TextEdit

        val resultat = partida.comprovarLletra(lletraProvada)
        //Li afegim un guió (sombra)
        val lletresOrdenades = partida.lletresUtilitzadesString.toCharArray().sorted().joinToString(" - ")
        llistaLletresComprovades.text = lletresOrdenades


        when (resultat) {
            1 -> { // ENCERT
                tvMissatgesEstat.text = "Encert! La lletra '$lletraProvada' hi és."
                revelarLletres(lletraProvada)
            }
            -1 -> { // ERROR
                tvMissatgesEstat.text = "Error! La lletra '$lletraProvada' no hi és."
                ensenyarImatge()
            }
            0 -> { // DUPLICAT
                tvMissatgesEstat.text = "Lletra '$lletraProvada' ja provada!"
            }
        }
    }

    private fun ensenyarImatge(){
        val indexRevelar = partida.errorsActuals - 1
        if (indexRevelar >= 0 && indexRevelar < pecesPenjat.size) {
            pecesPenjat[indexRevelar].visibility = View.VISIBLE

            comprovarFinalPartida()
        }
    }


    private fun comprovarFinalPartida() {
        // Comprovació derrota
        val maxErrors = pecesPenjat.size
        if (partida.isDerrota) {
            tvMissatgesEstat.text = "HAS PERDUT! El Penjat s'ha completat."

            btnComprovar.isEnabled = false
            provarLletraInput.isEnabled = false
            return
        }
        // Comprovació victòria
        if (isParaulaCompletada()) {
            tvMissatgesEstat.text = "HAS GUANYAT! Guanyes una cervesa gratis."
            btnComprovar.isEnabled = false
            provarLletraInput.isEnabled = false
        }
    }


    private fun revelarLletres(lletraEncertada: String){
        val lletraChar = lletraEncertada.first()
        for (i in partida.paraulaSecreta.indices){
            if (partida.paraulaSecreta[i] == lletraChar){
                lletresEnPantalla[i].text = lletraEncertada
            }
        }
        comprovarFinalPartida()
    }

    private fun isParaulaCompletada(): Boolean {
        for (tvLletra in lletresEnPantalla){
            if (tvLletra.text == "_"){
                return false
            }
        }
        return true
    }

    fun sortirPartida(view: View){
        finish()
    }

    fun reiniciarPartida(view:View){
        val paraulaDelJoc = partida.paraulaSecreta
        val maxErrorsJoc = pecesPenjat.size
        partida = PartidaPenjat(paraulaDelJoc, maxErrorsJoc)
        //Netejar la UI
        tvMissatgesEstat.text = "Joc Reiniciat!"
        llistaLletresComprovades.text=""
        provarLletraInput.text.clear()

        //Amaguem les peces del penjat
        for (peça in pecesPenjat){
            peça.visibility = View.INVISIBLE
        }

        //Tornar a posar les barres_baixes
        for(tvLletra in lletresEnPantalla){
            tvLletra.text = "_"
        }

        //Reactivar els botons per si ja havien guanyat o perdut
        btnComprovar.isEnabled = true
        provarLletraInput.isEnabled = true
    }
}