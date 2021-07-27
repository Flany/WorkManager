package com.flany.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.work.*
import com.flany.workmanager.utils.Constant.TAG
import com.flany.workmanager.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 模拟单任务
     */
    fun startSleepWork(view: View) {
        WorkManager.getInstance(this)
            .enqueue(OneTimeWorkRequestBuilder<MainWorkerBackground>().build())
    }

    /**
     * 模拟数据传递
     */
    fun startDataWork(view: View) {
        val request = OneTimeWorkRequestBuilder<MainWorkerData>().setInputData(
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

    /**
     * 模拟多任务
     */
    fun startMultiWork(view: View) {
        //1.所有都是串行执行
//        WorkManager.getInstance(this)
//            .beginWith(OneTimeWorkRequestBuilder<MainWorkerMulti.Worker1>().build())//return Result.failure() 会导致下个任务不执行
//            .then(OneTimeWorkRequestBuilder<MainWorkerMulti.Worker2>().build())
//            .then(OneTimeWorkRequestBuilder<MainWorkerMulti.Worker3>().build())
//            .enqueue()

        WorkManager.getInstance(this)
            //并行执行
            .beginWith(
                listOf(
                    OneTimeWorkRequestBuilder<MainWorkerMulti.Worker1>().build(),
                    OneTimeWorkRequestBuilder<MainWorkerMulti.Worker2>().build()
                )
            )
            //串行执行
            .then(OneTimeWorkRequestBuilder<MainWorkerMulti.Worker3>().build())
            .enqueue()
    }

    /**
     * 模拟app退出后，还能执行任务，等待一段时间查看SP文件
     */
    fun startAppWork(view: View) {
        WorkManager.getInstance(this)
            .enqueue(OneTimeWorkRequestBuilder<MainWorkerDataStore>().build())
    }

    /**
     * 模拟轮询任务
     */
    fun startRepeatWork(view: View) {
        WorkManager.getInstance(this)
            .enqueue(
                PeriodicWorkRequestBuilder<MainWorkerMulti.Worker3>(
                    10,
                    TimeUnit.SECONDS//最小事件15分钟
                ).setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresCharging(true)
                        .build()
                ).build()
            )
    }
}