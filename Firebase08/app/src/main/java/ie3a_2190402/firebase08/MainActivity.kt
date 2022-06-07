//IE3A18 藤村伊織

package ie3a_2190402.firebase08

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {
//    //課題 08_1
//    // フィールド用の変数（カウント数）を定義する
//    var count :String? = null
    var name :String? = null
    var pas :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        //課題 08_1
//        // 名前：like への参照を変数名 database へセットする
//        val database = FirebaseDatabase.getInstance().getReference("like")
//        // TextView を id:count_view で定義しておいた場合の例（id の取得）
//        val count_view = findViewById<TextView>(R.id.count_view)
//        // データベースの値が変更された場合に呼ばれるイベント処理
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // データベースの値を取得する（読み込み）
//                count = snapshot.value as String
//                count_view.text = count
//            }
//            override fun onCancelled(error: DatabaseError) {
//                //ここにエラーがあった場合の処理を記述する
//                Toast.makeText(applicationContext,
//                    "onCancelled が呼ばれました\n" + error.details, Toast.LENGTH_LONG).show()
//            }
//        })

        // 事前に DB へ、名前：user 値：""を追加しておくことで、
        val userdb = FirebaseDatabase.getInstance().getReference("user")
        // TextView を id:count_view で定義しておいた場合の例（id の取得）
        val username = findViewById<EditText>(R.id.username_edit)
        val password = findViewById<EditText>(R.id.password_edit)

//        //課題 08_1
//        //データベースの参照を取得する
//        val ref = userdb.getRef()
//        // Button を id:button で定義しておいた場合の例（id の取得）
//        val buttonWrite = findViewById<Button>(R.id.Addbutton)
//        // ボタンが押された場合の処理
//        buttonWrite.setOnClickListener {
//            var num = count?.toIntOrNull()
//            //toIntOrNull() :任意の文字列を数値型 (Int) に変換するするメソッド
//        //（変換できない文字列の場合は null を返す）
//            num = num?.plus(1) // インクリメントを行うメソッド
//            // データベースへ値をセットする（書き込み）
//            ref.setValue(num.toString())
//        }

//        //課題 08_3 自力で記述
//        //子要素の指定（最初の読み込みのときに最後のキーの値しか取らない）
//        //ChildEventListener → ValueEventListener に変更することで、
//        //データのリスト全体が単一の DataSnapshot として返されるため、これをループして個別の子にアクセスできます
//
//        // Button を id:button で定義しておいた場合の例（id の取得）
//        val buttonWrite = findViewById<Button>(R.id.Addbutton)
//        val buttonLogin = findViewById<Button>(R.id.Loginbutton)
//        // 追加ボタンが押された場合の処理
//        buttonWrite.setOnClickListener {
//            // 名前：user の子要素に、名前：username と値：password の組を追加することができる。
//            userdb.child(username.text.toString()).setValue(password.text.toString())
//        }
//
//        // ログインボタンが押された場合の処理
//        buttonLogin.setOnClickListener {
//            /*
//            val t4 = findViewById<TextView>(R.id.textView4)
//            val t5 = findViewById<TextView>(R.id.textView5)
//
//            t4.text = name
//            t5.text = pas
//            */
//
//            // データベースの値が変更された場合に呼ばれるイベント処理
//            userdb.ref.addChildEventListener(object : ChildEventListener {
//                // Firebase の子要素の値が追加または初期読み込みされた時
//                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                    Toast.makeText(applicationContext, "子要素の値が追加されました", Toast.LENGTH_LONG).show()
//                    // データベースの値を取得する（読み込み）
//                    name = snapshot.key as String
//                    pas = snapshot.value as String
//                }
//
//                // Firebase の子要素の値が変更された時
//                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                    Toast.makeText(applicationContext, "子要素が変更されました", Toast.LENGTH_LONG).show()
//                    // データベースの値を取得する（読み込み）
//                    name = snapshot.key as String
//                    pas = snapshot.value as String
//                }
//
//                // Firebase の子要素が削除された時
//                override fun onChildRemoved(snapshot: DataSnapshot) {
//                    Toast.makeText(applicationContext, "子要素が削除されました", Toast.LENGTH_LONG).show()
//                }
//
//                // Firebase の子要素の並びが変更された時
//                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                    Toast.makeText(applicationContext, "子要素の並びが変更されました", Toast.LENGTH_LONG).show()
//                    name = snapshot.key as String
//                    pas = snapshot.value as String
//                }
//
//                // Firebase のデータ読み取りがキャンセルされた時
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(applicationContext, "データ読み取りがキャンセルされました", Toast.LENGTH_LONG).show()
//                }
//            })
//
//            if(username.text.toString() == name.toString() && password.text.toString() == pas.toString()){
//                Toast.makeText(applicationContext, "ユーザーとパスワードが一致しました", Toast.LENGTH_LONG).show()
//                //Intentのインスタンスを作成
//                val intent = Intent(this, SubActivity::class.java)
//                //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
//                intent.putExtra("TEXT_KEY",name.toString())
//                //画面遷移を開始
//                startActivity(intent)
//            }
//        }

        //課題 08_3
        //【ヒント】
        val login = findViewById<Button>(R.id.Loginbutton)
        val add = findViewById<Button>(R.id.Addbutton)
        val editTextUser = findViewById<EditText>(R.id.username_edit)
        val editTextPassword = findViewById<EditText>(R.id.password_edit)

        // 追加ボタンが押された場合の処理
        add.setOnClickListener {
            // 名前：user の子要素に、名前：username と値：password の組を追加することができる。
            userdb.child(username.text.toString()).setValue(password.text.toString())
        }
        // ログインボタンクリック処理（08_3用）
        login.setOnClickListener {
            val user = editTextUser.text.toString()
            val password = editTextPassword.text.toString()
            //ユーザー名でDBにアクセスする
            userdb.child(user).get().addOnSuccessListener {
                //ログイン処理を記述
                //※①ユーザーが不在
                if(it.key?.isEmpty() == true){
                    Toast.makeText(applicationContext, "ユーザーが存在しません", Toast.LENGTH_LONG).show()
                //②ユーザーが存在しパスワードが一致
                } else if (it.key?.isNotEmpty() == true && it.value == password){
                    Toast.makeText(applicationContext, "ユーザーとパスワードが一致しました", Toast.LENGTH_LONG).show()
                    //Intentのインスタンスを作成
                    val intent = Intent(this, SubActivity::class.java)
                    //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
                    intent.putExtra("TEXT_KEY",user)
                    //画面遷移を開始
                    startActivity(intent)
                //③ユーザーが存在しパスワードが不一致
                } else if (it.key?.isNotEmpty() == true && it.value != password){
                    Toast.makeText(applicationContext, "パスワードが存在しません", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                //Error
            }
        }

    }
}