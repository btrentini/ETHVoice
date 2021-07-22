package com.example.ethvoice

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Let's initialise this variable later
    lateinit var mRecorder: MediaRecorder
    var recording = false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions,0)
        }

        val audioStreamButton = findViewById<Button>(R.id.audio_stream) as Button
        audioStreamButton.setOnClickListener {

            if(recording){
                stopRecording()
                recording = false
                (it as Button).text = "AUDIO STREAM OFF"
                (it as Button).setBackgroundColor(getColor(R.color.purple_500))
            }else{
                startRecording()
                recording = true
                (it as Button).text = "AUDIO STREAM ON"
                (it as Button).setBackgroundColor(getColor(R.color.special_green))
            }

        }
    }

    fun startRecording(){
        var output = filesDir.absolutePath+"/record.mp3"
        mRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(output)
        }
        mRecorder.prepare()
        mRecorder.start()
    }

    fun stopRecording(){
        mRecorder.stop()
        mRecorder.reset()
        mRecorder.release()
        playAudio()
    }

    fun playAudio(){
        var output = filesDir.absolutePath+"/record.mp4"
        val mp = MediaPlayer().apply {
            setDataSource(output)
            prepare()
            start()
            setVolume(10f,10f)
        }


    }
}