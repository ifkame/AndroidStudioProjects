package ie3a_2190402.firework09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RankingActivity : AppCompatActivity() {
    var UserNameList: ArrayList<String> = ArrayList<String>()    //ユーザー名リストのオブジェクト生成
    var ScoreList: ArrayList<String> = ArrayList<String>()    //スコアリストのオブジェクト生成
    lateinit var cAdapter: ContentAdapter                      //内容アダプターのオブジェクト生成
    var ContentList: ArrayList<ContentData> = ArrayList<ContentData>()
    //var ContentList: ArrayList<ContentData>? = null    //内容リストのオブジェクト生成

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)



        //受け取った変数を入れる
        val name = intent.getStringExtra("NAME")

        // レイアウトのオブジェクト取得
        val nametext = findViewById<TextView>(R.id.r_textView1)
        val rankingtext = findViewById<TextView>(R.id.r_textView3)

        // 名前のテキスト変更
        nametext.text = name!!.toString()

        val scoredb = FirebaseDatabase.getInstance().getReference("score")    //スコアデータベース

        scoredb.addValueEventListener(object : ValueEventListener {
            //データ取得成功
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //リスト情報をリセット
                UserNameList.clear()
                ScoreList.clear()
                ContentList.clear()  //読み込み時に内容リストの内容をリセット

                for (postSnapshot in dataSnapshot.children) {
                    UserNameList.add(postSnapshot.key as String)
                    ScoreList.add(postSnapshot.value as String)
                }

                for (i in UserNameList.indices) {
                    ContentList.add(ContentData(i+1, R.mipmap.ic_launcher, UserNameList[i].toString(), ScoreList[i].toInt()))    // 内容データの作成
                }

                this@RankingActivity.runOnUiThread {
                    /*
                        sortedWith :指定されたコンパレータ「()内」に従ってソート
                        compareBy :ソートで並び替えたい値を左から順に記述
                        it で前もって指定した変数を取ってくることが出来る
                     */
                    /*var sortedContentList = ContentList
                        .sortedWith(compareBy({ it.content }))*/

                    ContentList.sortBy { it.score }     //得点の低い順でソート
                    ContentList.reverse()               //並びを逆にすることで得点の高い順に変更

                    var n = 0   //順位を格納する変数

                    //得点順の順位に配列を格納し直す
                    for (i in UserNameList.indices) {
                        n++ //順位をインクリメント
                        ContentList[i] = ContentData(n, R.mipmap.ic_launcher, ContentList[i].user, ContentList[i].score)    // 内容データの再作成

                        if (nametext.text.toString() == ContentList[i].user){   //名前と一致したとき
                            rankingtext.text = ContentList[i].order_number.toString()   //自分の順位を格納（変更）
                        }

                        when {
                            i == ContentList.size-1 -> {}       //内容の要素数-1 に達したとき（処理を行わない）
                            ContentList[i].score == ContentList[i+1].score -> { //同着のとき 順位
                                n--
                            }
                            ContentList[i].score != ContentList[i+1].score -> {
                                n = i+1
                            }
                        }
                    }
                    // ユーザーのRecyclerViewの取得
                    val RankingRecyclerView = findViewById<RecyclerView>(R.id.content_RecyclerView)

                    // LayoutManagerの設定
                    RankingRecyclerView.layoutManager = LinearLayoutManager(this@RankingActivity)

                    // CustomAdapterの生成と設定
                    cAdapter = ContentAdapter(ContentList)
                    RankingRecyclerView.adapter = cAdapter
                }
            }
            //データ取得失敗
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "データ取得失敗", Toast.LENGTH_LONG).show()
            }
        })
    }
}