//IE3A18 藤村伊織

package ie3a_2190402.lunchmenu04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie3a_2190402.lunchmenu04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //弁当画像クリック時
        binding.imageTop.setOnClickListener {
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }
    }
}