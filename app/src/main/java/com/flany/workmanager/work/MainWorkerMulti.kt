package com.flany.workmanager.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.flany.workmanager.utils.Constant
import java.lang.Exception

/**
 * @author: hec@shuyilink.com
 * @date:   2021/7/23
 * @since:
 */
class MainWorkerMulti {

    class Worker1(context: Context, workerParameters: WorkerParameters) :
        Worker(context, workerParameters) {
        override fun doWork(): Result {
            Log.i(Constant.TAG, "doWork1: running...")
            try {
                Thread.sleep(3000)
//                throw Exception()
            } catch (e: Exception) {
                e.printStackTrace()
                return Result.failure()
            }
            Log.i(Constant.TAG, "doWork1: finish...")
            return Result.success()
        }
    }

    class Worker2(context: Context, workerParameters: WorkerParameters) :
        Worker(context, workerParameters) {
        override fun doWork(): Result {
            Log.i(Constant.TAG, "doWork2: running...")
            try {
                Thread.sleep(2000)
            } catch (e: Exception) {
                return Result.failure()
            }
            Log.i(Constant.TAG, "doWork2: finish...")
            return Result.success()
        }
    }

    class Worker3(context: Context, workerParameters: WorkerParameters) :
        Worker(context, workerParameters) {
        override fun doWork(): Result {
            Log.i(Constant.TAG, "doWork3: running...")
            try {
                Thread.sleep(1000)
            } catch (e: Exception) {
                return Result.failure()
            }
            Log.i(Constant.TAG, "doWork3: finish...")
            return Result.success()
        }
    }
}