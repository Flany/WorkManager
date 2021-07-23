package com.flany.workmanager.work

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

/**
 * @author: hec@shuyilink.com
 * @date:   2021/7/23
 * @since:
 */
class MainWorkData(private val context: Context, private val workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    companion object {
        private const val TAG = "MainWorkData"
    }

    override fun doWork(): Result {
        val data = workerParameters.inputData.getString("data-in")
        Log.i(TAG, "doWork: $data")
        try {
            Thread.sleep(1000)
        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success(Data.Builder().putString("data-out", "world").build())
    }
}