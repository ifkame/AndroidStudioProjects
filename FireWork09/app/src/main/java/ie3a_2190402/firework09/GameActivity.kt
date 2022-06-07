package ie3a_2190402.firework09

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import ie3a_2190402.firework09.R
import ie3a_2190402.firework09.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    /*
        バインドクラスのインスタンスの作成
        クラス名の命名規則 ...「対応するxmlの名前」+「Binding」(文字の始まりは大文字)
        例): activity_main.xml → ActivityMainBinding になる
     */
    private lateinit var binding: ActivityGameBinding
    private var result_count: Int = 0                          // 勝利数
    private var draw_count: Int = 0                            // 引き分け数
    private var game_count: Int = 0                            // 試合数

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // 変数の初期化
        val RockButton: Button = findViewById(R.id.rock_button)         //  グーボタン
        val SissorsButton: Button = findViewById(R.id.sissors_button)   //  チョキボタン
        val PaperButton: Button = findViewById(R.id.paper_button)       //  パーボタン
        val ResetButton: Button = findViewById(R.id.reset_button)       //  リセットボタン
        val JankenText: TextView = findViewById(R.id.janken_text)       //  自分の選択(グー・チョキ・パー)テキスト
        val CountText: TextView = findViewById(R.id.result_count)       //  勝利数テキスト
        val GameText: TextView = findViewById(R.id.game_count)          //  試合数テキスト
        val ResultText: TextView = findViewById(R.id.result_text)       //  結果テキスト

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,TopFragment())        //最初の Fragment 呼び出し
            addToBackStack(null)
            commit()
        }

        // グーボタンクリック時
        RockButton.setOnClickListener {
            JankenText.text = "あなたはグーを出しました！"
            RandomJanken(0)         // AIが じゃんけん の手をランダムで出す
            game_count++
            GameText.text = "試合数：$game_count"
        }
        // チョキボタンクリック時
        SissorsButton.setOnClickListener {
            JankenText.text = "あなたはチョキを出しました！"
            RandomJanken(1)         // AIが じゃんけん の手をランダムで出す
            game_count++
            GameText.text = "試合数：$game_count"
        }
        //パークリック時
        PaperButton.setOnClickListener {
            JankenText.text = "あなたはパーを出しました！"
            RandomJanken(2)         // AIが じゃんけん の手をランダムで出す
            game_count++
            GameText.text = "試合数：$game_count"
        }
        //リセットボタンクリック時
        ResetButton.setOnClickListener {
            // 以降で内容を初期化
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container,TopFragment())        //最初の Fragment 呼び出し
                addToBackStack(null)
                commit()
            }
            JankenText.text = "AIはあなたを待っている..."
            result_count = 0
            game_count = 0
            CountText.text = "勝利数：$result_count"
            GameText.text = "試合数：$game_count"
            ResultText.text = ""
        }
    }

    // ランダムで、AIのじゃんけんの手を考えるメソッド
     fun RandomJanken(mine : Int) {
         val ResultText: TextView = findViewById(R.id.result_text)      // 結果テキスト
         val CountText: TextView = findViewById(R.id.result_count)      // 勝利数テキスト
         var ResultFlag: Int = 0                                        // 勝敗結果フラグ

         var randomAI = (0..2).random()        // AIのじゃんけんの手

         // randomAI,mineのとき 0: グー, 1: チョキ, 2: パー
         // Flagのとき 0: 相子, 1: 勝ち, 2: 負け
         when (randomAI) {
             0 -> {     // AI:グー のとき
                 supportFragmentManager.beginTransaction().apply {
                     replace(R.id.container,RockFragment())        //グーの Fragment 呼び出し
                     addToBackStack(null)
                     commit()
                 }
                 when (mine) {
                     0 -> ResultFlag = 0    // 自分：グー, 結果:相子
                     1 -> ResultFlag = 2    // 自分：チョキ, 結果:負け
                     2 -> ResultFlag = 1    // 自分：パー, 結果:勝ち
                 }
             }
             1 -> {     // AI:チョキ のとき
                 supportFragmentManager.beginTransaction().apply {
                     replace(R.id.container,ScissorsFragment())        //チョキの Fragment 呼び出し
                     addToBackStack(null)
                     commit()
                 }
                 when (mine) {
                     0 -> ResultFlag = 1    // 自分：グー, 結果:勝ち
                     1 -> ResultFlag = 0    // 自分：チョキ, 結果:相子
                     2 -> ResultFlag = 2    // 自分：パー, 結果:負け
                 }
             }
             2 -> {     // AI:パー のとき
                 supportFragmentManager.beginTransaction().apply {
                     replace(R.id.container,PaperFragment())        //パーの Fragment 呼び出し
                     addToBackStack(null)
                     commit()
                 }
                 when (mine) {
                     0 -> ResultFlag = 2    // 自分：グー, 結果:負け
                     1 -> ResultFlag = 1    // 自分：チョキ, 結果:勝ち
                     2 -> ResultFlag = 0    // 自分：パー, 結果:相子
                 }
             }
         }

         when (ResultFlag) {
             0 -> {                                     // 結果:相子 のとき
                 ResultText.text = "AIと相子でした"
                 draw_count++
             }
             1 -> {                                     // 結果:勝ち のとき
                 ResultText.text = "AIに勝利した"
                 result_count++                            // 勝利数インクリメント
                 CountText.text = "勝利数：$result_count"    // 勝利数テキスト変更
             }
             2 -> ResultText.text = "AIに負けました"      // 結果:負け のとき
         }
     }

    //ユーザーがアクティビティを離れる最初に、システムはこのメソッドを呼び出す
    override fun onPause() {
        changePage()
        super.onPause()
    }

    //端末の戻るボタンクリック時
    override fun onBackPressed() {
        changePage()
        super.onBackPressed()
    }

    //ページが変わるまたはアクティビティを離れる最初のときに行う処理
    fun changePage() {
        //受け取った変数を入れる
        val name = intent.getStringExtra("NAME")
        //↓引き分けのときの得点での計算
        //val score = result_count * 20 + draw_count * 10
        val score = result_count * 10
        val scoredb = FirebaseDatabase.getInstance().getReference("score")    //スコアデータベース

        AlertDialog.Builder(this) // FragmentではActivityを取得して生成
            .setTitle("ページの遷移が確認されました")
            .setMessage("得点の更新または追加を行いますか？")
            .setPositiveButton("OK") { dialog, which ->
                // TODO:Yesが押された時の挙動
                // 名前：user の子要素に、名前：username と値：password の組を追加することができる。
                scoredb.child(name.toString()).setValue(score.toString())
                //Intentのインスタンスを作成
                val intent = Intent(this, SelectActivity::class.java)
                //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
                intent.putExtra("NAME",name)
                startActivity(intent)   //画面遷移を開始
            }
            .setNegativeButton("No") { dialog, which ->
                // TODO:Noが押された時の挙動
                //Intentのインスタンスを作成
                val intent = Intent(this, SelectActivity::class.java)
                //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
                intent.putExtra("NAME",name)
                startActivity(intent)   //画面遷移を開始
            }
            .setNeutralButton("Cancel") { dialog, which ->
                // TODO:Cancelが押された時の挙動
                return@setNeutralButton
            }
            .setIcon(R.mipmap.ic_launcher)
            .show()
    }
}