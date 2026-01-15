package com.example.saturnus

import android.os.Bundle
import android.view.View
import android.widget.GridView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.saturnus.ui.theme.SaturnusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        setContentView(com.example.saturnus.R.layout.activity_main)
//        setContentView(R.layout.relative_layout)

//        val gridView = findViewById<View>(R.id.grid_view) as GridView

//        gridView.adapter = ImageAdapter(this)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SaturnusTheme {
        Greeting("Android")
    }
}