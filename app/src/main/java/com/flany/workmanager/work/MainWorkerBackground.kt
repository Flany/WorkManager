package com.flany.workmanager.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.flany.workmanager.utils.Constant.TAG
import java.lang.Exception

/**
 * @author: hec@shuyilink.com
 * @date:   2021/7/23
 * @since:
 */
class MainWorkerBackground(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.i(TAG, "doWork: running...")
        try {
            Thread.sleep(6000)
        } catch (e: Exception) {
            return Result.failure()
        }
        Log.i(TAG, "doWork: finish...")
        return Result.success()
    }
}