package com.example.intentwork07

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** BGM設定ボタン（id:mainbutton1）を押した時に呼び出される処理を追加する */
        main_button1.setOnClickListener {
            // SettingActivityクラスを呼び出すIntentを生成
            val intent = Intent(this,MusicSetting::class.java)
            // Intent呼び出しを実行する
            startActivity(intent)
        }

        /** スタートボタン（id:mainbutton2）を押した時に呼び出される処理を追加する */
        main_button2.setOnClickListener {
            // SettingActivityクラスを呼び出すIntentを生成
            val intent = Intent(this,PlayerSetting1::class.java)
            // Intent呼び出しを実行する
            startActivity(intent)
        }
    }
}