package ie2a_2190402.hello01

import android.icu.util.Calendar
import android.icu.util.Calendar.getInstance
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TextviewのIDを取得する
        var textView02: TextView = findViewById(R.id.text02)
        textView02.text = "誕生日は4月1日です。"

        //今日の日付情報
        val calender    :Calendar = Calendar.getInstance()
        val day         :Int= calender.get(Calendar.DAY_OF_MONTH)
        val month       :Int= calender.get(Calendar.MONTH)
        val year        :Int= calender.get(Calendar.YEAR)

        //日付を秒単位にする
        val today       :Long = calender.timeInMillis

        //カレンダーを誕生日にする
        calender.set(year, 10-1, 10)

        //誕生日までの日数を計算
        val birthday    : Long = (calender.timeInMillis - today)/24/60/60/1000

        //誕生日までの日数を Int に変換
        val nissu       : Int = Math.round(Math.floor(birthday.toDouble())) .toInt()

        //TextviewのIDを取得する
        var textView03  : TextView = findViewById(R.id.text03)
        var str         : String = "次の誕生日まであと $nissu 日です"
        textView03.text = str
    }
}