package com.flany.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.work.*
import com.flany.workmanager.work.MainWorkBackground
import com.flany.workmanager.work.MainWorkData

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startSleepWork(view: View) {
        WorkManager.getInstance(this)
            .enqueue(OneTimeWorkRequestBuilder<MainWorkBackground>().build())
    }

    fun startDataWork(view: View) {
        val request = OneTimeWorkRequestBuilder<MainWorkData>().setInputData(
            Data.Builder().putString("data-in", "hello").build()
        ).build()
        WorkManager.getInstance(this).apply {
            getWorkInfoByIdLiveData(request.id).observe(this@MainActivity) {
                Log.i(TAG, "startDataWork: state: ${it.state.name}")
                if (it.state.isFinished) {
                    Log.i(TAG, "startDataWork: data: ${it.outputData.getString("data-out")}")
                }
            }
            enqueue(request)
        }
    }

    fun startMultiWork(view: View) {}
}