package com.teknos.dam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Model de dades per a cada carta
data class Card(
    val imageResId: Int, // ID del drawable que representa la imatge
    var isFlipped: Boolean = false, // Estat: visible (true) o amagada (false)
    var isMatched: Boolean = false // Estat: parella trobada (true)
)

class Joc1Activity : AppCompatActivity() {

    // Referències als elements de la UI
    private lateinit var memoryGrid: GridLayout
    private lateinit var tvEncerts: TextView
    private lateinit var tvErrades: TextView

    // Llista de totes les cartes del joc (32 elements)
    private lateinit var cards: List<Card>
    // Llista de tots els ImageButton de la UI (32 botons)
    private lateinit var buttons: List<ImageButton>

    // Lògica del Joc
    private var flippedCards = mutableListOf<ImageButton>() // Cartes actualment girades (màx. 2)
    private var isBusy = false // Bloqueja els clics mentre les cartes es giren/desgiren
    private var encerts = 0
    private var errades = 0
    private val totalPairs = 16 // 32 cartes = 16 parelles

    // Defineix les 16 imatges úniques per a les parelles
    // ASSEGURA'T QUE AQUESTS DRAWABLES EXISTEIXIN A LA TEVA CARPETA 'res/drawable'!
    private val drawableIds = listOf(
        R.drawable.img_a, R.drawable.img_b, R.drawable.img_c, R.drawable.img_d,
        R.drawable.img_e, R.drawable.img_f, R.drawable.img_g, R.drawable.img_h,
        R.drawable.img_i, R.drawable.img_j, R.drawable.img_k, R.drawable.img_l,
        R.drawable.img_m, R.drawable.img_n, R.drawable.img_o, R.drawable.img_p
    )

    private val cardBackId = R.drawable.card_back // Imatge del revers de la carta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joc1)

        // 1. Inicialitzar Vistes
        memoryGrid = findViewById(R.id.memory_grid)
        tvEncerts = findViewById(R.id.tv_encerts)
        tvErrades = findViewById(R.id.tv_errades)

        // 2. Preparar el Joc
        setupGame()
    }

    private fun setupGame() {
        // 2.1 Crear i Barrejar les cartes
        // Dupliquem la llista i la barregem (16 imatges * 2 = 32 cartes)
        val shuffledImageIds = (drawableIds + drawableIds).shuffled()

        // Crear els objectes Card amb les imatges assignades i els estats inicials
        cards = shuffledImageIds.map { imageId -> Card(imageId) }

        // 2.2 Crear els botons a la UI i afegir-los al GridLayout
        buttons = (0 until 32).map { index ->
            val button = createCardButton(index)
            memoryGrid.addView(button)
            button
        }

        // 2.3 Resetear comptadors
        encerts = 0
        errades = 0
        updateScore()
        memoryGrid.visibility = View.VISIBLE
    }

    // Funció per crear i configurar cada ImageButton dinàmicament
    private fun createCardButton(index: Int): ImageButton {
        val button = ImageButton(this).apply {
            // LayoutParams per 4 columnes amb pes
            val params = GridLayout.LayoutParams().apply {
                width = 0 // Necessari per columnWeight
                height = resources.getDimensionPixelSize(R.dimen.card_height) // Defineix una alçada per consistència
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // 1f = columnWeight
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // Fes que les files també tinguin pes, si cal (ajustar segons disseny)
                setMargins(4, 4, 4, 4) // Petits marges entre cartes
            }
            layoutParams = params

            // Propietats visuals
            setImageResource(cardBackId) // Tots comencen amb el revers
            scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
            adjustViewBounds = true
            setOnClickListener { onCardClicked(this, index) }
        }
        return button
    }

    // Funció que es crida quan es clica una carta
    private fun onCardClicked(clickedButton: ImageButton, index: Int) {
        if (isBusy || cards[index].isFlipped || cards[index].isMatched) {
            return // Ignora el clic si està ocupat, ja està girada, o ja està encertada
        }

        // 1. Gira la carta
        cards[index].isFlipped = true
        clickedButton.setImageResource(cards[index].imageResId)
        flippedCards.add(clickedButton)

        // 2. Comprova si s'han girat dues cartes
        if (flippedCards.size == 2) {
            isBusy = true // Bloqueja nous clics

            val card1Index = buttons.indexOf(flippedCards[0])
            val card2Index = buttons.indexOf(flippedCards[1])

            // 3. Compara les cartes
            if (cards[card1Index].imageResId == cards[card2Index].imageResId) {
                // Són una parella!
                encerts++
                cards[card1Index].isMatched = true
                cards[card2Index].isMatched = true

                // Mantenir-les descobertes i deshabilitades
                flippedCards[0].isEnabled = false
                flippedCards[1].isEnabled = false

                checkGameEnd()
                resetFlippedCards()

            } else {
                // NO són una parella!
                errades++

                // Després d'un temps, torna-les a girar
                Handler(Looper.getMainLooper()).postDelayed({
                    flippedCards[0].setImageResource(cardBackId)
                    flippedCards[1].setImageResource(cardBackId)

                    cards[card1Index].isFlipped = false
                    cards[card2Index].isFlipped = false

                    resetFlippedCards()
                }, 1000) // Temps d'espera (1 segon)
            }
            updateScore()
        }
    }

    private fun resetFlippedCards() {
        flippedCards.clear()
        isBusy = false
    }

    private fun updateScore() {
        tvEncerts.text = getString(R.string.encerts_format, encerts)
        tvErrades.text = getString(R.string.errades_format, errades)
    }

    private fun checkGameEnd() {
        if (encerts == totalPairs) {
            Toast.makeText(this, "🎉 ENHORABONA! Has guanyat el Joc Memori!", Toast.LENGTH_LONG).show()
            memoryGrid.visibility = View.INVISIBLE // Amaga la graella
        }
    }

    // Funció cridada pel botó (necessita android:onClick="reiniciarJoc" a l'XML)
    fun reiniciarJoc(view: View) {
        memoryGrid.removeAllViews() // Elimina els botons antics
        flippedCards.clear()
        isBusy = false
        setupGame()
        Toast.makeText(this, "Joc Reiniciat!", Toast.LENGTH_SHORT).show()
    }

    // Funció cridada pel botó (necessita android:onClick="sortirJoc" a l'XML)
    fun sortirJoc(view: View) {
        // Torna a la Pantalla 1 (l'Activity principal)
        // Utilitzem finish() si aquesta Activity s'ha obert amb startActivity() des de la Pantalla 1
        finish()
        // Si la Pantalla 1 no és Main, hauries d'usar un Intent:
        // val intent = Intent(this, PantallaPrincipalActivity::class.java)
        // startActivity(intent)
    }
}