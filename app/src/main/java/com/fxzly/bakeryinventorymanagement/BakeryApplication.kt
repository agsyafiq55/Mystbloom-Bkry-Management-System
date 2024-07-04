package com.fxzly.bakeryinventorymanagement

import android.app.Application
import com.fxzly.bakeryinventorymanagement.data.AppContainer
import com.fxzly.bakeryinventorymanagement.data.AppDataContainer

class BakeryApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
