package com.example.intentwork07

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_player_setting1.*

class PlayerSetting1 : AppCompatActivity() , Defines{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_setting1)

        //戻るボタンクリック時
        ps1_button1.setOnClickListener {
            // SettingActivityクラスを呼び出すIntentを生成
            val intent = Intent(this,MainActivity::class.java)
            // Intent呼び出しを実行する
            startActivity(intent)
        }

        //次へボタンクリック時
        ps1_button2.setOnClickListener {
            // SettingActivityクラスを呼び出すIntentを生成
            val intent = Intent(this,PlayerSetting2::class.java)
            // editText のテキストを取得
            if(ps1_editText1.text != null){
                // 取得したテキストを TextView に張り付ける
                Defines.NUMBER_PEOPLE = ps1_editText1.text.toString()
            }
            // Intent呼び出しを実行する
            startActivity(intent)
        }
    }
}