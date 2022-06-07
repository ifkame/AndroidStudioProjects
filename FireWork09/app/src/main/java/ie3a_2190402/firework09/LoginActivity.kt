package ie3a_2190402.firework09

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {
    //lateinit var UserList: ArrayList<String>    //ユーザーリストのオブジェクト生成
    var UserList: ArrayList<String> = ArrayList<String>()    //ユーザーリストのオブジェクト生成
    var PassList: ArrayList<String> = ArrayList<String>()    //パスワードリストのオブジェクト生成

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userdb = FirebaseDatabase.getInstance().getReference("user")    //ユーザーデータベース
        val username = findViewById<EditText>(R.id.username_edit)                //名前入力
        val password = findViewById<EditText>(R.id.password)                     //パスワード入力

        userdb.addValueEventListener(object : ValueEventListener {
            //userdb.child("${username.text}").addValueEventListener(object : ValueEventListener {
            //データ取得成功
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    UserList.add(postSnapshot.key as String)
                    PassList.add(postSnapshot.value as String)
                }
                Log.d("s",UserList.toString())
            }
            //データ取得失敗
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "データ取得失敗", Toast.LENGTH_LONG).show()
            }
        })

        val buttonWrite = findViewById<Button>(R.id.Addbutton)                   //追加ボタン
        val buttonLogin = findViewById<Button>(R.id.Loginbutton)                 //ログインボタン

        // 追加ボタンが押された場合の処理
        buttonWrite.setOnClickListener {
            //空白または Null かどうか判定
            val UserCheck = username.text.toString().trim().isNullOrEmpty()
            val PassCheck = password.text.toString().trim().isNullOrEmpty()

            when {
                UserCheck -> {
                    Toast.makeText(applicationContext, "名前を入力してください", Toast.LENGTH_SHORT).show()
                }
                PassCheck -> {
                    Toast.makeText(applicationContext, "パスワードを入力してください", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    for (num in UserList.indices){
                        if (UserList[num] == username.text.toString() && PassList[num] == password.text.toString()) {
                            Toast.makeText(applicationContext, "既にそのユーザーとパスワードは登録しています", Toast.LENGTH_SHORT).show()
                            break
                        }
                        if (UserList[num] == username.text.toString()) {
                            AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                                .setTitle("ユーザー名が確認されました")
                                .setMessage("パスワードの変更を行いますか？")
                                .setPositiveButton("OK") { dialog, which ->
                                    // TODO:Yesが押された時の挙動
                                    // 名前：user の子要素に、名前：username と値：password の組を追加することができる。
                                    userdb.child(username.text.toString()).setValue(password.text.toString())
                                }
                                .setNegativeButton("No") { dialog, which ->
                                    // TODO:Noが押された時の挙動
                                    return@setNegativeButton
                                }
                                .setIcon(R.mipmap.ic_launcher)
                                .show()
                        }
                    }

                    //リアルタイムデータベースの情報格納リストの更新
                    userdb.addValueEventListener(object : ValueEventListener {
                        //userdb.child("${username.text}").addValueEventListener(object : ValueEventListener {
                        //データ取得成功
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            //リスト情報をリセット
                            UserList.clear()
                            PassList.clear()
                            for (postSnapshot in dataSnapshot.children) {
                                UserList.add(postSnapshot.key as String)
                                PassList.add(postSnapshot.value as String)
                            }
                            Log.d("s",UserList.toString())
                        }
                        //データ取得失敗
                        override fun onCancelled(databaseError: DatabaseError) {
                            Toast.makeText(applicationContext, "データ取得失敗", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
        }

        // ログインボタンが押された場合の処理
        buttonLogin.setOnClickListener {

            //空白または Null かどうか判定
            val UserCheck = username.text.toString().trim().isNullOrEmpty()
            val PassCheck = password.text.toString().trim().isNullOrEmpty()

            if (UserCheck) {
                Toast.makeText(applicationContext, "名前を入力してください", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (PassCheck) {
                Toast.makeText(applicationContext, "パスワードを入力してください", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            userdb.addValueEventListener(object : ValueEventListener {
            //userdb.child("${username.text}").addValueEventListener(object : ValueEventListener {
                //データ取得成功
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //リスト情報をリセット
                    UserList.clear()
                    PassList.clear()
                    for (postSnapshot in dataSnapshot.children) {
                        UserList.add(postSnapshot.key as String)
                        PassList.add(postSnapshot.value as String)
                    }
                    Log.d("s",UserList.toString())
                }
                //データ取得失敗
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(applicationContext, "データ取得失敗", Toast.LENGTH_LONG).show()
                }
            })

            var UserPassFlag = 0    //ユーザーとパスワードが一致するか確認するフラグ(1 → 一致, 0 → 不一致)
            for (num in UserList.indices){
                if (UserList[num] == username.text.toString() && PassList[num] == password.text.toString()) {
                    Toast.makeText(applicationContext, "ユーザーとパスワードが一致しました", Toast.LENGTH_LONG).show()
                    //Intentのインスタンスを作成
                    val intent = Intent(this, SelectActivity::class.java)
                    //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
                    intent.putExtra("NAME",username.text.toString())
                    intent.putExtra("USER",UserList)
                    intent.putExtra("PASSWORD",PassList)
                    UserPassFlag = 1    //ユーザーとパスワードが一致
                    startActivity(intent)   //画面遷移を開始
                    break
                }
            }

            if (UserPassFlag == 0){     //ユーザーとパスワードが不一致のとき
                Toast.makeText(applicationContext, "ユーザーとパスワードが登録されていません", Toast.LENGTH_LONG).show()
            }
        }
    }
}