package com.example.bab2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // BAB 2 - ganti layout yang ingin ditampilkan:
        //setContentView(R.layout.activity_main)
        //setContentView(R.layout.relative_layout)
        //setContentView(R.layout.gridview)
        setContentView(R.layout.tugas_cuaca_relative)


        //val gridView = findViewById<GridView>(R.id.grid_view)
        //gridView.adapter = ImageAdapter(this)
    }
}