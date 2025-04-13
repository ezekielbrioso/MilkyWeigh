package com.milkyweigh

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

/* This Kotlin code defines an Android app where users can enter their weight on Earth and see how much they'd weigh on other planets (including the Moon and Pluto).
The MainActivity handles the user interface, including a text field for weight input, a button to submit, a results display, and an info button.
When the user hits submit, the app checks if the input is valid, calculates the weight on different celestial bodies using gravity ratios, and attaches fun, imaginative descriptions for each.
Then, it sends the results to a new screen called ResultsActivity. If the input isn’t valid (e.g. the user submitted an empty field), it shows up an error dialogue.
The info button shows a separate dialog that shows instructions of the app. */

class MainActivity : AppCompatActivity() {

    private lateinit var weightInput: EditText
    private lateinit var submitButton: Button
    private lateinit var results: TextView
    private lateinit var infoButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weightInput = findViewById(R.id.weightInput)
        submitButton = findViewById(R.id.submitButton)
        results = findViewById(R.id.results)
        infoButton = findViewById(R.id.infoButton)

        submitButton.setOnClickListener {
            calculateWeight()
        }

        infoButton.setOnClickListener {
            showInfoDialog()
        }
    }


    private fun calculateWeight() {
        val earthWeight = weightInput.text.toString().toDoubleOrNull()
        Log.d("MainActivity", "Input weight: $earthWeight")
        if (earthWeight != null) {
            val weights = calculateWeights(earthWeight)
            val resultText = formatResults(weights)
            Log.d("MainActivity", "Calculated results: $resultText")

            val intent = Intent(this, ResultsActivity::class.java)
            intent.putExtra("RESULTS", resultText)
            startActivity(intent)
        } else {
            showErrorDialog("Please enter a valid weight.")
        }
    }

    private fun calculateWeights(earthWeight: Double): Map<String, Pair<Double, String>> {
        val gravityMap = mapOf(
            "Mercury" to 3.7,
            "Venus" to 8.87,
            "Mars" to 3.721,
            "Jupiter" to 24.79,
            "Saturn" to 10.44,
            "Uranus" to 8.69,
            "Neptune" to 11.15,
            "Pluto" to 0.62,
            "Moon" to 1.625
        )

        val results = mutableMapOf<String, Pair<Double, String>>()
        for ((planet, gravity) in gravityMap) {
            val weightOnPlanet = earthWeight * (gravity / 9.807)
            val description = getDescription(planet)
            results[planet] = Pair(weightOnPlanet, description)
        }
        return results
    }

    private fun getDescription(planet: String): String {
        return when (planet) {
            "Mercury" -> "On Mercury, you’d be as light as a feather, weighing just 38% of your Earth weight! Zooming around like a shooting star!"
            "Venus" -> "On Venus, you’d feel a bit more substantial at 91% of your Earth weight! Radiating beauty like a glowing evening star!"
            "Mars" -> "On Mars, you’d float with ease at only 38% of your Earth weight! Dancing like a moonbeam in the Martian breeze!"
            "Jupiter" -> "On Jupiter, you’d be a heavyweight champion at 240% of your Earth weight! A colossal giant among the clouds!"
            "Saturn" -> "On Saturn, you’d weigh about 107% of your Earth weight! Adorned with rings, you’d be the star of the cosmic fashion show!"
            "Uranus" -> "On Uranus, you’d feel cool and collected at 91% of your Earth weight! Gliding through the icy winds like a majestic ice giant!"
            "Neptune" -> "On Neptune, you’d weigh around 114% of your Earth weight! Deep and mysterious, you’d be a voyager of the oceanic depths!"
            "Pluto" -> "On Pluto, you’d be as light as a whisper, weighing just 38% of your Earth weight! A distant wanderer in the icy realms!"
            "Moon" -> "On the Moon, you’d bounce around at a mere 16.5% of your Earth weight! Light as a feather, ready to leap into the lunar sky!"
            else -> "You’re out of this world! Your weight is just a cosmic number in the grand universe!"
        }
    }

    private fun formatResults(weights: Map<String, Pair<Double, String>>): String {
        val resultText = StringBuilder()
        for ((planet, weightPair) in weights) {
            resultText.append("${planet}: ${String.format("%.2f", weightPair.first)} kg - ${weightPair.second}\n")
        }
        return resultText.toString()
    }

    private fun showInfoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.modal_view, null)
        val modalTitle: TextView = dialogView.findViewById(R.id.modalTitle)
        val modalContent: TextView = dialogView.findViewById(R.id.modalContent)
        val closeButton: Button = dialogView.findViewById(R.id.closeButton)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showErrorDialog(message: String) {
        val dialogView = layoutInflater.inflate(R.layout.modal_view, null)
        val modalTitle: TextView = dialogView.findViewById(R.id.modalTitle)
        val modalContent: TextView = dialogView.findViewById(R.id.modalContent)
        val closeButton: Button = dialogView.findViewById(R.id.closeButton)

        modalTitle.text = "Error"
        modalContent.text = message

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}