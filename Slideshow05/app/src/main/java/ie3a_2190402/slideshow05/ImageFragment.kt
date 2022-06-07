//IE3A18 藤村伊織

package ie3a_2190402.slideshow05

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie3a_2190402.slideshow05.databinding.FragmentImageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

//（１）画像ﾘｿｰｽ ID を保存するキー名 IMG_RES_ID 定義を追加する。
private const val IMG_RES_ID = "IMG_RES_ID"
//（２）画像ﾘｿｰｽ ID を保存する変数名 imgResId の定義を null 許容型で追加する。
private var imgResId: Int? = null

//（４）viewBinding 用の変数定義を追加する。
private var _binding: FragmentImageBinding? = null // 課題 05_1 用に追加
private val binding get() = _binding!! // 課題 05_1 用に追加

/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //（３）再生成時に呼ばれる onCreate ﾒｿｯﾄﾞ内で Bundle ｵﾌﾞｼﾞｪｸﾄに保存されている値を呼び出す。
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            imgResId = it.getInt(IMG_RES_ID) // 課題 05_1 用に追加
        }
    }

    //（５）メソッドの変更と追加を行う。
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // 課題 05_1 用に変更
        //return inflater.inflate(R.layout.fragment_image, container, false)

        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 課題 05_1 用にメソッドを追加
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 課題 05_1 用にメソッドを追加
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imgResId?.let {
            binding.imageView.setImageResource(it)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        // 課題 05_1 用に変更
        //fun newInstance(param1: String, param2: String) =
        fun newInstance(imageResId: Int) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    // 課題 05_1 用に追加
                    putInt(IMG_RES_ID, imageResId)
                }
            }
    }
}