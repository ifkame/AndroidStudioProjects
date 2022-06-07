//IE3A18 藤村伊織

package ie3a_2190402.lunchmenu04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie3a_2190402.lunchmenu04.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //牛丼ボタンクリック時
        binding.gyudonButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container,GyudonFragment())        //牛丼 Fragment 呼び出し
                addToBackStack(null)
                commit()
            }
        }

        //ラーメンボタンクリック時
        binding.ramenButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container,RamenFragment())         //ラーメン Fragment 呼び出し
                addToBackStack(null)
                commit()
            }
        }

        //カレーボタンクリック時
        binding.curryButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container,CurryFragment())         //カレー Fragment 呼び出し
                addToBackStack(null)
                commit()
            }
        }

        //豚カツボタンクリック時
        binding.porkcutletButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container,PorkcutletFragment())    //豚カツ Fragment 呼び出し
                addToBackStack(null)
                commit()
            }
        }

        //うどんボタンクリック時
        binding.udonButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container,UdonFragment())          //うどん Fragment 呼び出し
                addToBackStack(null)
                commit()
            }
        }

        //蕎麦ボタンクリック時
        binding.sobaButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container,SobaFragment())          //蕎麦 Fragment 呼び出し
                addToBackStack(null)
                commit()
            }
        }

    }
}