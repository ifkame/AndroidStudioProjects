package com.example.imageshare

import android.util.Log
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class OkHttp3Callback {
    companion object{
        /**
         * 実機か仮想端末のどちらを使うかで localhostのアドレスを変更
         * LOCAL(今回使う端末のLocalhostアドレス)
         *  - ACTUAL_LOCAL(実機端末のLocalhostアドレス): localhost
         *  - VIRTUAL_LOCAL(仮想端末のLocalhostアドレス): 10.0.2.2
         */
        private const val ACTUAL_LOCAL = "localhost"    //実機端末のLocalhostアドレス
        private const val PC_LOCAL = "10.105.0.43"      //実機端末(PC)のIPアドレス(Localhost)
        private const val VIRTUAL_LOCAL = "10.0.2.2"    //仮想端末のLocalhostアドレス
        private const val LOCAL = VIRTUAL_LOCAL         //今回使う端末のLocalhostアドレス
        const val QR_URL = "http://${LOCAL}:5000/qr_code/qr_text="                      //QRコードの作成時に使用するURL
    }
    private lateinit var apiCreateQRCallback: ApiCreateQRCallback                   //QRコード作成コールバック

    var client = OkHttpClient()     //OkHttpオブジェクト生成(グローバル変数)

    /********************************
     * インターフェース
     ********************************/

    interface ApiCreateQRCallback {             //QRコード作成時に行う処理を関数化
        fun createQR_success(obj: JSONObject)   //QRコード作成コールバックの値返却・成功処理
        fun createQR_failed(obj: String?)       //QRコード作成コールバックの値返却・失敗処理（通信時のエラー）
    }

    /**************************************
     * コールバック設定
     **************************************/
    fun setApiCreateQRCallback(apiCreateQRCallback: ApiCreateQRCallback) {
        this.apiCreateQRCallback = apiCreateQRCallback
    }

    /************************
     * APIメソッド
     ************************/

    /**
     * QRコード作成チェック
     */
    fun getCreateQR(qr_text: String) {
        try{
            // リクエストを作成する
            val request: Request = Request.Builder()
                .url(QR_URL + qr_text)
                .build()

            Log.d("test", "URL = " + request.url)

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // 失敗
                    println("fail : $e")
                    Log.d("test", "Error = $e")
                }

                override fun onResponse(call: Call, response: Response) {
                    //response取り出し
                    var jsonStr = response.body?.string()
                    Log.d("Hoge", "jsonStr=" + jsonStr);
                    val parentJsonObj = JSONObject(jsonStr)
                    // 値を取得
                    val status: String = parentJsonObj.getString("text")  // => Your Name.
                    Log.d("test", "qr_text(text) = $status")
                    if (status != "") {
                        apiCreateQRCallback.createQR_success(parentJsonObj);
                    } else {
                        apiCreateQRCallback.createQR_failed(parentJsonObj.getString("text"));
                    }
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}