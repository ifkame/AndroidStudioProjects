package com.example.intentwork07

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_music_setting.*

class MusicSetting : AppCompatActivity() , Defines {

    //音楽の格納リスト「mp3」
    val mp3 = arrayOfNulls<MediaPlayer>(6)
    //音楽のリソースを格納「mp3text」
    val mp3text = arrayOf(R.raw.classic_bright,R.raw.classic_dark,R.raw.medievalcastle,R.raw.otosaretarakuen,R.raw.piano13_arrange,R.raw.sinfonia01)
    //選択した曲の初期値「itemInt」
    var selectItemInt = -1
    var itemtext : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_setting)

        //初期メッセージの表示
        if(Defines.MUSIC_SELECT_TEXT == ""){
            ms_textView2.text = "保存:なし"
        } else {
            ms_textView2.text = "保存:" + Defines.MUSIC_SELECT_TEXT
        }
        ms_textView3.text = ""
        ms_textView4.text = "BGM にしたい曲を選んでね！"

        //音楽を配列に格納
        for (i in mp3.indices) {    //mp3の要素数までループ
            mp3[i]= MediaPlayer.create(this, mp3text[i])
        }

        //音楽の状態を格納
        //０： 停止中、１： 「クラシック/暗め」再生中、２： 「クラシック/明るめ」再生中、３： 「中世の城」再生中
        //４： 「滅びた楽園」再生中、５： 「ピアノ13/アレンジ」再生中、６： 「シンフォニア第1番」再生中
        var music_count = 0

        listView.setOnItemClickListener {parent, view, position, id ->
            // listViewのクリックされた行のテキストを取得
            itemtext = (view.findViewById<TextView>(android.R.id.text1)).text as String
            selectItemInt = position
            //選択された BGM を表示
            ms_textView3.text = "「" + itemtext + "」"
            //選択された BGM の状態 を表示
            ms_textView4.text = "停止中"
            // トーストで表示する
            Toast.makeText(applicationContext, itemtext, Toast.LENGTH_LONG).show()
        }

        //「一時停止」ボタンクリック時
        ms_imageView1.setOnClickListener {
            //テキスト表示を変更
            ms_textView3.text = "「" + itemtext + "」"
            ms_textView4.text = "ポーズ中"
            //曲の停止
            pause(music_count)
            //音楽の状態を[停止]に変更
            music_count = 0
        }

        //「開始」ボタンクリック時
        ms_imageView2.setOnClickListener {
            if (selectItemInt != -1){
                //音楽の状態を[「クラシック/暗め」再生]変更
                music_count = selectItemInt
                //テキスト表示を変更
                ms_textView2.text = "選択:" + itemtext
                ms_textView3.text = "「" + itemtext + "」"
                ms_textView4.text = "再生中"
                //曲の停止
                pause(music_count)
                //「クラシック/暗め」をタップするタイミングで曲を流す
                mp3[selectItemInt]?.start() //  「クラシック/暗め」再生
            } else {
                //テキスト表示を変更
                ms_textView3.text = "曲を選んでください"
            }
        }

        /** 「戻る」を押した時に呼び出される処理を追加する */
        ms_button1.setOnClickListener {
            //曲の停止
            pause(-1)
            // MainActivityクラスを呼び出すIntentを生成
            val intent = Intent(this,MainActivity::class.java)
            // Intent呼び出しを実行する
            startActivity(intent)
        }

        /** 「保存」を押した時に呼び出される処理を追加する */
        ms_button2.setOnClickListener {
            if (selectItemInt != -1){
                //曲の停止
                pause(-1)
                ms_textView4.text = "停止中"
                //選択された BGM を保存
                Defines.MUSIC_SELECT_TEXT = itemtext
                //保存した曲の表示
                ms_textView2.text = "保存:" + Defines.MUSIC_SELECT_TEXT
                // トーストで保存完了を表示する
                Toast.makeText(applicationContext, itemtext + "が保存されました", Toast.LENGTH_LONG).show()
            } else {
                ms_textView3.text = "曲、未選択により保存不可"
            }
        }
    }

    fun pause(no:Int){

        //全ての音楽を開始
        for (i in mp3.indices) {    //mp3の要素数までループ
            mp3[i]?.start()
        }

        //選択された音楽以外を最初の位置に戻す
        for (i in mp3.indices) {    //mp3の要素数までループ
            if (no != i){
                mp3[i]?.seekTo(0)
            }
        }

        //全ての音楽を開始
        for (i in mp3.indices) {    //mp3の要素数までループ
            mp3[i]?.pause()
        }
    }
}