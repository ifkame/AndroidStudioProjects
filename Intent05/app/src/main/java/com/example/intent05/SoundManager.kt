package com.example.intent05

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

class SoundManager  (context: Context){

    //効果音を鳴らす（SoundPool）
    private val mSoundPool : SoundPool

    companion object{
        val SOUND_GUN = 0
        val SOUND_EXPLO = 1

        //利用する効果音を列挙する。配布するファイルをコピーする。
        private val SoundList = intArrayOf(R.raw.gun, R.raw.explo)
    }

    private val mSoundTable = IntArray(SoundList.size)

    init {
        //SoundPool の初期化
        val autoAttributes = AudioAttributes.Builder()
            //USAGE_MEDIA
            //USAGE_GAME
            .setUsage(AudioAttributes.USAGE_GAME)
            //CONTENT_TYPE_MUSIC
            //CONTENT_TYPE_SPEECH, etc
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()

        mSoundPool = SoundPool.Builder()
            .setAudioAttributes(autoAttributes)
            //ストリーム数に応じて
            .setMaxStreams(2)
            .build()

        //SoundPool を使って効果音をロードし、戻り値の ID を mSoundTable に保存する
        for (i in SoundList.indices){
            mSoundTable[i] = mSoundPool.load(context, SoundList[i], 1)
        }
    }

    //効果音の再生
    fun play(no: Int, vol : Int){
        if (no < 0 || no >= mSoundTable.size){
            return
        }
        val fvol = (vol / 100).toFloat()

        mSoundPool.play(mSoundTable[no], fvol, fvol, 0, 0,1.0f)
    }

    //効果音の解放
    fun release(){
        //読み込まれていた効果音を解放する
        for (i in mSoundTable.indices){
            mSoundPool.unload(mSoundTable[i])
        }
        mSoundPool.release()
    }
}