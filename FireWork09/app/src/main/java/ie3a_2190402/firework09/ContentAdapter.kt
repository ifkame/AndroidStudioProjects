package ie3a_2190402.firework09

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContentAdapter internal constructor(private var ContentDataList: List<ContentData>) : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {
    // Viewの初期化
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val order_number: TextView  //順位
        val iconId: ImageView       //アイコン画像
        val user: TextView          //ユーザー名
        val score: TextView         //内容

        //レイアウトとアダプター内で使用する型を紐づける
        init {
            order_number = view.findViewById(R.id.order_number)     //順位オブジェクト生成
            iconId = view.findViewById(R.id.iconId)                 //アイコン画像オブジェクト生成
            user = view.findViewById(R.id.user)                     //ユーザー名オブジェクト生成
            score = view.findViewById(R.id.score)                   //内容オブジェクト生成
        }
    }

    // レイアウトの設定
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        //ユーザー詳細用のRecyclerViewのパラメータ格納
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.content_view_holder, viewGroup, false)
        return ViewHolder(view)     //内容のアイテムリスト生成
    }

    // RecyclerViewの設定(作成)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ContentData = ContentDataList[position]   //指定された配列リストのデータをそれぞれの変数に格納

        //ユーザー詳細の ViewHolderのレイアウトに与えられた値を設定
        viewHolder.order_number.text = ContentData.order_number.toString()  //順位 → order_number
        viewHolder.iconId.setImageResource(ContentData.iconId)              //アイコン画像 → iconId
        viewHolder.user.text = ContentData.user                             //ユーザー名 → user
        viewHolder.score.text = ContentData.score.toString()                //得点 → score
    }

    // 表示数を返す
    override fun getItemCount() = ContentDataList.size
}