package com.flany.workmanager.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.flany.workmanager.utils.Constant.SP_KEY
import com.flany.workmanager.utils.Constant.SP_NAME
import com.flany.workmanager.utils.Constant.TAG
import java.lang.Exception

/**
 * @author: hec@shuyilink.com
 * @date:   2021/7/23
 * @since:
 */
class MainWorkerApp(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.i(TAG, "doWork: running...")
        try {
            Thread.sleep(6000)
        } catch (e: Exception) {
            return Result.failure()
        }

        val sp = context.applicationContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        var value = sp.getInt(SP_KEY, 0)
        sp.edit().putInt(SP_KEY, ++value).commit()

        Log.i(TAG, "doWork: finish...")
        return Result.success()
    }
}