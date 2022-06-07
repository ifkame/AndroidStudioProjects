package ie2a2190402.event02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                imageView.setImageResource(R.drawable.like_english)
            }else{
                imageView.setImageResource(R.drawable.like_normal)
            }
            i = 1 - i
            return@setOnLongClickListener true
        }

        var count = 0
        imageView.setOnClickListener {
            count++
            textView.text = count.toString()
        }

        /*
            //ボタン２ のテキスト表示を変える
            button2.text = "ABC"
         */

    }
}