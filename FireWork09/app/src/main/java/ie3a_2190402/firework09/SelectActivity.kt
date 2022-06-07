package ie3a_2190402.firework09

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        //受け取った変数を入れる
        val name = intent.getStringExtra("NAME")
        val user = intent.getStringArrayListExtra("USER")
        val pass = intent.getStringArrayListExtra("PASSWORD")

        // レイアウトのオブジェクト取得
        val nametext = findViewById<TextView>(R.id.s_title_text2)
        val GameStartButton = findViewById<Button>(R.id.g_start_button)
        val RankingButton = findViewById<Button>(R.id.ranking_button)

        // 名前のテキスト変更
        nametext.text = name!!.toString()

        GameStartButton.setOnClickListener {    //ゲーム開始ボタンクリック時
            //Intentのインスタンスを作成
            val intent = Intent(this, GameActivity::class.java)
            //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
            intent.putExtra("NAME",name)
            startActivity(intent)   //画面遷移を開始
        }

        RankingButton.setOnClickListener {      //ランキングボタンクリック時
            //Intentのインスタンスを作成
            val intent = Intent(this, RankingActivity::class.java)
            //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
            intent.putExtra("NAME",name)
            startActivity(intent)   //画面遷移を開始
        }
    }
}