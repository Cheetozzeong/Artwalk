package com.a401.artwalk.view.record

import android.app.Service
import android.content.Intent
import android.os.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

const val RECORD_TICK = "RecordTick"
const val RECORD_STATUS = "RecordStatus"

const val IS_RECORD_RUNNING = "isRecordRunning"
const val TIME_ELAPSED = "timeElapsed"
const val SERVICE_COMMAND = "Command"

class RecordService : Service(), CoroutineScope {

    private var serviceState: RecordState = RecordState.INITIALIZED
    private val helper by lazy { NotificationHelper(this) }

    private var currentTime by Delegates.notNull<Int>()

    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable = object : Runnable {
        override fun run() {
            currentTime++
            sendDuration()
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
                RecordState.GET_STATUS -> sendStatus()
                else-> return START_STICKY
            }
        }
        return START_STICKY
    }



    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        job.cancel()
    }

    private fun endRecord() {
        serviceState = RecordState.STOP
        handler.removeCallbacks(runnable)
        sendStatus()
        stopService()
    }

    private fun pauseRecord() {
        serviceState = RecordState.PAUSE
        handler.removeCallbacks(runnable)
        sendStatus()
    }

    private fun startRecord() {
        serviceState = RecordState.START

        startForeground(NotificationHelper.NOTIFICATION_ID, helper.getNotification())
        sendStatus()

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

    private fun sendStatus() {
        val statusIntent = Intent(RECORD_STATUS)
            .putExtra(IS_RECORD_RUNNING, serviceState as Parcelable)
            .putExtra(TIME_ELAPSED, currentTime)
        sendBroadcast(statusIntent)
    }

    private fun sendDuration() {
        val elapseTime = currentTime
        val durationIntent = Intent(RECORD_TICK)
            .putExtra(TIME_ELAPSED, elapseTime)
        sendBroadcast(durationIntent)
    }
}