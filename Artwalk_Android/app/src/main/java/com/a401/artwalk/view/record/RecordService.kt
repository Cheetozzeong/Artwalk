package com.a401.artwalk.view.record

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

const val SERVICE_COMMAND = "Command"
const val NOTIFICATION_TEXT = "NotificationText"

class RecordService : Service(), CoroutineScope {

    var serviceState: RecordState = RecordState.INITIALIZED
    private val helper by lazy { NotificationHelper(this) }

    private var currentTime by Delegates.notNull<Int>()
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable = object : Runnable {
        override fun run() {
            currentTime++
            broadcastUpdate()
            handler.postDelayed(this, 1000)
        }
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        currentTime = 0
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        intent?.extras?.run {
            when(getSerializable(SERVICE_COMMAND) as RecordState) {
                RecordState.START -> startRecord()
                RecordState.PAUSE -> pauseRecord()
                RecordState.STOP -> endRecord()
                else-> return START_NOT_STICKY
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        job.cancel()
    }

    private fun broadcastUpdate() {
        when (serviceState) {
            RecordState.START -> {
                val elapsedTime = currentTime
                sendBroadcast(
                    Intent("RECORD_ACTION")
                        .putExtra(NOTIFICATION_TEXT, elapsedTime)
                )
            }
            RecordState.PAUSE -> {

            }
            else -> {
                sendBroadcast(
                    Intent("RECORD_ACTION")
                        .putExtra(NOTIFICATION_TEXT, 0)
                )
            }
        }
    }

    private fun endRecord() {
        serviceState = RecordState.STOP
        handler.removeCallbacks(runnable)
        broadcastUpdate()
        stopService()
    }

    private fun pauseRecord() {
        serviceState = RecordState.PAUSE
        handler.removeCallbacks(runnable)
        broadcastUpdate()
    }

    private fun startRecord() {
        serviceState = RecordState.START
        broadcastUpdate()
        startForeground(NotificationHelper.NOTIFICATION_ID, helper.getNotification())
        startCoroutineTimer()
    }

    private fun stopService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        } else {
            stopSelf()
        }
    }

    private fun startCoroutineTimer() {
        launch(coroutineContext) {
            handler.post(runnable)
        }
    }
}