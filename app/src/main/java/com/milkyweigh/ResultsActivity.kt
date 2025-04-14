package com.milkyweigh

import android.content.Intent
import android.graphics.Typeface
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/* The ResultsActivity in the MilkyWeigh app shows your weight on different planets in a clear and visually pleasing way.
Each planet’s info is shown in a neat row with soft colors, rounded edges, and a bit of shadow to make it stand out.
The text is easy to read, with bold names, your calculated weight, and a short description all nicely spaced out.
The layout works well on different screen sizes, and there’s a retry button at the bottom so users can quickly go back and enter a new weight. */

class ResultsActivity : AppCompatActivity() {

    private lateinit var resultsTableLayout: TableLayout
    private lateinit var retryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        resultsTableLayout = findViewById(R.id.resultsTableLayout)
        retryButton = findViewById(R.id.retryButton)

        val results = intent.getStringExtra("RESULTS")
        results?.let {
            populateResults(it)
        } ?: run {
            val emptyRow = createTableRow("No results available", "", "")
            resultsTableLayout.addView(emptyRow)
        }

        retryButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun populateResults(results: String) {
        val lines = results.split("\n")
        for (line in lines) {
            val parts = line.split(":")
            if (parts.size == 2) {
                val planetInfo = parts[0].trim()
                val weightAndDesc = parts[1].trim().split("-")
                val weight = weightAndDesc[0].trim()
                val description = weightAndDesc.getOrNull(1)?.trim() ?: ""

                val newRow = createTableRow(planetInfo, weight, description)
                resultsTableLayout.addView(newRow)

            }
        }
    }

    private fun createTableRow(planetName: String, weight: String, description: String): TableRow {
        val row = TableRow(this).apply {
            elevation = 20f
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT

            ).apply {
                setMargins(0, 20, 0, 20)
                setBackgroundColorWithRadius("#1A1A2ECC", 40f)
            }
            setPadding(10,20,10,20)

        }

        val nameTextView = TextView(this).apply {
            text = planetName
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f)
            textSize = 15f
            setTypeface(null, Typeface.BOLD)
            setPadding(20, 10, 10, 20)
            setTextColor(Color.parseColor("#4B2E6D"))
        }

        val weightTextView = TextView(this).apply {
            text = weight
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f)
            setPadding(16, 16, 16, 20)
            setTextColor(Color.parseColor("#4B2E6D"))
        }

        val descTextView = TextView(this).apply {
            text = description
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3f)
            setPadding(16, 16, 16, 20)
            setTextColor(Color.parseColor("#4B2E6D"))
        }

        row.addView(nameTextView)
        row.addView(weightTextView)
        row.addView(descTextView)

        return row
    }

    private fun TableRow.setBackgroundColorWithRadius(color: String, cornerRadius: Float) {
        val backgroundDrawable = GradientDrawable().apply {
            setColor(Color.parseColor(color))
            this.cornerRadius = cornerRadius
        }
        background = backgroundDrawable
    }
}