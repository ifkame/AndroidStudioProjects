package com.example.myschedule06

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import com.example.myschedule06.Defines.Companion.sFmt
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : ListActivity() , Defines {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }

    override fun onListItemClick(l: ListView, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        val date = l.adapter.getItem(position) as String

        try {
            val dtime = sFmt.parse(date)
            val intent = Intent(this, Edit::class.java)
            
            intent.putExtra(Defines.KEY_DATE,dtime.time)
            
            startActivity(intent)
        } catch (e : ParseException){
            
        }
    }

    // 今月の日付の一覧をリストにして返却するselectDays()メソッド
    private fun selectDays(): ArrayList<String> {
        val ret = ArrayList<String>()

        // 今日のカレンダーを取得
        val cal = Calendar.getInstance()

        // 月の最大日数分だけ繰り返す
        val maxday = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 0 until maxday) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1)
            // 整形した日付の文字列をリストに追加する
            val datestr = sFmt.format(cal.time)
            ret.add(datestr)
        }
        return ret
    }
}