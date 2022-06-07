package com.example.uidesigntest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Xml
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import org.xmlpull.v1.XmlPullParser


class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var text1 = findViewById<TextView>(R.id.maintext1)

        text1.setOnLongClickListener { text1 ->
            Toast.makeText(this, "長押し確認", Toast.LENGTH_LONG).show()
            val view = LayoutInflater.from(this).inflate(R.layout.popupwindow, null)
            val popupWindow = PopupWindow(view).apply {
                isOutsideTouchable = true
                isFocusable = true
            }
            popupWindow.showAsDropDown(text1)
            return@setOnLongClickListener true
        }
    }
}