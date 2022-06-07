package com.example.imageshare

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.example.imageshare.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI


class MainActivity : AppCompatActivity(), OkHttp3Callback.ApiCreateQRCallback {
    var apiRequest = OkHttp3Callback()  //API処理クラス呼び出し

    lateinit var shareQRcode: Uri       //共有するQRコード

    lateinit var QRimageView: ImageView //QRコードの画像
    lateinit var QRCreateButton: Button //QRコード作成ボタン
    lateinit var QRShareButton: Button  //QRコード共有ボタン
    lateinit var QRcodeText: TextView   //QRコード作成テキスト
    lateinit var QRsetText: TextView    //送るQRコードテキスト

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //BindingしたUI部品を連携する
        QRimageView = binding.qrImage
        QRCreateButton = binding.qrCreateButton
        QRShareButton = binding.qrShareButton
        QRcodeText = binding.qrCreateEditText
        QRsetText = binding.qrSendShareText

        apiRequest.setApiCreateQRCallback(this)                 //QRコード作成コールバック呼び出し

        //QRコード作成ボタンを押したとき
        QRCreateButton.setOnClickListener {
            apiRequest.getCreateQR(QRcodeText.text.toString())      //QRコード作成APIを実行
        }

        //QRコード共有ボタンを押したとき
        QRShareButton.setOnClickListener {
            ShareFunction()
        }
    }

    //シェアで行う処理をまとめたもの
    fun ShareFunction() {
//        val imageUri = resources.openRawResource(resId).use { input ->
//            val file = File("$cacheDir/create_qrcode.png")  //画像名は指定できる
//            file.createNewFile()
//            file.outputStream().use { output ->
//                input.copyTo(output)
//            }
//            FileProvider.getUriForFile(this, "$packageName.fileprovider", file)
//        }

        val imageUri = shareQRcode
        ShareCompat.IntentBuilder(this).apply {
            setChooserTitle("share title")      //この共有のアクティビティチューザーに使用されるタイトルを設定
            setText("これはQRコードの画像です。")   //共有の一部として送信されるリテラルテキストデータ(本文のデフォルト)を設定

            /** setStream(streamUri: Uri?)
             *  共有する必要のあるデータにストリームURIを設定します。
             *  これにより、現在設定されているすべてのストリームURIが置き換えられ、単一ストリームのACTION_SENDインテントが生成されます。*/
            setStream(imageUri)
            setType("image/png")
        }.startChooser()
    }

    // Base64 to Bitmap
    @Throws(IllegalArgumentException::class)
    fun convert(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(
            base64Str.substring(base64Str.indexOf(",") + 1),
            Base64.DEFAULT
        )

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    // Bitmap to URI
    private fun getImageUri(context: Context, bitmapImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val sdcard = Environment.getExternalStorageDirectory()
        if (sdcard != null) {
            val mediaDir = File(sdcard, "DCIM/Camera")
            if (!mediaDir.exists()) {
                mediaDir.mkdirs()
            }
        }
        val path = MediaStore.Images.Media.insertImage(
            context.getContentResolver(),
            bitmapImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    //QRコード作成の結果取得時
    override fun createQR_success(obj: JSONObject) {
        // 別スレッドで反映
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            Toast.makeText(applicationContext, obj.toString(), Toast.LENGTH_SHORT).show()
            Log.d("JSON結果", obj.toString())

            var Base64toBitmap: Bitmap = convert(obj.getString("qr_data"))
            QRimageView.setImageBitmap(Base64toBitmap)
            shareQRcode = getImageUri(this, Base64toBitmap)
            QRsetText.text = QRcodeText.text.toString()

        }
    }

    //QRコード作成の取得失敗時
    override fun createQR_failed(obj: String?) {
        TODO("Not yet implemented")
    }
}