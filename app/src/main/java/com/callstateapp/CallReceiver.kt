package com.callstateapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast

class CallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

        when (phoneState) {
            TelephonyManager.EXTRA_STATE_IDLE -> {
                if (lastPhoneState == TelephonyManager.EXTRA_STATE_RINGING) {
                    showToast(context, "Missed Call from $phoneNumber")
                } else if (lastPhoneState == TelephonyManager.EXTRA_STATE_OFFHOOK) {
                    showToast(context, "Outgoing Call Ended")
                }
            }
            TelephonyManager.EXTRA_STATE_RINGING -> {
                showToast(context, "Incoming Call Started $phoneNumber")
            }
            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                if (lastPhoneState == TelephonyManager.EXTRA_STATE_RINGING) {
                    showToast(context, "On Incoming Call Started $phoneNumber")
                } else {
                    showToast(context, "On Outgoing Call Started")
                }
            }
        }
        lastPhoneState = phoneState
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private var lastPhoneState = TelephonyManager.EXTRA_STATE_IDLE
    }
}
