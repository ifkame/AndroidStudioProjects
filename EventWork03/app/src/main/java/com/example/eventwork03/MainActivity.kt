package com.example.eventwork03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_m1.setOnClickListener {
            //他の Activity に移動するための情報
            var intent = Intent(this, SabActivity::class.java)
            //intent で指定した Activity に移動
            startActivity(intent)
        }

    }
}