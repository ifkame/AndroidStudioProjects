package com.example.http_post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.http_post.databinding.ActivityCreateBinding
import com.example.http_post.databinding.ActivityMainBinding
import okhttp3.*
import okhttp3.Request.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(), OkHttp3Callback.ApiTranslateCallback {
    var apiRequest = OkHttp3Callback()
    private lateinit var binding: ActivityMainBinding

    private lateinit var beforeTransText: EditText      //翻訳前の入力テキスト
    private lateinit var afterTransText: EditText       //翻訳後の入力テキスト
    private lateinit var btnTranslate: Button           //翻訳ボタン

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //レイアウトからID指定したオブジェクト取得
        beforeTransText = binding.homeBeforeText
        afterTransText = binding.homeAfterText
        btnTranslate = binding.homeTranslateButton

        apiRequest.setApiTranslateCallback(this)    //翻訳コールバック呼び出し

        //翻訳ボタンを押したとき
        btnTranslate.setOnClickListener {
            //apiRequest.postTranslate(Defines.MY_TOKEN, beforeTransText.text.toString())
            apiRequest.postTranslate(Defines.MY_TOKEN, beforeTransText.text.toString())
        }
    }

    override fun translate_success(obj: JSONObject) {
        // 別スレッドで反映
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            Toast.makeText(applicationContext,obj.toString(), Toast.LENGTH_SHORT).show()
            Log.e("トークン", obj.optString("result"))
            afterTransText.setText(obj.optString("result").toString())  //翻訳後テキスト表示
        }
    }

    override fun translate_failed(obj: JSONObject) {
        TODO("Not yet implemented")
    }
}