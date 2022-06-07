package com.example.myschedule06

import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit.*

class Edit : AppCompatActivity() {

    //パラメータで受け取った日付のフィールド定義を追加
    private var mDate: Long = 0
    // ﾃﾞﾌｫﾙﾄの設定ﾌｧｲﾙ名を“mPrefs”と名づける（nullを許可）
    private var mPrefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)



        /**  西暦年月日を画面上に表示する処理を追加する  */

        // Intentから日付を取得する
        val intent = intent

        //インテントからデータを取得する
        if (intent != null){
            if (intent.hasExtra(Defines.KEY_DATE)){
                mDate = intent.getLongExtra(Defines.KEY_DATE,0)
            }
        }

        // リストから選択した日付を整形した文字列に変換する
        val date = Defines.sFmt.format(mDate)
        // TextViewには日付、EditTextにはﾃﾞﾌｫﾙﾄの設定ﾌｧｲﾙに保存されている文字列を表示
        textView1.text = date

        //  保存ボタンイベント
        button1.setOnClickListener{
            val dlg = AlertDialog.Builder(this)

            dlg.setTitle(getString(R.string.SaveTitle))//保存確認

            // アラートダイアログのメッセージを設定します「メッセージを保存しますか？」

            dlg.setMessage(R.string.DialogMsg1)

            // 「はい」を表示させる
            dlg.setPositiveButton(getString(R.string.Yes), DialogInterface.OnClickListener { _, _ ->
                // TODO 自動生成されたメソッド・スタブ
                val toast = Toast.makeText(applicationContext, R.string.YesMsg, Toast.LENGTH_SHORT)
                toast.show()

                // 入力欄に記述されている文字列を変数contentに取得する
                val content = editText1.text.toString()

                // Editorクラスのインスタンスを取得する
                val editor = mPrefs!!.edit()    // !!は、Null許容

                // キー名"content"に、入力された文字列を設定する
                editor.putString(date, content)

                // 設定を反映する
                editor.commit()
            })
            // 「いいえ」を表示させる
            dlg.setNegativeButton(getString(R.string.No), DialogInterface.OnClickListener { _, _ ->
                // TODO 自動生成されたメソッド・スタブ
                val toast = Toast.makeText(applicationContext, R.string.NoMsg, Toast.LENGTH_LONG)
                toast.show()
            })
            dlg.show()
        }

        //  終了ボタンイベント
        button2.setOnClickListener{
            val dlg = AlertDialog.Builder(this)

            dlg.setTitle(getString(R.string.FinishTitle))//終了確認

            // アラートダイアログのメッセージを設定します「終了してよろしいですか？」
            dlg.setMessage(R.string.DialogMsg2)

            // 「はい」を表示させる
            dlg.setPositiveButton(getString(R.string.Yes)) { _, _ ->
                // TODO 自動生成されたメソッド・スタブ
                val toast = Toast.makeText(applicationContext, R.string.YesMsg, Toast.LENGTH_SHORT)
                toast.show()

                // 終了処理
                finish()
            }


            // 「いいえ」を表示させる
            dlg.setNegativeButton(getString(R.string.No)) { _, _ ->
                // TODO 自動生成されたメソッド・スタブ
                val toast = Toast.makeText(applicationContext, R.string.NoMsg, Toast.LENGTH_LONG)
                toast.show()
            }
            dlg.show()
        }

        // ﾃﾞﾌｫﾙﾄﾌﾟﾚﾌｧﾚﾝｽのインスタンスを取得
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        // ﾃﾞﾌｫﾙﾄﾌﾟﾚﾌｧﾚﾝｽからキー名"content"の値を取得する(第2引数は未取得時の値)
        val content = mPrefs!!.getString(date, "")  // !!は、Null許容

        val txtcontent: EditText = findViewById(R.id.editText1)

        // データを表示する
        txtcontent.setText(content)
    }
}