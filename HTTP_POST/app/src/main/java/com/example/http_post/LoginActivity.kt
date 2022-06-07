package com.example.http_post

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity(), OkHttp3Callback.ApiLoginCallback {
    var  apiRequest = OkHttp3Callback()

    private lateinit var userId: EditText       //ユーザーID
    private lateinit var password: EditText     //パスワード
    private lateinit var btnLogin:Button        //ログインボタン
    private lateinit var btnNewCreate:Button    //新規登録ボタン

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //レイアウトからID指定したオブジェクト取得
        userId = findViewById<EditText>(R.id.login_user_edit)
        password = findViewById<EditText>(R.id.login_password)
        btnLogin = findViewById<Button>(R.id.login_login_button)
        btnNewCreate = findViewById<Button>(R.id.login_create_button)

        //履歴確認画面へ遷移
        if(true){
            Toast.makeText(this, "履歴確認画面へ遷移", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        //ユーザー登録されていないとき
        if(false){
            Toast.makeText(this, "ユーザー登録画面へ遷移", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

        apiRequest.setApiLoginCallback(this)    //ログインコールバック呼び出し

        //ログインボタンを押したとき
        btnLogin.setOnClickListener{
            //apiRequest.getAPI_login(userId.text.toString(),password.text.toString())
            apiRequest.getAPI_login("test2@gamil.com", "password")
        }
    }

    override fun login_success(obj: Any?) {val intent = Intent(this, MainActivity::class.java)
        Log.e("Login成功", obj.toString())
        intent.putExtra(obj.toString(), userId.text.toString())
        startActivity(intent);
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun login_failed(obj: String?) {
        // 別スレッドで反映
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            Toast.makeText(applicationContext,obj, Toast.LENGTH_SHORT).show()
        }
    }
}