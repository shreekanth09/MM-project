package com.example.workpulse.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.workpulse.R

class ReportsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }
    }
}
