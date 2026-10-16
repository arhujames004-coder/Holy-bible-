package com.example.audio

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build

class DeviceSpeakerHelper(private val context: Context) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager

    fun getStreamMaxVolume(): Int {
        return audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: 15
    }

    fun getStreamVolume(): Int {
        return audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: 10
    }

    fun getStreamVolumePercent(): Float {
        val max = getStreamMaxVolume()
        if (max <= 0) return 1.0f
        return (getStreamVolume().toFloat() / max).coerceIn(0.0f, 1.0f)
    }

    fun setStreamVolumePercent(percent: Float) {
        val max = getStreamMaxVolume()
        val target = (percent * max).toInt().coerceIn(0, max)
        try {
            audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, target, 0)
        } catch (ignored: Exception) {}
    }

    fun getActiveAudioDeviceName(): String {
        val am = audioManager ?: return "Phone Speaker"
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val devices = am.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
                val bluetooth = devices.firstOrNull {
                    it.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP ||
                            it.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO
                }
                if (bluetooth != null) {
                    return "📶 Bluetooth Audio (${bluetooth.productName ?: "Connected"})"
                }
                val wired = devices.firstOrNull {
                    it.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES ||
                            it.type == AudioDeviceInfo.TYPE_WIRED_HEADSET ||
                            it.type == AudioDeviceInfo.TYPE_USB_HEADSET
                }
                if (wired != null) {
                    return "🎧 Headset / Earphones Connected"
                }
            }
            "🔊 Built-in Phone Speaker"
        } catch (e: Exception) {
            "🔊 Built-in Phone Speaker"
        }
    }
}
