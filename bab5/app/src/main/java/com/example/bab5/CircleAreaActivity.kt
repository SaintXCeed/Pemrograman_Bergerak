package com.example.bab5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bab5.R
import kotlin.math.PI

class CircleAreaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_area)

        val inputRadius = findViewById<EditText>(R.id.inputRadius)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val textResult = findViewById<TextView>(R.id.textResult)

        btnCalculate.setOnClickListener {
            val rText = inputRadius.text.toString()
            if (rText.isBlank()) {
                inputRadius.error = "Masukkan nilai r"
                return@setOnClickListener
            }

            val r = rText.toDouble()
            val luas = PI * r * r
            textResult.text = "Luas: %.2f".format(luas)
        }
    }
}