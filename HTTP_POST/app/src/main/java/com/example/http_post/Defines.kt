package com.example.http_post

interface Defines {
    companion object{
        /*
           LocalHostのアドレス
           ホスト ループバック インターフェースへの特殊エイリアス
           （開発マシンの 127.0.0.1 など）
         */
        /*
            constをつけるとgetXXX()の処理がなくなるのでメソッド数が減る。
            constをつけるとgetXXX()メソッドを一つ挟まずに直接変数にアクセスする。
         */
        const val NEW_REG_URL = "http://10.0.2.2:5000/new_user_reg"    //新規登録時に使用するURL
        const val LOGIN_URL = "http://10.0.2.2:5000/login"             //ログイン時に使用するURL
        const val GET_LANG_URL = "http://10.0.2.2:5000/get_lang_code"  //言語取得時に使用するURL
        const val TRANSLATE_URL = "http://10.0.2.2:5000/translate"  //言語取得時に使用するURL
        const val HISTORY_URL = "http://10.0.2.2:5000/history"  //言語取得時に使用するURL

        //自分のトークン("id"と"lang_code"を暗号化したもの)
        var MY_TOKEN : String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MiwibGFuZ19jb2RlIjoiREEifQ.DxNv9Ch6uNV85HL-XYfvXdOsvzjDk8-jVTEApVrKDpQ"
        //翻訳言語リスト(初期設定：空配列)
        var TRANSLATE_LANGUAGE: ArrayList<String> = ArrayList<String>()

        /*
            //ユーザ名[name](メールアドレス)
            var USER_ID = "userId1"
            //パスワード
            var PASS_WARD = ""
            //自分のアイコン画像
            var MY_ICON_IMAGE: Int? = 0
            //選択されたユーザー名
            var SELECT_USER_ID = "userId1"
            //以前選択されたユーザー名
            var BEFORE_USER_ID = ArrayList<String>()
            //選択されたコメント
            var SELECT_COMENT = ""
            //選択されたユーザアイコン[image](アイコン)
            var SELECT_IMAGE: Int? = null
            //選択されたイイね画像
            var SELECT_GOOD: Int? = null
            //選択できるアイコン画像一覧
            val ICON_IMAGE_LIST = arrayListOf<Int>(
                R.mipmap.ic_launcher,R.drawable.ic01_alarm_clock,R.drawable.ic02_chick,
                R.drawable.ic03_cooking_tools, R.drawable.ic04_frog,R.drawable.ic05_morning_glory,
                R.drawable.ic06_owl, R.drawable.ic07_panda,R.drawable.ic08_raccoon,
                R.drawable.ic09_shoes, R.drawable.ic10_tropical_fish
            )
            //選択できるアイコンテキスト一覧
            val ICON_TEXT_LIST = arrayListOf<String>(
                "デフォルト(ドロイド君)","目覚まし時計","ひよこ","料理道具","かえる","朝顔",
                "ふくろう","パンダ","たぬき","靴","熱帯魚"
            )

            // 選択された BGM を格納
            var MUSIC_SELECT_TEXT = "なし"

            //選択された人数を格納
            var NUMBER_PEOPLE = ""

            //プレイヤー名を格納
            var PLAY_NAME = arrayListOf("プレイヤー１","プレイヤー２","プレイヤー３","プレイヤー４","プレイヤー５","プレイヤー６")
        */
    }
}