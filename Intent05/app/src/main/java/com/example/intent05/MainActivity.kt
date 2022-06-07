package com.example.intent05

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Instant

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** ボタン（id:button01）を押した時に呼び出される処理を追加する */
        button01.setOnClickListener {
            // Hello01クラスを呼び出すIntentを生成
            val intent = Intent(this,Hello01::class.java)
            // Intent呼び出しを実行する
            startActivity(intent)
        }

        /** ボタン（id:button02）を押した時に呼び出される処理を追加する */
        button02.setOnClickListener {
            // Hello01クラスを呼び出すIntentを生成
            val intent = Intent(this,Event02::class.java)
            // Intent呼び出しを実行する
            startActivity(intent)
        }

        /** ボタン（id:button03）を押した時に呼び出される処理を追加する */
        button03.setOnClickListener {
            // Hello01クラスを呼び出すIntentを生成
            val intent = Intent(this,EventWork03::class.java)
            // Intent呼び出しを実行する
            startActivity(intent)
        }

        /** ボタン（id:button04）を押した時に呼び出される処理を追加する */
        button04.setOnClickListener {
            // Hello01クラスを呼び出すIntentを生成
            val intent = Intent(this,List04::class.java)
            // Intent呼び出しを実行する
            startActivity(intent)
        }

        cameraBt.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,200)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //リクエスト番号が 200 かつ 正常終了のとき
        if (requestCode == 200 && resultCode == Activity.RESULT_OK){
            //カメラが呼び出されて正常終了したとき
            val bitmap = data?.getParcelableExtra<Bitmap>("data")
            imageView.setImageBitmap(bitmap)
        } else if (requestCode == 201 && resultCode == Activity.RESULT_OK){
            //設定画面が呼び出されて正常終了した時（など）
        }

    }
}