package com.example.intent05

import android.app.ListActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class List04 : ListActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list04)

        //トースト表示
        Toast.makeText(applicationContext, "List04 が選択されました", Toast.LENGTH_LONG).show()

        //日付の一覧を元に ArrayAdapter を作成する
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,selectDays())
        /*  this = コンテキスト
            android.R.layout.simple_list_item_1 = resource（レイアウト）
            selectDays() = リスト（配列など）

            context = アプリ全体の情報を管理、画面間などの情報を受け渡すために使用される
                      getApplication(), getContext(), getBaseContext()で取得可能、
                      Activityの場合は this でも可
         */

        //ArrayAdapter をリストに設定する
        listView.adapter = adapter
        listView.adapter = selectSchedule()
    }

    //今日の日付の一覧をリストにして返却する selectDays()メソッドを追加する
    private fun selectDays():ArrayList<String>{
        //作成したものを格納するリストを初期化
        val ret = ArrayList<String>()
        //日付を整形出力するためのフォーマッターを作成
        val fmt = SimpleDateFormat("MM/dd")
        //今日のカレンダーを取得
        val cal = Calendar.getInstance()
        //月の最大日数分だけ繰り返す
        val maxday = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 0 until maxday){
            cal.set(Calendar.DAY_OF_MONTH, i + 1)
            //日付の文字列を整形
            val datestr = fmt.format(cal.time)
            //リストに追加する
            ret.add(datestr)
        }
        return ret
    }

    //
    private fun selectSchedule(): SimpleAdapter {
        //今日のカレンダーを取得
        val cal = Calendar.getInstance()
        //月の最大日数分だけ繰り返す
        val maxday = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        //2つ以上のデータを表示させる Map を生成
        val items = List<Map<String, String>>(maxday, { i -> mapOf("title" to "今月の予定 No.$i", "detail" to "詳細_$i")})
        //SimpleAdapter を生成
        val adapter = SimpleAdapter(
            this,                                       //第一引数には Context を指定
            items,                                              //第二引数には表示したいデータを指定
            android.R.layout.simple_list_item_2,                //第三引数はレイアウトのIDを指定するのですが、Android 標準で用意されている2行だけのレイアウトを指定
            arrayOf("title", "detail"),                         //第四引数には表示したいデータのキー名を指定(ここでキー名が必要となってくるため Map が必要となってくる)
            intArrayOf(android.R.id.text1, android.R.id.text2)  //第五引数には表示させる部品の ID を指定
        )
        return adapter
    }

    /*
    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val date = 1!!.adapter ~~~~
    }
     */
}