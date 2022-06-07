package com.example.intent05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_event02.*

class Event02 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event02)

        //トースト表示
        Toast.makeText(applicationContext, "Event02 が選択されました", Toast.LENGTH_LONG).show()

        //効果音管理インスタンスを生成する
        var mSoundManager = SoundManager(context = this)

        //ボタン１ がクリックされた時
        button1.setOnClickListener {
            //画像を両方ともドロイド君に変える
            imageView0.setImageResource(R.drawable.img0)
            imageView1.setImageResource(R.drawable.img0)

            //ボタン１をタップするタイミングでサウンドを鳴らす
            mSoundManager.play(SoundManager.SOUND_GUN, 100) //  銃声

            /*
                var bt = it as Button
                bt.text = "あいうえお"
             */

        }

        //ボタン２ がクリックされた時
        button2.setOnClickListener {
            //画像を両方ともドロイド君(ユニフォーム)に変える
            imageView0.setImageResource(R.drawable.img1)
            imageView1.setImageResource(R.drawable.img1)

            //ボタン２をタップするタイミングでサウンドを鳴らす
            mSoundManager.play(SoundManager.SOUND_EXPLO, 100) //  爆発

        }

        var i = 0

        //いいね の画像が長押しされた時
        imageView.setOnLongClickListener {
            if (i == 0) {
                //英語表示に変更
                imageView.setImageResource(R.drawable.like_english)
            }else{
                //元に戻す（日本語表示に変更）
                imageView.setImageResource(R.drawable.like_normal)
            }
            //切り替え用の変数を変更
            i = 1 - i
            //長押ししたときにクリックしたときの動きを行わない → TRUE
            return@setOnLongClickListener true
        }

        //クリックした回数を格納
        var count = 0
        //いいね の画像がクリックされた時
        imageView.setOnClickListener {
            //クリックした回数をインクリメント
            count++
            //クリックした回数をString型にして、表示
            textView.text = count.toString()
        }

        /*
            //ボタン２ のテキスト表示を変える
            button2.text = "ABC"
         */

    }

}