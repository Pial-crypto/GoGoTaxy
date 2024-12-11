package com.example.go_go_taxy

import CustomGridAdapter
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class front_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_front_page)



        // Sample data
        val itemNames = arrayOf("Short trip", "Long trip", "Rental", "Delivery")
        val itemImages = arrayOf(
              R.drawable.short_trip, // Replace with your image resources
            R.drawable.long_trip_big,
             R.drawable.rental,
              R.drawable.delivery
        )

        // Set up the GridView
        val gridView: GridView = findViewById(R.id.grid_view) // Replace with your GridView ID
        val adapter = CustomGridAdapter(this, itemNames, itemImages)
        gridView.adapter = adapter

        // Item click listener
        gridView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Clicked: ${itemNames[position]}", Toast.LENGTH_SHORT).show()
        }

    }
}