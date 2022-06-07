package com.example.http_post

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * 履歴表示アダプター（未定）
 * textView: 値[Number (0 ~ 100)]
 */
class MyAdapter(private val listData: MutableList<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    // レイアウトの設定
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //履歴表示用のRecyclerViewのパラメータ格納
        val inflater = LayoutInflater.from(parent.context)
        val textView = inflater.inflate(R.layout.list_item, parent, false) as TextView
        return MyViewHolder(textView)   //履歴表示のアイテムリスト生成
    }

    // RecyclerViewの設定(作成)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //履歴表示の ViewHolderのレイアウトに与えられた値を設定
        holder.textView.text = listData[position]
    }

    // 表示数を返す
    override fun getItemCount() = listData.size

    /**
     * リストデータを追加して画面に反映させるメソッド。
     */
    fun add(listData: List<String>) {
        this.listData += listData
        notifyDataSetChanged()
    }

    // Viewの初期化
    class MyViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView){
        //レイアウトとアダプター内で使用する型を紐づける
        init {

        }
    }
}