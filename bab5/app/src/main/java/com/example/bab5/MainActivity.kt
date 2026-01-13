package com.example.bab5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bab5.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnToSecond = findViewById<Button>(R.id.btnToSecond)
        val btnBrowser = findViewById<Button>(R.id.btnBrowser)
        val btnCircle = findViewById<Button>(R.id.btnCircle)

        // Explicit Intent
        btnToSecond.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        // Implicit Intent (Browser)
        btnBrowser.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
            startActivity(intent)
        }

        // Explicit Intent ke kalkulator lingkaran
        btnCircle.setOnClickListener {
            startActivity(Intent(this, CircleAreaActivity::class.java))
        }
    }
}
