package com.example.http_post

import android.R
import android.accounts.Account
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import com.example.http_post.databinding.ActivityCreateBinding
import com.example.http_post.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject

class CreateActivity : AppCompatActivity(), OkHttp3Callback.ApiCreateUserCallback, OkHttp3Callback.ApiLanguageCallback {
    var  apiRequest = OkHttp3Callback()
    private lateinit var binding: ActivityCreateBinding

    private var spinnerItems : ArrayList<String> = ArrayList<String>()  // 選択できる言語リスト
    private var select_lang: Int = 1                                    // 選択した言語のインデックス

    private lateinit var langSpinner: Spinner           //言語スピナー
    private lateinit var btnCreateUser: Button          //新規登録ボタン
    private lateinit var confirmText: TextView          //選択した言語確認テキスト
    private lateinit var userId: EditText               //ユーザーID
    private lateinit var password: EditText             //パスワード

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //レイアウトからID指定したオブジェクト取得
        langSpinner = binding.createLangSpinner
        btnCreateUser = binding.createAccountButton
        confirmText = binding.createSelectText
        userId = binding.createUserEdit
        password = binding.createPassword

        apiRequest.setApiLanguageCallback(this)     //言語取得コールバック呼び出し
        apiRequest.setApiCreateUserCallback(this)   //新規登録コールバック呼び出し

        /**
         * 言語を取得するAPI
         */
        apiRequest.getAPI_language()

        langSpinner.post {    //言語スピナーに post()して UIThreadを操作する
            // Adapterの作成（Adapterのパラメータ設定）
            val adapter = ArrayAdapter(applicationContext,
                R.layout.simple_spinner_item, Defines.TRANSLATE_LANGUAGE)

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // spinner に adapter をセット
            // View Binding
            langSpinner.adapter = adapter
        }

        // 言語スピナーが押したとき
        langSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner               //この言語スピナーオブジェクトをスピナーとして作成
                val item = spinnerParent.selectedItem as String     //作成したスピナーから選択したアイテムを取得
                // View Binding
                confirmText.text = item        //選択したアイテムを表示
                select_lang = position+1                    //選択した位置を選択した言語として格納
            }

            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //特に処理なし
            }
        }

        //新規登録ボタンを押したとき
        btnCreateUser.setOnClickListener {
            /**
             * 新規登録を行うAPI
             * email: 作成したいユーザ名
             * lang_id: 作成するユーザーの登録言語
             * password: 作成したいユーザのパスワード
             */
            apiRequest.postCreateUser(userId.text.toString(), select_lang, password.text.toString())
        }
    }

    //言語の取得成功時
    override fun language_success(obj: JSONArray?) {
        for (i in 0 until obj!!.length()) {
            var lang_id = obj!!.getJSONObject(i).getInt("lang_id")
            var lang_name = obj!!.getJSONObject(i).getString("lang_name")
            Log.d("", "言語番号[%d]: %s".format(lang_id, lang_name))
            spinnerItems.add(lang_name)
            Defines.TRANSLATE_LANGUAGE.add(lang_name)
        }
    }

    //言語の取得失敗時
    override fun language_failed(obj: String?) {
    }

    override fun createuser_success(obj: JSONObject) {
        // 別スレッドで反映
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            Toast.makeText(applicationContext,obj.toString(), Toast.LENGTH_SHORT).show()
            Log.e("トークン", obj.getString("token"))
        }
    }

    //新規登録の取得失敗時
    override fun createuser_failed(obj: JSONObject) {
        // 別スレッドで反映
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            Toast.makeText(applicationContext,obj.toString(), Toast.LENGTH_SHORT).show()
            Log.d("エラーメッセージ", obj.getString("message"))
        }
    }
}