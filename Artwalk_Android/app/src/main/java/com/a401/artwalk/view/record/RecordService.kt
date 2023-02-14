package com.a401.artwalk.view.record

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.properties.Delegates


const val RECORD_TICK = "RecordTick"
const val RECORD_LOCATION = "RecordLocation"
const val RECORD_STATUS = "RecordStatus"

const val IS_RECORD_RUNNING = "isRecordRunning"
const val TOTAL_LOCATION = "TotalLocation"
const val LAST_LOCATION = "LAST_LOCATION"
const val CURRENT_LOCATION = "CURRENT_LOCATION"
const val DISTANCE = "DISTANCE"
const val TIME_ELAPSED = "timeElapsed"

const val SERVICE_COMMAND = "Command"

class RecordService : Service(), CoroutineScope {

    private var serviceState: RecordState = RecordState.INITIALIZED
    private val helper by lazy { NotificationHelper(this) }
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var currentTime by Delegates.notNull<Int>()
    private var totalDistance by Delegates.notNull<Double>()
    private val locationList = ArrayList<DoubleArray>()

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
        totalDistance = 0.0
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
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

        currentTime = 0
        totalDistance = 0.0
        locationList.clear()

        stopService()
    }

    private fun pauseRecord() {
        serviceState = RecordState.PAUSE

        handler.removeCallbacks(runnable)
        fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)

        sendStatus()
    }


    private fun startRecord() {
        startForeground(NotificationHelper.NOTIFICATION_ID, helper.getNotification())
        serviceState = RecordState.START
        sendStatus()
        startCoroutineTimer()
        startGetLocation()
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

    @SuppressLint("MissingPermission")
    private fun startGetLocation() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            3000
        ).setMinUpdateDistanceMeters(4f).build()

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper())
    }

    private fun sendStatus() {
        val statusIntent = Intent(RECORD_STATUS)
            .putExtra(IS_RECORD_RUNNING, serviceState as Parcelable)
            .putExtra(TIME_ELAPSED, currentTime)
            .putExtra(TOTAL_LOCATION, locationList)
            .putExtra(DISTANCE, totalDistance)
        sendBroadcast(statusIntent)
    }

    private fun sendDuration() {
        val elapseTime = currentTime
        val durationIntent = Intent(RECORD_TICK)
            .putExtra(TIME_ELAPSED, elapseTime)
        sendBroadcast(durationIntent)
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation.let {  location ->
                if(location != null) {
                    val curLngLat = doubleArrayOf(location.longitude, location.latitude)
                    Log.v("LOCATION_UPDATE", "${location?.latitude}, ${location?.longitude}")
                    val lastLngLat = if (locationList.isEmpty()) {
                        curLngLat
                    }else {
                        locationList.last()
                    }
                    locationList.add(curLngLat)
                    totalDistance += getDistance(lastLngLat, curLngLat)

                    sendBroadcast(
                        Intent(RECORD_LOCATION)
                            .putExtra(LAST_LOCATION, lastLngLat)
                            .putExtra(CURRENT_LOCATION, curLngLat)
                            .putExtra(DISTANCE, totalDistance)
                    )
                }
            }
        }
    }

    private fun getDistance(lastLngLat: DoubleArray, curLngLat: DoubleArray): Double {
        val dLat = Math.toRadians(lastLngLat[1] - curLngLat[1])
        val dLong = Math.toRadians(lastLngLat[0] - curLngLat[0])
        val a = Math.sin(dLat / 2).pow(2.0) + Math.sin(dLong / 2)
            .pow(2.0) * Math.cos(Math.toRadians(curLngLat[1]))
        val c = 2 * asin(sqrt(a))
        return (6372.8 * 1000 * c) / 1000 // 상수 입니다..!
    }
}