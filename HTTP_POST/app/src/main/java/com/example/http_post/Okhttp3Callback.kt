package com.example.http_post

import android.util.Log
import android.widget.Spinner
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class OkHttp3Callback {
    companion object{
        /*
           LocalHostのアドレス
           ホスト ループバック インターフェースへの特殊エイリアス
           （開発マシンの 127.0.0.1 など）
         */
        /*
            constをつけるとgetXXX()の処理がなくなるのでメソッド数が減る。
            constをつけるとgetXXX()メソッドを一つ挟まずに直接変数にアクセスする。
         */

        const val NEW_REG_URL = "http://10.0.2.2:5000/new_user_reg"     //新規登録時に使用するURL
        const val LOGIN_URL = "http://10.0.2.2:5000/login"              //ログイン時に使用するURL
        const val GET_LANG_URL = "http://10.0.2.2:5000/get_lang_code"   //言語取得時に使用するURL
        const val TRANSLATE_URL = "http://10.0.2.2:5000/translate"      //翻訳時に使用するURL
        const val HISTORY_URL = "http://10.0.2.2:5000/history"          //履歴取得時に使用するURL
    }

    public val TEXT_SUCCESS = "success"
    private lateinit var apiManagerCallback: ApiLoginCallback           //ログインコールバック
    private lateinit var apiCreateUserCallback: ApiCreateUserCallback   //新規登録コールバック
    private lateinit var apiLanguageCallback: ApiLanguageCallback       //言語取得コールバック
    private lateinit var apiTranslateCallback: ApiTranslateCallback     //翻訳コールバック
    private lateinit var apiHistoryCallback: ApiHistoryCallback         //履歴取得コールバック

    var client = OkHttpClient()                                                 //OkHttpオブジェクト生成(グローバル変数)
    private val JSON_MEDIA = "application/json; charset=utf-8".toMediaType()    //POST通信時使用するJSON設定

    /********************************
     * インターフェース
     ********************************/

    interface ApiLoginCallback {        //ログイン時に行う処理を関数化
        fun login_success(obj: Any?)        //ログインコールバックの値返却・成功処理
        fun login_failed(obj: String?)      //ログインコールバックの値返却・失敗処理（通信時のエラー）
    }

    interface ApiCreateUserCallback {   //新規作成時に行う処理を関数化
        fun createuser_success(obj: JSONObject)  //新規登録コールバックの値返却・成功処理
        fun createuser_failed(obj: JSONObject)   //新規登録コールバックの値返却・失敗処理（通信時のエラー）
    }

    interface ApiLanguageCallback {     //選択言語取得時に行う処理を関数化
        fun language_success(obj: JSONArray?)   //選択言語取得コールバックの値返却・成功処理
        fun language_failed(obj: String?)       //選択言語取得コールバックの値返却・失敗処理（通信時のエラー）
    }

    interface ApiTranslateCallback {    //翻訳時に行う処理を関数化
        fun translate_success(obj: JSONObject)  //翻訳コールバックの値返却・成功処理
        fun translate_failed(obj: JSONObject)   //翻訳コールバックの値返却・失敗処理（通信時のエラー）
    }

    interface ApiHistoryCallback {      //履歴取得時に行う処理を関数化
        fun history_success(obj: JSONObject)    //履歴取得コールバックの値返却・成功処理
        fun history_failed(obj: JSONObject)     //履歴取得コールバックの値返却・失敗処理（通信時のエラー）
    }

    /**************************************
     * コールバック設定
     **************************************/

    fun setApiLoginCallback(apiLoginCallback: ApiLoginCallback) {
        if (apiLoginCallback != null) {
            this.apiManagerCallback = apiLoginCallback
        }
    }

    fun setApiCreateUserCallback(apiCreateUserCallback: ApiCreateUserCallback) {
        this.apiCreateUserCallback = apiCreateUserCallback
    }

    fun setApiLanguageCallback(apiLanguageCallback: ApiLanguageCallback){
        this.apiLanguageCallback = apiLanguageCallback
    }

    fun setApiTranslateCallback(apiTranslateCallback: ApiTranslateCallback){
        this.apiTranslateCallback = apiTranslateCallback
    }

    fun setApiHistoryCallback(apiHistoryCallback: ApiHistoryCallback){
        this.apiHistoryCallback = apiHistoryCallback
    }

    /************************
     * APIメソッド
     ************************/

    /**
     * ログインチェック
     */
    fun getAPI_login(userId: String, password: String) {
        try{
            // POST通信で送るJSONデータ
            val sendDataJson = "{\"password\":\"$password\",\"email\":\"$userId\"}"
            // リクエストを作成する
            val request: Request = Request.Builder()
                .url(LOGIN_URL)
                .post(sendDataJson.toRequestBody(JSON_MEDIA))
                .build()

            Log.d("test", "URL = " + request.url)

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // 失敗
                    println("fail : $e")
                    Log.d("test", "Error = " + e)
                }

                override fun onResponse(call: Call, response: Response) {
                    //response取り出し
                    var jsonStr = response.body?.string()
                    Log.d("Hoge", "jsonStr=" + jsonStr);
                    val parentJsonObj = JSONObject(jsonStr)
                    // 値を取得
                    val status: String = parentJsonObj.getString("status")  // => Your Name.
                    Log.d("test", "status = $status")
                    if (status == "200") {
                        apiManagerCallback.login_success(parentJsonObj.getString("token"));
                    } else {
                        apiManagerCallback.login_failed(parentJsonObj.getString("status"));
                    }
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * ユーザ作成
     * email: 作成したいユーザID
     * lang_id: 作成したいユーザ名
     * password: 作成したいユーザのパスワード
     *
     */
    fun postCreateUser(email: String?, lang_id: Int, password: String) {
        try{
            // POST通信で送るJSONデータ
            val sendDataJson = "{\"password\":\"$password\",\"email\":\"$email\",\"lang_id\":$lang_id}"

            // リクエストを作成する
            val request: Request = Request.Builder()
                .url(NEW_REG_URL)
                .post(sendDataJson.toRequestBody(JSON_MEDIA))
                .build()
            Log.d("test", "URL = " + request.url)
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    //response取り出し
                    var jsonStr = response.body?.string()
                    Log.d("postCreateUser", "jsonStr=" + jsonStr);
                    val parentJsonObj = JSONObject(jsonStr)
                    // 値を取得
                    val status: String = parentJsonObj.getString("status")  // => Your Name.
                    Log.d("test", "status = $status")
                    if (status == "200") {
                        apiCreateUserCallback.createuser_success(parentJsonObj)
                    } else {
                        apiCreateUserCallback.createuser_failed(parentJsonObj)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    // 失敗
                    println("fail : $e")
                    Log.d("test", "Error = " + e)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * 選択できる言語取得
     */
    fun getAPI_language() {
        try{
            // リクエストを作成する
            val request: Request = Request.Builder()
                .url(GET_LANG_URL)
                .get()
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
                    Log.d("getAPI_language", "jsonStr=$jsonStr")
                    val parentJsonObj = JSONObject(jsonStr)
                    if (parentJsonObj.optJSONArray("data") != null) {
                        val JSONArrayList = parentJsonObj.getJSONArray("data")
                        apiLanguageCallback.language_success(JSONArrayList)
                    } else {
                        apiLanguageCallback.language_failed("errMsg")
                    }
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * 指定した言語に翻訳する
     */
    fun postTranslate(token: String, text: String) {
        try{
            // POST通信で送るJSONデータ
            val sendDataJson = "{\"token\":\"$token\",\"text\":\"$text\"}"

            // リクエストを作成する
            val request: Request = Request.Builder()
                .url(TRANSLATE_URL)
                .post(sendDataJson.toRequestBody(JSON_MEDIA))
                .build()
            Log.d("test", "URL = " + request.url)
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    //response取り出し
                    var jsonStr = response.body?.string()
                    Log.d("postTranslate", "jsonStr=" + jsonStr);
                    val parentJsonObj = JSONObject(jsonStr)
                    // 値を取得
                    val status: String = parentJsonObj.getString("status")  // => Your Name.
                    Log.d("test", "status = $status")
                    if (status == "200") {
                        apiTranslateCallback.translate_success(parentJsonObj)
                    } else {
                        apiTranslateCallback.translate_failed(parentJsonObj)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    // 失敗
                    println("fail : $e")
                    Log.d("test", "Error = " + e)
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace();
        }
    }

    /**
     * 履歴取得
     */
    fun getAPI_history(token: String) {
        try{
            // POST通信で送るJSONデータ
            val sendDataJson = "{\"token\":\"$token\"}"

            // リクエストを作成する
            val request: Request = Request.Builder()
                .url(HISTORY_URL)
                .post(sendDataJson.toRequestBody(JSON_MEDIA))
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
                    Log.d("getAPI_language", "jsonStr=$jsonStr")
                    val parentJsonObj = JSONObject(jsonStr)
                    if (parentJsonObj.optJSONArray("data") != null) {
                        apiHistoryCallback.history_success(parentJsonObj)
                    } else {
                        apiHistoryCallback.history_failed(parentJsonObj)
                    }
                }
            })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}