package com.example.wisperapp

import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

class S_03_01 : M_00_01() {
    //
    private var mEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whisper)

        //変数宣言
        mEditText = findViewById(R.id.wh_edit) as EditText              //入力用 Edittext
        val cancelButton: Button = findViewById(R.id.wh_cancel_button)  //戻る用 Button
        val postButton: Button = findViewById(R.id.wh_post_button)      //投稿用 Button

        // キーボードを表示するようにする
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        //キャンセルボタンクリック時
        cancelButton.setOnClickListener {
            finish()        //今開いている画面を閉じて、前の画面に戻る
        }

        //投稿ボタンクリック時
        postButton.setOnClickListener {
            //①WebAPIを叩き、入力された内容を投稿する
            finish()        //②投稿入力ダイアログを閉じる
        }
    }
}