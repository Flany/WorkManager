package com.flany.workmanager.work

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.flany.workmanager.utils.Constant.SP_KEY
import com.flany.workmanager.utils.Constant.TAG
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @author: hec@shuyilink.com
 * @date:   2021/7/23
 * @since:
 */
class MainWorkerDataStore(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private val dataStore: DataStore<Preferences> by lazy { context.createDataStore(name = "PREFERENCE_NAME") }

    private val key = preferencesKey<Int>(SP_KEY)

    override fun doWork(): Result {
        Log.i(TAG, "doWork: running...")
        try {
            Thread.sleep(6000)
        } catch (e: Exception) {
            return Result.failure()
        }
        GlobalScope.launch {
            dataStore.edit {
                var value = it[key] ?: 0
                it[key] = ++value
            }
        }
        Log.i(TAG, "doWork: finish...")
        return Result.success()
    }
}