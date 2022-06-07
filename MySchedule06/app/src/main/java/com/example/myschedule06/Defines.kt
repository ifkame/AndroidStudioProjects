package com.example.myschedule06

import java.text.SimpleDateFormat


interface Defines {
    companion object{
        // 日付を取得するKEY
        val KEY_DATE = "date"

        // メモ帳のサブジェクトを取得する際のKEY
        val KEY_SUBJECT = "subject"

        // メモ帳の内容を取得する際のKEY
        val KEY_CONTENT = "content"

        // 日付を整形出力するためのフォーマッター
        val sFmt = SimpleDateFormat("yyyy/MM/dd(E)")

    }
}