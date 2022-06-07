package com.example.http_post

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HistoryActivity : AppCompatActivity(), OkHttp3Callback.ApiHistoryCallback {
    /**
     * API から取得するデータセットを想定したプロパティ。
     */
    //private val dataSet = mutableListOf<String>()
    private var dataSet = ArrayList<HistoryData>()

    /**
     * API に問い合わせ中は true になる。
     */
    private var nowLoading = false

    //private lateinit var myAdapter: MyAdapter
    private lateinit var historyAdapter: HistoryAdapter

    private val handler = Handler()

    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }

    var periodindex:Int = 0
    var apiRequest = OkHttp3Callback()

    /*init {

        periodindex = 99
        // 仮想データセットを生成。
        for (i in 0..99) { dataSet.add(HistoryData("$i", "翻訳前言語$i", "翻訳後言語$i", "翻訳前テキスト$i", "翻訳後テキスト$i")) }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        dataSet = ArrayList<HistoryData>()

        progressBar.visibility = View.VISIBLE
        apiRequest.setApiHistoryCallback(this@HistoryActivity)  //履歴コールバック設定
        apiRequest.getAPI_history(Defines.MY_TOKEN)             //履歴コールバック実行
    }

    /**
     * API でリストデータを取得することを想定したメソッド。
     */
    private suspend fun fetch(index: Int): List<HistoryData> {

        // API 問い合わせの待ち時間を仮想的に作る。
        handler.post { progressBar.visibility = View.VISIBLE }
        delay(100)
        handler.post { progressBar.visibility = View.INVISIBLE }
        Log.e("", "$periodindex")

        return when (index) {
            /*in 0..90 -> dataSet.slice(index..index + 9)
            in 91..99 -> dataSet.slice(index..99)
            else -> listOf()*/
            in 0 until periodindex-(periodindex%5) -> dataSet.slice(index..index + 4)
            in periodindex-(periodindex%5)..periodindex -> dataSet.slice(index..periodindex)
            else -> listOf()
        }
    }

    /**
     * API でリストデータを取得して画面に反映することを想定したメソッド。
     */
    private suspend fun fetchAndUpdate(index: Int) {
        val fetchedData = withContext(Dispatchers.Default) {
            fetch(index)
        }

        // 取得したデータを画面に反映。
        if (fetchedData.isNotEmpty()) {
            handler.post {
                handler.post { historyAdapter.add(fetchedData) }
            }
        }
        // 問い合わせが完了したら false に戻す。
        nowLoading = false
    }

    /**
     * リストの下端までスクロールしたタイミングで発火するリスナー。
     */
    inner class InfiniteScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            // アダプターが保持しているアイテムの合計
            val itemCount = historyAdapter.itemCount
            // 画面に表示されているアイテム数
            val childCount = recyclerView.childCount
            val manager = recyclerView.layoutManager as LinearLayoutManager
            // 画面に表示されている一番上のアイテムの位置
            val firstPosition = manager.findFirstVisibleItemPosition()

            // 何度もリクエストしないようにロード中は何もしない。
            if (nowLoading) {
                return
            }

            // 以下の条件に当てはまれば一番下までスクロールされたと判断できる。
            if (itemCount == childCount + firstPosition) {
                // API 問い合わせ中は true となる。
                nowLoading = true
                GlobalScope.launch {
                    fetchAndUpdate(historyAdapter.itemCount)
                }
            }
        }
    }

    override fun history_success(obj: JSONObject) {
        // 別スレッドで反映
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            val JSONArrayList = obj.getJSONArray("data")
            if (JSONArrayList != JSONObject.NULL) {
                //Toast.makeText(applicationContext,obj.toString(), Toast.LENGTH_SHORT).show()
                for (i in 0 until JSONArrayList.length()) {
                    var historyDate = JSONArrayList.getJSONObject(i).optString("recoded_date")
                    var after_lang_code = JSONArrayList.getJSONObject(i).optString("after_lang_code")
                    var before_lang_code = JSONArrayList.getJSONObject(i).optString("before_lang_code")
                    var before_translation = JSONArrayList.getJSONObject(i).optString("before_translation")
                    var after_translation = JSONArrayList.getJSONObject(i).optString("after_translation")
                    //Log.d("", "言語番号[%d]: %s → %s, %s → %s".format(i, before_lang_code, after_lang_code, before_translation, after_translation))
                    //Log.d("date","${SimpleDateFormat("yyyy/MM/dd").format(Date(historyDate))}")
                    dataSet.add(
                        HistoryData(
                            (i+1).toString(),
                            SimpleDateFormat("yyyy/MM/dd").format(Date(historyDate)),
                            before_lang_code,
                            after_lang_code,
                            before_translation,
                            after_translation
                        )
                    )
                }
                periodindex = dataSet.lastIndex
                Log.e("", "$periodindex")
            } else {
                Toast.makeText(applicationContext,"データが存在しません", Toast.LENGTH_SHORT).show()
            }

            Log.e("", "$periodindex")
            this@HistoryActivity.runOnUiThread{
                val listData = runBlocking { fetch(0) }
                historyAdapter = HistoryAdapter(listData as List<HistoryData>)
                findViewById<RecyclerView>(R.id.recycler_view).also {
                    it.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    it.adapter = historyAdapter
                    it.addItemDecoration(
                        DividerItemDecoration(
                            this@HistoryActivity,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    it.addOnScrollListener(InfiniteScrollListener())
                }
                progressBar.visibility = View.INVISIBLE
            }
        }

    }

    override fun history_failed(obj: JSONObject) {
        TODO("Not yet implemented")
    }
}