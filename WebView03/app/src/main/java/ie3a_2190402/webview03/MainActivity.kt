//IE3A18 藤村伊織
package ie3a_2190402.webview03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // 回転数
    private var mCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // WebSettingsオブジェクトを取得
        val settings = webView.settings

        // JavaScriptを有効にする
        settings.javaScriptEnabled = true
        // WebViewのリンクをクリックするたびにIntentが発行される処理を無効にする
        webView.setWebViewClient(WebViewClient())

        // onSaveInstanceState によって設定された Bundle から値を取得
        if (savedInstanceState != null) {
            mCount = savedInstanceState.getInt("count", 0)
        }

        // カウント数（mCount）を４で割り、余り（0～4 の値）から分岐
        val imagetype = mCount % 4

        when (imagetype) {
            0 -> {
                webView.loadUrl("https://www.google.com/")
            }
            1 -> {
                webView.loadUrl("https://www.youtube.com/")
            }
            2 -> {
                webView.loadUrl("https://ja.wikipedia.org/wiki/")
            }
            3 -> {
                webView.loadUrl("https://twitter.com/?lang=ja")
            }
        }
        // onCreate が呼ばれるごとにカウントアップする
        mCount++

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("count", mCount)
        // WebViewの状態を保存する（画面の回転前の状態を保存する処理）
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }


    override fun onBackPressed() {
        Toast.makeText(applicationContext, "「onBackPressed」が呼ばれました。", Toast.LENGTH_SHORT).show()

        // 履歴があれば、Backキーで前のページヘ戻る
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            // 履歴がなければ通常通りBackキーの処理を行い、Activityを終了する
            super.onBackPressed()
        }
    }

    override fun onPause() {
        Toast.makeText(applicationContext, "「onPause」が呼ばれました。", Toast.LENGTH_SHORT).show()

        super.onPause()
        // WebViewで実行中の処理を停止
        webView.onPause()
    }

    override fun onResume() {
        Toast.makeText(applicationContext, "「onResume」が呼ばれました。", Toast.LENGTH_SHORT).show()

        // WebViewの処理を再び開始
        webView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        Toast.makeText(applicationContext, "「onDestroy」が呼ばれました。", Toast.LENGTH_SHORT).show()

        // WebViewを安全に終了
        if (webView != null) {
            webView.stopLoading()
            webView.setWebChromeClient(null)
            webView.destroy()
        }
        super.onDestroy()
    }
}