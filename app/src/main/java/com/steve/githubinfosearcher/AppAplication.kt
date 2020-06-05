package com.steve.githubinfosearcher

import android.app.Application
import android.content.Context

class AppAplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: AppAplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = AppAplication.applicationContext()
    }
}