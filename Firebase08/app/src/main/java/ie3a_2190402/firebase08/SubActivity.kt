package ie3a_2190402.firebase08

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        //受け取った変数を入れる
        val name = intent.getStringExtra("TEXT_KEY")

        // TextView を id:count_view で定義しておいた場合の例（id の取得）
        val nametext = findViewById<TextView>(R.id.textView2)

        // 名前のテキスト変更
        nametext.text = name



    }
}