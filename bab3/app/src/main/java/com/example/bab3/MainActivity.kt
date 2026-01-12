package com.example.bab3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.tugas_cuaca)

        val info = findViewById<TextView>(R.id.tvInfo)
        findViewById<Button>(R.id.btnRefresh).setOnClickListener {
            info.text = "Info: Terakhir diperbarui (dummy)"
        }
    }
}
