package com.nencer.thesieure

import android.app.Application
import android.content.ContextWrapper
import com.nencer.thesieure.di.appModule
import org.koin.core.context.startKoin

lateinit var mApplication: Application

class App : Application() {

    companion object{
        private lateinit var instance: App
        fun get(): App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        instance = this
        
        startKoin {
            modules(appModule)
        }
    }
}

object AppContext : ContextWrapper(mApplication)