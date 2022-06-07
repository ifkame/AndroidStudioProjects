//IE3A18 藤村伊織
package ie3a_2190402.mymap07

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ie3a_2190402.mymap07.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback , LocationListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var GoogleMapCount: Int = 0
    val GoogleMapLatitude: List<Double> = listOf(34.7014631, 35.681366901503566, 35.17104653249209)
    val GoogleMapLongitude: List<Double> = listOf(135.4963996, 139.76717844132907, 136.88157981247727)
    val GoogleMapText: List<String> = listOf("ここが大阪駅です！", "ここが東京駅です！", "ここが名古屋駅です！")

    private var genzaiti : LatLng = LatLng(0.0,0.0)
    private var nowMarker: Marker? = null

    @SuppressLint("MissingPermission")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // ロケーションマネージャー
        var locMan : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // GPS_PROVIDER:GPS衛星から現在地取得、NETWORK_PROVIDER:ネットワークから現在地取得
        // minTime 最小更新時間(ミリ秒)、minDistance 最小更新距離(メートル)
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 5F, this)
        //locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 5F, this)

    }

    //メニューの作成の関数
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        //メニューのリソース選択
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    //メニューのアイテムを押下した時の処理の関数
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            //タイムライン(action_timeline)ボタンを押したとき
            R.id.action_osaka -> {
                GoogleMapCount = 0
                onMapReady(mMap)
                return true
            }
            //検索(action_search)ボタンを押したとき
            R.id.action_tokyo -> {
                GoogleMapCount = 1
                onMapReady(mMap)
                return true
            }
            //ウィスパー(action_whisper)ボタンを押したとき
            R.id.action_nagoya -> {
                GoogleMapCount = 2
                onMapReady(mMap)
                return true
            }
            //Myプロフィール(action_my_pro)ボタンを押したとき
            R.id.action_my_position -> {
                // 現在地のマーカー位置に移動
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(genzaiti, 17f))
                // マーカーの位置とクリックした時に表示される文字列をセットする
                mMap.addMarker(MarkerOptions().position(genzaiti).title("ここが現在地です！"))
                return true
            }
        }
        //whenで指定している以外のIDが取得したときに何も返されなくなるのを防ぐ
        return super.onOptionsItemSelected(item)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // 指定した場所の緯度と経度を指定する
        // (lat:緯度 latitude, lng:経度 longitude)
        val map01 = LatLng(GoogleMapLatitude[GoogleMapCount],GoogleMapLongitude[GoogleMapCount])
        // マーカーの位置とクリックした時に表示される文字列をセットする
        mMap.addMarker(MarkerOptions().position(map01).title(GoogleMapText[GoogleMapCount]))
        // 位置を指定したマップを表示する（newLatLngZoom メソッドでマップの縮尺も指定）
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(map01, 17f))
    }

    override fun onLocationChanged(location: Location) {
        // 現在地が変更されるとこのメソッドが呼び出されるため、ここで最新の現在地を取得できる。
        Toast.makeText(applicationContext, "現在地が更新されました", Toast.LENGTH_LONG).show()

        // let文で現在地のマーカーが存在している時のみ削除する。
        nowMarker?.let{
            it.remove()
        }

        location?.let{
            // 最新の位置情報に更新をしてマーカーを設定する。
            genzaiti = LatLng(it.latitude,it.longitude)
            nowMarker = mMap.addMarker(MarkerOptions().position(genzaiti).title("現在地"))
        }
    }
    /*
    override fun onProviderEnabled(provider: String) {
    }
    override fun onProviderDisabled(provider: String) {
    }
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }   */

}
