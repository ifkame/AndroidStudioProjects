package com.example.eventwork03

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sab.*

class SabActivity : AppCompatActivity() {

    //それぞれの mp3 を初期化
    lateinit var mp1:MediaPlayer
    lateinit var mp2:MediaPlayer
    lateinit var mp3:MediaPlayer
    lateinit var mp4:MediaPlayer
    lateinit var mp5:MediaPlayer
    lateinit var mp6:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sab)

        textView_s4.text = "https://eplus.jp/sf/classic/orchestra?block=true&chiho_filter=11&sokensu=208"

        mp1 = MediaPlayer.create(this,R.raw.classic_dark)
        mp2 = MediaPlayer.create(this,R.raw.classic_bright)
        mp3 = MediaPlayer.create(this,R.raw.medievalcastle)
        mp4 = MediaPlayer.create(this,R.raw.otosaretarakuen)
        mp5 = MediaPlayer.create(this,R.raw.piano13_arrange)
        mp6 = MediaPlayer.create(this,R.raw.sinfonia01)

        //音楽の状態を格納
        //０： 停止中、１： 「クラシック/暗め」再生中、２： 「クラシック/明るめ」再生中、３： 「中世の城」再生中
        //４： 「滅びた楽園」再生中、５： 「ピアノ13/アレンジ」再生中、６： 「シンフォニア第1番」再生中
        var music_count = 0

        //戻るボタンクリック時
        button_s1.setOnClickListener {
            //全ての曲の停止
            var i = 0
            for (i in 1..5){
                pause(music_count)
            }
            //他の Activity に移動するための情報
            var intent = Intent(this, MainActivity::class.java)
            //intent で指定した Activity に移動
            startActivity(intent)
        }

        //「クラシック/暗め」をクリック時
        button_s2.setOnClickListener {
            //曲が流れていないとき または 他の曲が流れているとき
            if (music_count != 1){
                //音楽の状態を[「クラシック/暗め」再生]変更
                music_count = 1
                //テキスト表示を変更
                textView_s1.text = "「クラシック/暗め」再生中"
                //曲の停止
                pause(music_count)
                //「クラシック/暗め」をタップするタイミングで曲を流す
                mp1.start() //  「クラシック/暗め」再生
            }else{
                //テキスト表示を変更
                textView_s1.text = "「クラシック/暗め」ポーズ中"
                //曲の停止
                pause(music_count)
                //音楽の状態を[停止]に変更
                music_count = 0
            }
        }

        //「クラシック/明るめ」をクリック時
        button_s3.setOnClickListener {
            //曲が流れていないとき または 他の曲が流れているとき
            if (music_count != 2){
                //音楽の状態を[「クラシック/明るめ」再生]変更
                music_count = 2
                //テキスト表示を変更
                textView_s1.text = "「クラシック/明るめ」再生中"
                //曲の停止
                pause(music_count)
                //「クラシック/明るめ」をタップするタイミングで曲を流す
                mp2.start() //  「クラシック/明るめ」再生
            }else{
                //テキスト表示を変更
                textView_s1.text = "「クラシック/明るめ」ポーズ中"
                //曲の停止
                pause(music_count)
                //音楽の状態を[停止]に変更
                music_count = 0
            }
        }

        //「中世の城」をクリック時
        button_s4.setOnClickListener {
            //曲が流れていないとき または 他の曲が流れているとき
            if (music_count != 3){
                //音楽の状態を[「中世の城」再生]変更
                music_count = 3
                //テキスト表示を変更
                textView_s1.text = "「中世の城」再生中"
                //曲の停止
                pause(music_count)
                //「中世の城」をタップするタイミングで曲を流す
                mp3.start() //  「中世の城」再生
            }else{
                //テキスト表示を変更
                textView_s1.text = "「中世の城」ポーズ中"
                //曲の停止
                pause(music_count)
                //音楽の状態を[停止]に変更
                music_count = 0
            }
        }

        //「滅びた楽園」をクリック時
        button_s5.setOnClickListener {
            //曲が流れていないとき または 他の曲が流れているとき
            if (music_count != 4){
                //音楽の状態を[「滅びた楽園」再生]変更
                music_count = 4
                //テキスト表示を変更
                textView_s1.text = "「滅びた楽園」再生中"
                //曲の停止
                pause(music_count)
                //「滅びた楽園」をタップするタイミングで曲を流す
                mp4.start() //  「滅びた楽園」再生
            }else{
                //テキスト表示を変更
                textView_s1.text = "「滅びた楽園」ポーズ中"
                //曲の停止
                pause(music_count)
                //音楽の状態を[停止]に変更
                music_count = 0
            }
        }

        //「ピアノ13/アレンジ」をクリック時
        button_s6.setOnClickListener {
            //曲が流れていないとき または 他の曲が流れているとき
            if (music_count != 5){
                //音楽の状態を[「ピアノ13/アレンジ」再生]変更
                music_count = 5
                //テキスト表示を変更
                textView_s1.text = "「ピアノ13/アレンジ」再生中"
                //曲の停止
                pause(music_count)
                //「中世の城」をタップするタイミングで曲を流す
                mp5.start() //  「ピアノ13/アレンジ」再生
            }else{
                //テキスト表示を変更
                textView_s1.text = "「ピアノ13/アレンジ」ポーズ中"
                //曲の停止
                pause(music_count)
                //音楽の状態を[停止]に変更
                music_count = 0
            }
        }

        //「シンフォニア第1番」をクリック時
        button_s7.setOnClickListener {
            //曲が流れていないとき または 他の曲が流れているとき
            if (music_count != 6){
                //音楽の状態を[「シンフォニア第1番」再生]変更
                music_count = 6
                //テキスト表示を変更
                textView_s1.text = "「シンフォニア第1番」再生中"
                //曲の停止
                pause(music_count)
                //「シンフォニア第1番」をタップするタイミングで曲を流す
                mp6.start() //  「シンフォニア第1番」再生
            }else{
                //テキスト表示を変更
                textView_s1.text = "「シンフォニア第1番」ポーズ中"
                //曲の停止
                pause(music_count)
                //音楽の状態を[停止]に変更
                music_count = 0
            }
        }
    }

    fun pause(no:Int){
        mp1.start()
        mp2.start()
        mp3.start()
        mp4.start()
        mp5.start()
        mp6.start()


        if(no != 1){
            mp1.seekTo(0)
        }
        if (no != 2){
            mp2.seekTo(0)
        }
        if (no != 3){
            mp3.seekTo(0)
        }
        if (no != 4){
            mp4.seekTo(0)
        }
        if (no != 5){
            mp5.seekTo(0)
        }
        if (no != 6){
            mp6.seekTo(0)
        }

        mp1.pause()
        mp2.pause()
        mp3.pause()
        mp4.pause()
        mp5.pause()
        mp6.pause()
    }
}