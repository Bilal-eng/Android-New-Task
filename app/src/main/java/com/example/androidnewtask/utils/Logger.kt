package com.example.androidnewtask.utils

import android.util.Log

class Logger {

    object Logger {
        private const val TAG = "MovieApp"
        fun debugErrorLogMessage(message: String) {
            Log.d(TAG, " 🤬 Message : $message")
        }

        fun debugSuccessLogMessage(message: String) {
            Log.d(TAG, " 😄 Message : $message")
        }
    }
}