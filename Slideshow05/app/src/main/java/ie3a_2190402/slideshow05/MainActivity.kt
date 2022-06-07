//IE3A18 藤村伊織

package ie3a_2190402.slideshow05

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ie3a_2190402.slideshow05.databinding.ActivityMainBinding
import kotlin.concurrent.timer

//MainActivity の変更
class MainActivity : AppCompatActivity() {

    // 課題 05_1 用に内部クラスを追加
    class MyAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private val resources = listOf(
            R.drawable.m01,R.drawable.m02,R.drawable.m03,
            R.drawable.m04,R.drawable.m05,R.drawable.m06,
            R.drawable.m07,R.drawable.m08,R.drawable.m09,
            R.drawable.m10,R.drawable.m11,R.drawable.m12
        )
        override fun getItemCount(): Int = resources.size // 画面（画像）数を得る
        override fun createFragment(position: Int): Fragment
                = ImageFragment.newInstance((resources[position])) // 指定したﾘｿｰｽ ID のﾌﾗｸﾞﾒﾝﾄを作成する
    }

    private lateinit var binding: ActivityMainBinding

    //課題 05_3 用の音楽パラメータ
    //音楽の格納リスト「mp3」
    val mp3 = arrayOfNulls<MediaPlayer>(2)
    //音楽のリソースを格納「mp3text」
    val mp3text = arrayOf(R.raw.arms_of_heaven, R.raw.beneath_the_moonlight)
    //選択した曲のテキスト「itemText」
    var itemtext = arrayOf("arms_of_heaven", "beneath_the_moonlight")
    //音楽の状態を格納
    var music_count = 0

    //７．タイマーを使ってスライドショーを実装
    //MainActivity.kt 内の onCreate メソッド内に以下の処理を追加
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 課題 05_1 用に下記変更
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pager.adapter = MyAdapter(this) // ViewPager2 と FragmentStateAdapter を関連付ける

        val handler = Handler(Looper.getMainLooper())

        // ３秒ごとに画像を切り替える仕様とする
        timer(period = 3000){
            handler.post{
                binding.apply {
                    pager.currentItem = (pager.currentItem + 1 ) % 12
                }
            }
        }

        //課題 05_3 用の音楽パラメータ
        //音楽を配列に格納
        for (i in mp3.indices) {    //mp3の要素数までループ
            mp3[i]= MediaPlayer.create(applicationContext, mp3text[i])
        }

        // 3×6秒ごとに画像を切り替える仕様とする
        timer(period = 18000){
            handler.post{
                binding.apply {
                    //曲の一時停止
                    pause(music_count)
                    //曲を流す
                    mp3[music_count]?.start()
                    // トーストで表示する
                    Toast.makeText(applicationContext, itemtext[music_count], Toast.LENGTH_LONG).show()

                    music_count = 1 - music_count
                }
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