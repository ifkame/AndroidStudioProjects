//IE3A18 藤村伊織
package ie3a18_2190402.intent01

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //フィールドを追加する
    private var mEditText: EditText? = null

    // onCreateメソッド内へ下記の処理を追加する
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mEditText = findViewById(R.id.edit) as EditText
        // キーボードを表示するようにする
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

    }

    // checkEmptyメソッドを新規作成する（ｷｰﾜｰﾄﾞ欄が空(null、または空文字列)の場合にToastﾒｯｾｰｼﾞを表示する）
    private fun checkEmpty(keyword: String): Boolean {
        if (TextUtils.isEmpty(keyword)) {
            Toast.makeText(applicationContext, getString(R.string.lb_please_input_keyword), Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //入力されているテキストを取得
        val keyword = mEditText!!.text.toString()

        //連携処理を実施
        val itemId = item.itemId
        try {
            if (itemId == R.id.action_google) {
                //ウェブ検索（Google）
                if (!checkEmpty(keyword)) {
                    val intent = Intent(Intent.ACTION_WEB_SEARCH)
                    intent.putExtra(SearchManager.QUERY, keyword)
                    startActivity(intent)
                }
            } else if (itemId == R.id.action_googlemap) {
                // GoogleMAP検索
                if (!checkEmpty(keyword)) {
                    var strurl = "https://www.google.co.jp/maps/search/"
                    strurl = strurl + keyword
                    val url = Uri.parse(strurl)
                    startActivity(Intent(Intent.ACTION_VIEW, url))
                }
            } else if (itemId == R.id.action_yahoo) {
                // Yahoo!検索
                var strurl = "https://search.yahoo.co.jp/search?p="
                strurl = strurl + keyword
                val url = Uri.parse(strurl)
                startActivity(Intent(Intent.ACTION_VIEW, url))
            } else if (itemId == R.id.action_amazon) {
                // Amazon検索
                var strurl = "http://www.amazon.co.jp/gp/search/?field-keywords="
                strurl = strurl + keyword
                val url = Uri.parse(strurl)
                startActivity(Intent(Intent.ACTION_VIEW, url))
            } else if (itemId == R.id.action_rakuten) {
                // 楽天検索
                var strurl = "https://search.rakuten.co.jp/search/mall/"
                strurl = strurl + keyword + "/"
                val url = Uri.parse(strurl)
                startActivity(Intent(Intent.ACTION_VIEW, url))
            } else if (itemId == R.id.action_wiki) {
                // ウィキペディア検索
                var strurl = "https://ja.wikipedia.org/wiki/"
                strurl = strurl + keyword
                val url = Uri.parse(strurl)
                startActivity(Intent(Intent.ACTION_VIEW, url))
            }
        } catch (e: ActivityNotFoundException) {
            //開こうとしているアプリが見つからないエラー
            Toast.makeText(this, getString(R.string.lb_activity_not_found), Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    //onCreateOptionsMenuメソッドを以下の内容で【新規作成】する
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        getMenuInflater().inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

}