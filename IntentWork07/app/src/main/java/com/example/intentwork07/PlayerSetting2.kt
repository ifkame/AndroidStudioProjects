package com.example.intentwork07

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_player_setting2.*

class PlayerSetting2 : AppCompatActivity() , Defines {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_setting2)

        //入力された参加人数
        var PlayerCount: Int = Defines.NUMBER_PEOPLE.toInt() - 1
        //for文で回す用のプレイヤーに関する TextView
        val PlayerText =listOf<TextView>(findViewById(R.id.ps2_textView1),findViewById(R.id.ps2_textView2),findViewById(R.id.ps2_textView3),findViewById(R.id.ps2_textView4),findViewById(R.id.ps2_textView5),findViewById(R.id.ps2_textView6))
        //for文で回す用のプレイヤーに関する editText
        val PlayerEdit = listOf<EditText>(findViewById(R.id.ps2_editText1),findViewById(R.id.ps2_editText2),findViewById(R.id.ps2_editText3),findViewById(R.id.ps2_editText4),findViewById(R.id.ps2_editText5),findViewById(R.id.ps2_editText6))

        //全部非表示
        for (i in 0..5){
            PlayerText[i].visibility = View.INVISIBLE
            PlayerEdit[i].visibility = View.INVISIBLE
        }

        //前ページで選択された人数分の表示
        for (i in 0..PlayerCount){
            PlayerText[i].visibility = View.VISIBLE
            PlayerEdit[i].visibility = View.VISIBLE
            //editText に保存されているプレイヤー名を表示
            PlayerEdit[i].setText(Defines.PLAY_NAME[i], TextView.BufferType.NORMAL)
        }

        //戻るボタンクリック時
        ps2_button1.setOnClickListener {
            // SettingActivityクラスを呼び出すIntentを生成
            val intent = Intent(this,PlayerSetting1::class.java)
            // Intent呼び出しを実行する
            startActivity(intent)
        }

        //次へボタンクリック時
        ps2_button2.setOnClickListener {
            //プレイヤーの名前を格納
            for (i in 0..PlayerCount){
                //プレイヤー名を格納
                Defines.PLAY_NAME[i] = PlayerEdit[i].text.toString()
            }
            // SettingActivityクラスを呼び出すIntentを生成
            val intent = Intent(this,MainActivity::class.java)
            // Intent呼び出しを実行する
            startActivity(intent)
        }
    }


}