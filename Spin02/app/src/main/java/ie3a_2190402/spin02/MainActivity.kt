//IE3A18 藤村伊織
package ie3a_2190402.spin02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //(a)フィールドを追加する
    // 回転数
    private var mCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //(b)onCreate メソッドに以下の処理を追加する
        // onSaveInstanceState によって設定された Bundle から値を取得
        if (savedInstanceState != null) {
            mCount = savedInstanceState.getInt("count", 0)
        }
        // カウント数（mCount）を４で割り、余り（0～4 の値）から分岐
        val imagetype = mCount % 4
        when (imagetype) {
            0 -> {
                //対応した画像を表示
                imageView.setImageResource(R.drawable.jinro)
                // カウントを表示用の TextView に設定
                setTitle("藤村伊織「食べちゃうぞー」")
            }
            1 -> {
                imageView.setImageResource(R.drawable.uranai)
                setTitle("藤村伊織「占いで全てを見通す！」")
            }
            2 -> {
                imageView.setImageResource(R.drawable.murabito)
                setTitle("藤村伊織「食べないでください～」")
            }
            3 -> {
                imageView.setImageResource(R.drawable.kaito)
                setTitle("藤村伊織「私に盗めないものはない！」")
            }
        }
        // onCreate が呼ばれるごとにカウントアップする
        mCount++

    }

    //(c)以下のメソッドを追加する
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("count", mCount)
    }
}